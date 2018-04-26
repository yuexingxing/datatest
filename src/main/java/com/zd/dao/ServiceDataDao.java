package com.zd.dao;

import com.zd.common.ConvertDataSchemaUtils;
import com.zd.object.ActionRequest;
import com.zd.object.ResultInfo;
import com.zd.object.ScanDataListEnt;
import com.zd.pojo.*;
import com.zd.util.SqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 服务类数据保存
 * Created by Administrator on 2017-06-05.
 */
public class ServiceDataDao {

    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(ServiceDataDao.class);

    /**
     * 检验
     */
    public ResultInfo save_data_verify(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Verify data = ConvertDataSchemaUtils.Conver2Scan_Detail_Verify(scanData, dataEnt);

            // 检查数据是否重复:
            String id = data.getScan_detail_id();
            String SQL = "select count(1) as cnt from Scan_Detail_Verify where Scan_Detail_Id = #{id}";

            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL = " insert into Scan_Detail_Verify(" +
                    "scan_detail_id,scan_id,scan_time,main_goods_id,pack_no," +
                    "pack_barcode,verify_comp_id,verify_comp_name," +
                    "verify_user_id,verify_user_name,verify_result," +
                    "gc_flag," +
                    "Created_UserID,Created_User,Created_Time," +
                    "updated_userid,updated_user,updated_time) " +
                    "values(" +
                    "#{scan_detail_id},#{scan_id},#{scan_time},#{main_goods_id},#{pack_no}," +
                    "#{pack_barcode},#{verify_comp_id},#{verify_comp_name}," +
                    "#{verify_user_id},#{verify_user_name},#{verify_result}," +
                    "#{gc_flag}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time})";

            int insertCnt = sqlMapper.insert(SQL, data);

            logger.debug("save_data_verify.insertCnt(插入记录数量)=" + insertCnt);

            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_verify()异常:" + ex.getMessage());
            logger.error("save_data_verify()异常:" + ex.getLocalizedMessage());
        }

        return resultInfo;
    }

    /**
     * 打尺
     */
    public ResultInfo save_data_scale(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Scale data = ConvertDataSchemaUtils.Conver2Scan_Detail_Scale(scanData, dataEnt);

            // 检查数据是否重复:
            String id = data.getScan_detail_id();
            String SQL = "select count(1) as cnt from Scan_Detail_Scale where Scan_Detail_Id = #{id}";

            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL = " insert into Scan_Detail_Scale(" +
                    "scan_detail_id,scan_id,scan_time,main_goods_id,pack_no," +
                    "pack_barcode,scale_company_id,scale_company_name," +
                    "goods_len,goods_width,goods_height,gross_weight,charge_ton," +
                    "scale_user_id,scale_user_name,gc_flag,Supervise_Name," +
                    "Created_UserID,Created_User,Created_Time," +
                    "updated_userid,updated_user,updated_time)" +
                    "values(" +
                    "#{scan_detail_id},#{scan_id},#{scan_time},#{main_goods_id},#{pack_no}," +
                    "#{pack_barcode},#{scale_company_id},#{scale_company_name}," +
                    "#{goods_len},#{goods_width},#{goods_height},#{gross_weight},#{charge_ton}," +
                    "#{scale_user_id},#{scale_user_name},#{gc_flag},#{Supervise_Name}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time})";

            int insertCnt = sqlMapper.insert(SQL, data);

            logger.debug("save_data_scale.insertCnt(插入记录数量)=" + insertCnt);

            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_scale()异常:" + ex.getMessage());
            logger.error("save_data_scale()异常:" + ex.getLocalizedMessage());
        }

        return resultInfo;
    }

    /**
     * 包装----总箱单
     */
    public ResultInfo save_data_pack_main(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Pack_Main data = ConvertDataSchemaUtils.Conver2Scan_Detail_Pack_Main(scanData, dataEnt);

            // 检查数据是否重复:
            String id = data.getID();
            String SQL = "select count(1) as cnt from Main_ContainerGoods where ID = #{id}";

            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL = " insert into Main_ContainerGoods(" +
                    "ID,Project,Pack_BarCode,Pack_No,Company_ID,Goods_Name," +
                    "Product_Name,Packing," +
                    "Created_UserID,Created_User,Created_Time,GC_Flag," +
                    "updated_user,updated_time) " +
                    "values(" +
                    "#{ID},#{Project},#{Pack_BarCode},#{Pack_No},#{Company_ID},#{Goods_Name}," +
                    "#{Product_Name},#{Packing}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time},#{GC_Flag}," +
                    "#{Created_User},#{Created_Time})";

            int insertCnt = sqlMapper.insert(SQL, data);

            logger.debug("save_data_pack_main.insertCnt(插入记录数量)=" + insertCnt);

            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_pack_main()异常:" + ex.getMessage());
            logger.error("save_data_pack_main()异常:" + ex.getLocalizedMessage());
        }

        return resultInfo;
    }


    /**
     * 包装
     */
    public ResultInfo save_data_pack(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Pack data = ConvertDataSchemaUtils.Conver2Scan_Detail_Pack(scanData, dataEnt);

            // 检查数据是否重复:
            String id = data.getScan_Detail_ID();
            String SQL = "select count(1) as cnt from Scan_Detail_Pack where Scan_Detail_Id = #{id}";

            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL = " insert into Scan_Detail_Pack(" +
                    "Scan_Detail_ID,Scan_ID,Scan_Time,Part_Goods_ID,Goods_No,Goods_BarCode," +
                    "Pack_Company_ID,Pack_Company_Name,Pack_Info," +
                    "Pack_Pattern,Product_Name," +
                    "Created_UserID,Created_User,Created_Time,GC_Flag," +
                    "updated_userid,updated_user,updated_time) " +
                    "values(" +
                    "#{Scan_Detail_ID},#{Scan_ID},#{Scan_Time},#{Part_Goods_ID},#{Goods_No},#{Goods_BarCode}," +
                    "#{Pack_Company_ID},#{Pack_Company_Name},#{Pack_Info}," +
                    "#{Pack_Pattern},#{Product_Name}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time},#{GC_Flag}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time})";

            int insertCnt = sqlMapper.insert(SQL, data);

            logger.debug("save_data_pack.insertCnt(插入记录数量)=" + insertCnt);

            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_pack()异常:" + ex.getMessage());
            logger.error("save_data_pack()异常:" + ex.getLocalizedMessage());
        }

        return resultInfo;
    }

    /**
     * 包装--更新分箱单包装号码
     */
    public ResultInfo update_data_pack(ActionRequest actionRequest, String packNo, SqlSession sen, ResultInfo resultInfo) {

        resultInfo.setSuccess(0);
        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            String SQL = " UPDATE dbo.Part_ContainerGoods " +
                    " SET Pack_BNo = '" + packNo + "' " +
                    " WHERE Goods_BarCode = #{pack_barcode} " +
                    " AND Goods_No = #{pack_no} " +
                    " AND Project = #{project_id} ";

            int insertCnt = sqlMapper.update(SQL, actionRequest);

            logger.debug("update_data_pack.insertCnt(插入记录数量)=" + insertCnt);

            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("update_data_pack()异常:" + ex.getMessage());
            logger.error("update_data_pack()异常:" + ex.getLocalizedMessage());
        }

        return resultInfo;
    }

    /**
     * 绑扎
     */
    public ResultInfo save_data_strap(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Strap data = ConvertDataSchemaUtils.Conver2Scan_Detail_Strap(scanData, dataEnt);

            // 检查数据是否重复:
            String id = data.getScan_Detail_ID();
            String SQL = "select count(1) as cnt from Scan_Detail_Strap where Scan_Detail_Id = #{id}";

            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL = " insert into Scan_Detail_Strap(" +
                    "Scan_Detail_ID,Scan_ID,Scan_Time,Main_Goods_ID,Pack_No," +
                    "Pack_Barcode,Strap_Company_ID,Strap_Company_Name," +
                    "Strap_User_ID,Strap_User_Name,GC_Flag," +
                    "Created_UserID,Created_User,Created_Time," +
                    "updated_userid,updated_user,updated_time)" +
                    "values(" +
                    "#{Scan_Detail_ID},#{Scan_ID},#{Scan_Time},#{Main_Goods_ID},#{Pack_No}," +
                    "#{Pack_Barcode},#{Strap_Company_ID},#{Strap_Company_Name}," +
                    "#{Strap_User_ID},#{Strap_User_Name},#{GC_Flag}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time})";

            int insertCnt = sqlMapper.insert(SQL, data);

            logger.debug("save_data_strap.insertCnt(插入记录数量)=" + insertCnt);

            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_strap()异常:" + ex.getMessage());
            logger.error("save_data_strap()异常:" + ex.getLocalizedMessage());
        }

        return resultInfo;
    }


    /**
     * 下线
     */
    public ResultInfo save_data_offline(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Offline data = ConvertDataSchemaUtils.Conver2Scan_Detail_Offline(scanData, dataEnt);

            // 检查数据是否重复:
            String id = data.getScan_Detail_ID();
            String SQL = "select count(1) as cnt from Scan_Detail_Offline where Scan_Detail_Id = #{id}";

            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL = " insert into Scan_Detail_Offline(" +
                    "Scan_Detail_ID,Scan_ID,Scan_Time,Part_Goods_ID,Goods_No," +
                    "Goods_BarCode,Goods_Len,Goods_Width," +
                    "Goods_Height,Product_Name," +
                    "Offline_Info,CheckUser_ID,CheckUser_Name,GC_Flag," +
                    "Created_UserID,Created_User,Created_Time," +
                    "updated_userid,updated_user,updated_time)" +
                    "values(" +
                    "#{Scan_Detail_ID},#{Scan_ID},#{Scan_Time},#{Part_Goods_ID},#{Goods_No}," +
                    "#{Goods_BarCode},#{Goods_Len},#{Goods_Width}," +
                    "#{Goods_Height},#{Product_Name}," +
                    "#{Offline_Info},#{CheckUser_ID},#{CheckUser_Name},#{GC_Flag}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time})";

            int insertCnt = sqlMapper.insert(SQL, data);

            logger.debug("save_data_offline.insertCnt(插入记录数量)=" + insertCnt);

            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_offline()异常:" + ex.getMessage());
            logger.error("save_data_offline()异常:" + ex.getLocalizedMessage());
        }

        return resultInfo;
    }

    /**
     * 安装
     */
    public ResultInfo save_data_install(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Install data = ConvertDataSchemaUtils.Conver2Scan_Detail_Install(scanData, dataEnt);

            // 检查数据是否重复:
            String id = data.getScan_Detail_ID();
            String SQL = "select count(1) as cnt from Scan_Detail_Install where Scan_Detail_Id = #{id}";

            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL = " insert into Scan_Detail_Install(" +
                    "Scan_Detail_ID,Scan_ID,Scan_Time,Part_Goods_ID,Goods_No," +
                    "Goods_BarCode,Product_Name,Installer_ID," +
                    "Installer_Name,Install_Info,GC_Flag," +
                    "Created_UserID,Created_User,Created_Time," +
                    "updated_userid,updated_user,updated_time)" +
                    "values(" +
                    "#{Scan_Detail_ID},#{Scan_ID},#{Scan_Time},#{Part_Goods_ID},#{Goods_No}," +
                    "#{Goods_BarCode},#{Product_Name},#{Installer_ID}," +
                    "#{Installer_Name},#{Install_Info},#{GC_Flag}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time}," +
                    "#{Created_UserID},#{Created_User},#{Created_Time})";

            int insertCnt = sqlMapper.insert(SQL, data);

            logger.debug("save_data_install.insertCnt(插入记录数量)=" + insertCnt);

            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_install()异常:" + ex.getMessage());
            logger.error("save_data_install()异常:" + ex.getLocalizedMessage());
        }

        return resultInfo;
    }


    /**
     * 打尺--获取原始数据进行对比
     * @param request
     * @param sen
     * @param resultInfo
     * @return
     */
    public ResultInfo getOldScaleData(ActionRequest request, SqlSession sen, ResultInfo resultInfo){

        SqlMapper sqlMapper = new SqlMapper(sen);

        try {
            String SQL = " SELECT ID, Pack_No, Pack_BarCode, Length, Width, Height, Gross_Weight, Charge_Ton " +
                    " FROM dbo.Main_ContainerGoods" +
                    " WHERE Pack_BarCode = #{pack_barcode}" +
                    " AND Project = #{project_id}";

            Map<String, Object> map = sqlMapper.selectOne(SQL, request);
            if(map != null && map.size() > 0){

                //计算体积
                double Length = Double.parseDouble(map.get("Length").toString()) / 1000.0;
                double Width = Double.parseDouble(map.get("Width").toString()) / 1000.0;
                double Height = Double.parseDouble(map.get("Height").toString()) / 1000.0;

                double V3 = Length * Width * Height;
                map.put("V3", String.format("%.2f", V3));//m³

                resultInfo.setSuccess(0);
                resultInfo.setMessage("查询成功");
                resultInfo.setData(map);
            }else{
                resultInfo.setSuccess(1);
                resultInfo.setMessage("未查到有效数据");
                resultInfo.setData(null);
            }

        }catch(Exception ex){
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("getOldScaleData()异常:" + ex.getMessage());
            logger.error("getOldScaleData()异常:" + ex.getLocalizedMessage());
        }

        return  resultInfo;
    }

    /**
     * 打尺--获取原始数据进行对比
     * @param request
     * @param sqlMapper
     * @return
     */
    public Map<String, Object> getScaleData(ActionRequest request, SqlMapper sqlMapper){

        Map<String, Object> map = null;

        try {
            String SQL = " SELECT top(1) ID, Pack_No, Pack_BarCode, Length as length, Width as width, " +
                    " Height as height, Gross_Weight as weight, Charge_Ton as charge_ton, Volume as v3 " +
                    " FROM dbo.Main_ContainerGoods" +
                    " WHERE Pack_BarCode = #{pack_barcode}" +
                    " AND Project = #{project_id}";

            map = sqlMapper.selectOne(SQL, request);
        }catch(Exception ex){
            ex.printStackTrace();
            logger.error("getScaleData()异常:" + ex.getLocalizedMessage());
        }

        return  map;
    }

    /**
     * 包装方式
     * @param request
     * @param sen
     * @param resultInfo
     * @return
     */
    public ResultInfo getPackWayData(ActionRequest request, SqlSession sen, ResultInfo resultInfo){

        SqlMapper sqlMapper = new SqlMapper(sen);

        try {
            String SQL = "SELECT A.ID as id, a.Text as pack_way" +
                    "  FROM COM_DataDictionaryInfo A " +
                    " WHERE A.ID_Parent = (SELECT ID FROM COM_DataDictionaryInfo" +
                    " WHERE TEXT = '包装方式')";

            List<Map<String, Object>> list = sqlMapper.selectList(SQL, request);
            if(list != null && list.size() > 0){

                resultInfo.setSuccess(0);
                resultInfo.setMessage("查询成功");
                resultInfo.setData(list);
            }else{
                resultInfo.setSuccess(1);
                resultInfo.setMessage("未查到有效数据");
                resultInfo.setData(null);
            }

        }catch(Exception ex){
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("getPackWayData()异常:" + ex.getMessage());
            logger.error("getPackWayData()异常:" + ex.getLocalizedMessage());
        }

        return  resultInfo;
    }

    /**
     * 更新主表条码及相关信息
     * @param request
     * @param sen
     * @param resultInfo
     * @return
     */
    public ResultInfo updateStickBarcode(ActionRequest request, SqlSession sen, ResultInfo resultInfo){

        SqlMapper sqlMapper = new SqlMapper(sen);

        try {
            String SQL = " UPDATE  dbo.Main_ContainerGoods" +
                    " SET    Pack_BarCode = #{pack_barcode} ," +
                    "        Updated_User = #{user_name} ," +
                    "        Updated_Time = #{time}" +
                    " WHERE  ID = #{id}";

            int count = sqlMapper.update(SQL, request);
            if(count > 0){

                resultInfo.setSuccess(0);
                resultInfo.setMessage("更新成功");
            }else{
                resultInfo.setSuccess(1);
                resultInfo.setMessage("更新失败");
            }

        }catch(Exception ex){
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("updateStickBarcode()异常:" + ex.getMessage());
            logger.error("updateStickBarcode()异常:" + ex.getLocalizedMessage());
        }

        return  resultInfo;
    }
}
