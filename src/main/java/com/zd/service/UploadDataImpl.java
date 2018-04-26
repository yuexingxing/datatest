package com.zd.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zd.dao.Scan_DetailDao;
import com.zd.object.ActionRequest;
import com.zd.object.ResultInfo;
import com.zd.object.ScanDataListEnt;
import com.zd.object.UploadHeader;
import com.zd.pojo.ScanData;
import com.zd.util.Constant;
import com.zd.util.MyBatisUtil;
import com.zd.util.SqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 数据上传服务,实现两个基本功能
 * 1) 存储文件到中间数据库,每一批次的存储数据,写入后获取一个 Scan.ID 当做待处理批次号;
 * 2) lot_no 写入到redis队列,作为一个生产项目id
 * 3) 启动 springboot 服务程序,进行数据同步(从临时库将数据同步到主库)
 * <p/>
 * 数据库中数据的保存逻辑:
 * (1)每批上传(n条扫描)要写入一条Scan表记录, n条Scan_Detail 以及 n条Scan_Detail_xxx
 * (2)Scan_Detail_xxx 对应具体的业务，海运、陆运、空运、等等，每种业务对应不同表，但有些共性字段同时要冗余写入Scan_Detail表
 */
@Service("uploaddataService")
public class UploadDataImpl implements UploadDataInterface {

    // 内部错误编码,写入到日志,用于跟踪错误的
    private static final String Inner_log_Errcode = "【UploadDataImpl-ERR】-";

    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(UploadDataImpl.class);
    private static final TypeReference<UploadHeader> Header_type = new TypeReference<UploadHeader>() {};
    /**
     * (1) json数据解析到类对象
     * (2) 数据存储到 scan,Scan_Detail,scan_detail_xxx(具体业务表),route_task表
     */
    public ResultInfo upload(ScanDataListEnt scandataPackage, ActionRequest actionRequest) {

        ResultInfo rlt = new ResultInfo();
        rlt.setSuccess(0);
        SqlSession sen = null;

        try {
            // 禁止自动提交数据修改
            sen = MyBatisUtil.getSqlSession(false);
            SqlMapper sqlMapper = new SqlMapper(sen);

            //贴唛
            if(actionRequest.getType().equals(Constant.SCAN_TYPE_STICK)){
                rlt = new Scan_DetailDao().save_data_stick(actionRequest, scandataPackage, sen);
            }else{
                rlt = new Scan_DetailDao().save_data(actionRequest, scandataPackage, sen);
            }

            if (rlt.getSuccess()==0){

                rlt.setCode("1001");
                rlt.setMessage("数据提交成功");
                rlt.setData(null);
            }else{

                sen.rollback();
                rlt.setSuccess(-1);
                rlt.setData(null);
                rlt.setCode("4004");
            }

            // 正确则提交事务
            sen.commit();

            sen.close();;
            sen = null;
            sqlMapper = null;

        } catch (Exception ex) {
            logger.error(Inner_log_Errcode + "upload()函数,异常:" + ex.getMessage());

            sen.rollback();
            rlt.setSuccess(-1);
            rlt.setMessage("数据提交失败!");
            rlt.setData(null);
            rlt.setCode("4004");
            return rlt;
        }

        return rlt;
    }

    /**
     * (1) 抵达扫描修改
     * (2) 数据存储到 scan,Scan_Detail
     */
    public ResultInfo upload_arrive(ScanDataListEnt scandataPackage, ActionRequest actionRequest) {

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(0);
        SqlSession sen = null;
        boolean trans_flag = true;//提交事务成功标记
        try {

            // 禁止自动提交数据修改
            sen = MyBatisUtil.getSqlSession(false);
            SqlMapper sqlMapper = new SqlMapper(sen);

            Scan_DetailDao sdo = new Scan_DetailDao();

            ScanData scanData = scandataPackage.getDetail().get(0);

//            actionRequest.setTask_name(scanData.getTaskName());
//            actionRequest.setLoad_task_id(scanData.getTaskId());
//
//            resultInfo = CommandTools.getTaskId(actionRequest, sen, resultInfo);
//            if (resultInfo.getSuccess() == 0){
//                scanData.setTaskId(resultInfo.getCode());
//            }else{
//                return resultInfo;
//            }

            //数据保存到Scan表，通过route_task_id关联detail
            resultInfo = sdo.save_data_scan(scandataPackage, scanData, sen, resultInfo);
            if (resultInfo.getSuccess()!=0) {
                sen.rollback();
                resultInfo.setSuccess(-1);
                resultInfo.setData(null);
                resultInfo.setCode("4004");
                return resultInfo;
            }

            resultInfo = sdo.save_data_detail_arrive(scandataPackage, sen, resultInfo);
            if (resultInfo.getSuccess()!=0) {
                sen.rollback();
                resultInfo.setSuccess(-1);
                resultInfo.setData(null);
                resultInfo.setCode("4004");
                return resultInfo;
            }
            //---------------------------------------------------------------

            if (resultInfo.getSuccess()==0){

                resultInfo.setCode("1001");
                resultInfo.setMessage("数据提交成功");
                resultInfo.setData(null);
            }else{

                sen.rollback();
                resultInfo.setSuccess(-1);
                resultInfo.setData(null);
                resultInfo.setCode("4004");
                return resultInfo;
            }

            sdo = null;

        } catch (Exception ex) {
            logger.error(Inner_log_Errcode + "upload()函数,异常:" + ex.getMessage());

            sen.rollback();
            resultInfo.setSuccess(-1);
            resultInfo.setMessage("数据提交失败!");
            resultInfo.setData(null);
            resultInfo.setCode("4004");
            return resultInfo;
        }

        // 正确则提交事务
        sen.commit();

        // 返回
        return resultInfo;
    }

    /**
     * (1) 异常、退运、拍照保存
     * (2) 数据存储到 exception表
     */
    public ResultInfo upload_exception(ScanDataListEnt scandataPackage) {

        ResultInfo rlt = new ResultInfo();
        rlt.setSuccess(0);
        SqlSession sen = null;
        try {

            // 禁止自动提交数据修改
            sen = MyBatisUtil.getSqlSession(false);
            SqlMapper sqlMapper = new SqlMapper(sen);

            Scan_DetailDao sdo = new Scan_DetailDao();

            rlt = sdo.save_data_exception(scandataPackage, sen, rlt, "2");
            if (rlt.getSuccess()!=0) {
                sen.rollback();
                rlt.setSuccess(-1);
                rlt.setData(null);
                rlt.setCode("4004");
                return rlt;
            }

            if (rlt.getSuccess()==0){

                rlt.setCode("1001");
                rlt.setMessage("数据提交成功");
                rlt.setData(null);
            }else{

                sen.rollback();
                rlt.setSuccess(-1);
                rlt.setData(null);
                rlt.setCode("4004");
                return rlt;
            }

            sdo = null;
            sen.commit();
        } catch (Exception ex) {
            logger.error(Inner_log_Errcode + "upload()函数,异常:" + ex.getMessage());

            sen.rollback();
            rlt.setSuccess(-1);
            rlt.setMessage("数据提交失败!");
            rlt.setData(null);
            rlt.setCode("4004");
            return rlt;
        }

        return rlt;
    }

    /**
     * (1) 退运
     * (2) 数据存储到 exception表
     */
    public ResultInfo upload_back(ScanDataListEnt scandataPackage) {

        ResultInfo rlt = new ResultInfo();
        rlt.setSuccess(0);
        SqlSession sen = null;
        boolean trans_flag = true;//提交事务成功标记
        try {

            // 禁止自动提交数据修改
            sen = MyBatisUtil.getSqlSession(false);
            SqlMapper sqlMapper = new SqlMapper(sen);

            Scan_DetailDao sdo = new Scan_DetailDao();

            rlt = sdo.save_data_exception(scandataPackage, sen, rlt, "1");

            if (rlt.getSuccess() == 0){

                rlt.setCode("1001");
                rlt.setMessage("数据提交成功");
                rlt.setData(null);
            }else{

                sen.rollback();
                rlt.setSuccess(-1);
                rlt.setData(null);
                rlt.setCode("4004");
                return rlt;
            }

            sdo = null;

        } catch (Exception ex) {
            logger.error(Inner_log_Errcode + "upload_back()函数,异常:" + ex.getMessage());

            sen.rollback();
            rlt.setSuccess(-1);
            rlt.setMessage("数据提交失败");
            rlt.setData(null);
            rlt.setCode("4004");
            return rlt;
        }

        // 正确则提交事务
        sen.commit();

        return rlt;
    }

    /**
     * (1)单件录入
     * (2) 数据存储到
     */
    public ResultInfo upload_single(ScanDataListEnt scandataPackage) {

        ResultInfo rlt = new ResultInfo();
        rlt.setSuccess(0);
        SqlSession sen = null;
        boolean trans_flag = true;//提交事务成功标记
        try {

            // 禁止自动提交数据修改
            sen = MyBatisUtil.getSqlSession(false);
            SqlMapper sqlMapper = new SqlMapper(sen);

            Scan_DetailDao sdo = new Scan_DetailDao();

            rlt = sdo.save_data_single(scandataPackage, sen, rlt);

            if (rlt.getSuccess() == 0){

                rlt.setCode("1001");
                rlt.setMessage("数据提交成功");
                rlt.setData(null);
            }else{

                sen.rollback();
                rlt.setSuccess(-1);
                rlt.setData(null);
                rlt.setCode("4004");
                return rlt;
            }

            sdo = null;

        } catch (Exception ex) {
            logger.error(Inner_log_Errcode + "upload_single()函数,异常:" + ex.getMessage());

            sen.rollback();
            rlt.setSuccess(-1);
            rlt.setMessage("数据提交失败");
            rlt.setData(null);
            rlt.setCode("4004");
            return rlt;
        }

        // 正确则提交事务
        sen.commit();

        return rlt;
    }

    /**
     * (1) 拍照模块图片保存
     * (2)
     */
    public ResultInfo upload_takephoto(ScanDataListEnt scandataPackage) {

        ResultInfo rlt = new ResultInfo();
        rlt.setSuccess(0);
        SqlSession sen = null;
        boolean trans_flag = true;//提交事务成功标记
        try {

            // 禁止自动提交数据修改
            sen = MyBatisUtil.getSqlSession(false);
            SqlMapper sqlMapper = new SqlMapper(sen);

            Scan_DetailDao sdo = new Scan_DetailDao();

            rlt = sdo.save_data_exception(scandataPackage, sen, rlt, "3");//拍照

            if (rlt.getSuccess() == 0){

                rlt.setCode("1001");
                rlt.setMessage("数据提交成功");
                rlt.setData(null);
            }else{

                sen.rollback();
                rlt.setSuccess(-1);
                rlt.setData(null);
                rlt.setCode("4004");
                return rlt;
            }

            sdo = null;

        } catch (Exception ex) {
            logger.error(Inner_log_Errcode + "upload_takephoto()函数,异常:" + ex.getMessage());

            sen.rollback();
            rlt.setSuccess(-1);
            rlt.setMessage("数据提交失败");
            rlt.setData(null);
            rlt.setCode("4004");
            return rlt;
        }

        // 正确则提交事务
        sen.commit();

        return rlt;
    }

    /**
     * 打尺确认，微信端扫码发起
     * @param actionRequest
     * @return
     */
    public ResultInfo update_ruler_state(ActionRequest actionRequest){

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(0);
        resultInfo.setCode("1000");
        resultInfo.setMessage("提交成功");

        Scan_DetailDao sdo = new Scan_DetailDao();

        resultInfo = sdo.updateRulerStatus(actionRequest, resultInfo);

        return resultInfo;
    }
}
