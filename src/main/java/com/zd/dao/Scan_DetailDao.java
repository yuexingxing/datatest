package com.zd.dao;

import com.zd.common.ConvertDataSchemaUtils;
import com.zd.object.*;
import com.zd.pojo.*;
import com.zd.service.ActionServiceImpl;
import com.zd.util.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * 扫描数据保存
 */
public class Scan_DetailDao {

    // 内部错误编码,写入到日志,用于跟踪错误的
    private static final String Inner_log_Errcode = "【Scan_DetailDao-ERR】-";

    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(Scan_DetailDao.class);

    /**
     * 数据保存处理
     */
    public ResultInfo save_data(ActionRequest actionRequest, ScanDataListEnt dataEnt, SqlSession sen) {

        Scan_DetailDao scan_detailDao = new Scan_DetailDao();
        ServiceDataDao serviceDataDao = new ServiceDataDao();

        ResultInfo resultInfo  = new ResultInfo();
        resultInfo.setSuccess(0);
        resultInfo.setMessage("提交成功");

        try {

            int len  = dataEnt.getDetail().size();
            for (int ix = 0; ix < len; ix++) {

                ScanData scanData = dataEnt.getDetail().get(ix);

                actionRequest.setTask_name(scanData.getTaskName());
                actionRequest.setLoad_task_id(scanData.getTaskId());

                resultInfo = CommandTools.getTaskId(actionRequest, sen, resultInfo);//每次获取task_id
                if (resultInfo.getSuccess() == 0){
                    scanData.setTaskId(resultInfo.getCode());
                }else{
                    return resultInfo;
                }

                resultInfo = scan_detailDao.save_data_scan(dataEnt, scanData, sen, resultInfo);
                if (resultInfo.getSuccess()!=0) {
                    sen.rollback();
                    resultInfo.setSuccess(-1);
                    resultInfo.setCode("4004");
                    resultInfo.setData(null);
                    return resultInfo;
                }

                resultInfo = scan_detailDao.save_data_detail(scanData, dataEnt, sen, resultInfo);
                if (resultInfo.getSuccess()!=0) {
                    sen.rollback();
                    resultInfo.setSuccess(-1);
                    resultInfo.setCode("4004");
                    resultInfo.setData(null);
                    return resultInfo;
                }

                /**************************************
                 *              七大模块数据保存
                 *************************************/
                //空运
                if (dataEnt.getScan_type().toLowerCase().trim().equals("air")) {
                    resultInfo = save_data_air(actionRequest, dataEnt, scanData, sen, resultInfo);
                }
                //陆运
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("land")) {
                    resultInfo = save_data_car(actionRequest, dataEnt, scanData, sen, resultInfo);
                }
                //铁运
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("railway")) {
                    resultInfo = save_data_railway(actionRequest, dataEnt, scanData, sen, resultInfo);
                }
                //海运
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("sea")) {
                    resultInfo = save_data_sea(actionRequest, dataEnt, scanData, sen, resultInfo);
                }
                //集装箱
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("container")) {
                    resultInfo = save_data_container(actionRequest, dataEnt, scanData, sen, resultInfo);
                }
                //货场
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("storage")) {
                    resultInfo = save_data_storage(actionRequest, dataEnt, scanData,  sen, resultInfo);
                }
                //装车,卸车
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("load")) {
                    resultInfo = save_data_load(actionRequest, dataEnt, scanData, sen, resultInfo);
                }


                /**************************************
                 *              服务类数据保存
                 *************************************/
                //检验
                else  if (dataEnt.getScan_type().toLowerCase().trim().equals("verify")) {
                    resultInfo = serviceDataDao.save_data_verify(actionRequest, dataEnt, scanData, sen, resultInfo);
                }
                //打尺
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("scale")) {
                    resultInfo = serviceDataDao.save_data_scale(actionRequest, dataEnt, scanData, sen, resultInfo);
                }
                //包装
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("pack")) {

                    resultInfo = serviceDataDao.save_data_pack(actionRequest, dataEnt, scanData, sen, resultInfo);
                    if(resultInfo.getSuccess() != 0){
                        return resultInfo;
                    }

                    actionRequest.setPack_barcode(scanData.getMinutePackBarcode());//总条码
                    actionRequest.setPack_no(scanData.getMinutePackNumber());//总箱单号

                    resultInfo = serviceDataDao.update_data_pack(actionRequest, scanData.getPackNumber(), sen, resultInfo);
                    if(resultInfo.getSuccess() != 0){
                        return resultInfo;
                    }

                    resultInfo = new ActionServiceImpl().singleCheckBarcode(actionRequest);
                    if(resultInfo.getSuccess() == 0){
                        ScanData scanData1 = dataEnt.getDetail().get(0);
                        scanData1.setGoodsName(scanData1.getPackName());//为了保持单件录入取值的统一，这里进行赋值处理
                        new Scan_DetailDao().save_data_single(dataEnt, sen, resultInfo);
                    }else{
                        resultInfo.setSuccess(0);
                        resultInfo.setMessage("提交成功");
                    }

                }
                //绑扎
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("strap")) {
                    resultInfo = serviceDataDao.save_data_strap(actionRequest, dataEnt, scanData, sen, resultInfo);
                }
                //下线
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("offline")) {
                    resultInfo = serviceDataDao.save_data_offline(actionRequest, dataEnt, scanData, sen, resultInfo);
                }
                //安装
                else if (dataEnt.getScan_type().toLowerCase().trim().equals("install")) {
                    resultInfo = serviceDataDao.save_data_install(actionRequest, dataEnt, scanData,  sen, resultInfo);
                }
            }

        } catch (Exception ex) {
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data()异常:" + ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * 贴唛数据保存
     */
    public ResultInfo save_data_stick(ActionRequest actionRequest, ScanDataListEnt dataEnt, SqlSession sen) {

        Scan_DetailDao scan_detailDao = new Scan_DetailDao();
        ServiceDataDao serviceDataDao = new ServiceDataDao();

        ResultInfo resultInfo  = new ResultInfo();
        try {

            int len  = dataEnt.getDetail().size();
            for (int ix = 0; ix < len; ix++) {

                ScanData scanData = dataEnt.getDetail().get(ix);

                actionRequest.setId(scanData.getMainGoodsId());
                actionRequest.setPack_barcode(scanData.getPackBarcode());
                actionRequest.setTime(CommandTools.getTime());

                actionRequest.setTask_name(scanData.getTaskName());
                actionRequest.setLoad_task_id(scanData.getTaskId());

                resultInfo = CommandTools.getTaskId(actionRequest, sen, resultInfo);//每次获取task_id
                if (resultInfo.getSuccess() == 0) {
                    scanData.setTaskId(resultInfo.getCode());
                } else {
                    return resultInfo;
                }

                resultInfo = scan_detailDao.save_data_scan(dataEnt, scanData, sen, resultInfo);
                if (resultInfo.getSuccess() != 0) {
                    sen.rollback();
                    resultInfo.setSuccess(-1);
                    resultInfo.setCode("4004");
                    resultInfo.setData(null);
                    return resultInfo;
                }

                resultInfo = scan_detailDao.save_data_detail(scanData, dataEnt, sen, resultInfo);
                if (resultInfo.getSuccess() != 0) {
                    sen.rollback();
                    resultInfo.setSuccess(-1);
                    resultInfo.setCode("4004");
                    resultInfo.setData(null);
                    return resultInfo;
                }

                resultInfo = new ServiceDataDao().updateStickBarcode(actionRequest, sen, resultInfo);
            }
        } catch (Exception ex) {
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_stick()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_stick()异常:" + ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * 保存到Route_Task,表
     * 该操作只对陆运有效，其他类型不需要保存
     */
    public ResultInfo save_data_route_task(ScanDataListEnt dataEnt, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);

        try {
            ScanData scanData = dataEnt.getDetail().get(0);

            String node_id = dataEnt.getHeader().getRoute_points_id();
            String SQL1 = "select rpt.Route_Points_Code+ right(convert(varchar,1001+convert(int,isnull(max(" +
                    " REPLACE(rtk.Task_Code, rpt.Route_Points_Code,'')),'0'))),3) Task_Code" +
                    " from Route_Points rpt left join Route_Task rtk on rpt.ID = rtk.route_points_id " +
                    " and ISNUMERIC(REPLACE(rtk.Task_Code, rpt.Route_Points_Code,''))=1" +
                    " where rpt.ID = #{node_id}" +
                    " GROUP BY rpt.Route_Points_Code";
            Map<String, Object> map_node = sqlMapper.selectOne(SQL1, node_id);
            String task_code = map_node.get("Task_Code").toString();

            RouteTaskInfo data = ConvertDataSchemaUtils.Conver2Scan_RouteTask(scanData, dataEnt, task_code);

            // 检查数据是否重复:
            String id = data.getId();
            String SQL = "select count(1) as cnt from Route_Task where ID = #{id}";
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                // 重复则跳过;
                return resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL =   " insert into Route_Task(" +
                    " ID,Task_Code,Task_Name,route_points_id,Created_UserID," +
                    " Created_User,Created_Time,GC_Flag,Be_Plan) " +
                    " values(" +
                    "#{id},#{task_code},#{task_name},#{route_points_id},#{created_userid}," +
                    "#{created_user},#{created_time},#{gc_flag},#{be_plan})";

            int insertCnt = sqlMapper.insert(SQL, data);

            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);

        } catch (Exception ex) {
            logger.error(Inner_log_Errcode + "save_data_route_task()异常:" + ex.getMessage());
            resultInfo.setSuccess(-1);
            resultInfo.setMessage("save_data_route_task()异常");
        }

        return resultInfo;
    }

    /**
     * 数据保存到 Scan 表
     */
    public ResultInfo save_data_scan(ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {
        boolean saveResult = true;
        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            // 检查数据是否重复:
            String id = scanData.getScan_id();
            String SQL = "select count(1) as cnt from Scan where id = #{id}";
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            // 检查有无,没有则插入一条,有则update.
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                resultInfo.setSuccess(1);
                return resultInfo;
            }

            // 数据结构拷贝到 Scan_Detail 中;
            Header header = ConvertDataSchemaUtils.Conver2Scan(dataEnt, scanData);

            // 维护与主表的对应关系(scan.id = scan_detaul.scan_id);
            // 根据 scan_detail_ent 构造插入语句,执行.
            SQL = " insert into Scan(" +
                    "id,route_task_id,upload_time,load_number,link_no," +
                    "device_id," +
                    "route_points_id,gc_flag," +
                    "created_userid,created_user,created_time," +
                    "updated_userid,updated_user,updated_time) " +
                    " values (" +
                    "#{id},#{route_task_id},#{upload_time},#{load_number},#{link_no}," +
                    "#{device_id}," +
                    "#{route_points_id},#{gc_flag}," +
                    "#{scan_user_id},#{scan_user},#{scan_time}," +
                    "#{scan_user_id},#{scan_user},#{scan_time})";

            int insertCnt = sqlMapper.insert(SQL, header);

            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);

            saveResult = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(Inner_log_Errcode + "save_data_scan()异常:" + ex.getMessage());
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_scan()异常:" + ex.getMessage());
            saveResult = false;
        }

        return resultInfo;
    }

    /**
     * 数据保存到 Scan_Detail 表
     */
    public ResultInfo save_data_detail(ScanData scanData, ScanDataListEnt dataEnt, SqlSession sen, ResultInfo resultInfo) {
        boolean saveResult = true;
        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            String id = scanData.getCacheId();

            // 检查数据是否重复:
            String SQL = "select count(1) as cnt from Scan_Detail where id = #{id}";
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            // 检查有无,没有则插入一条,有则update.
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                resultInfo.setSuccess(1);
                return resultInfo;
            }

            // 数据结构拷贝到 Scan_Detail 中;
            Scan_Detail scan_detail_ent = ConvertDataSchemaUtils.Conver2Scan_Detail(scanData, dataEnt);

            // 维护与主表的对应关系(scan.id = scan_detaul.scan_id);
            // 根据 scan_detail_ent 构造插入语句,执行.
            SQL = " insert into Scan_Detail(" +
                    "id,scan_id,scan_time,goods_type,goods_id," +
                    "goods_code,goods_barcode,gps_coordx,gps_coordy,gc_flag," +
                    "Task_Param_1,Task_Param_2,Task_Param_3," +
                    "created_userid,created_user,created_time," +
                    "updated_userid,updated_user,updated_time) " +
                    " values (" +
                    "#{id},#{scan_id},#{scan_time},#{goods_type},#{goods_id}," +
                    "#{goods_code},#{goods_barcode},#{gps_coordx},#{gps_coordy},#{gc_flag}," +
                    "#{task_param_1},#{task_param_2},#{task_param_3}," +
                    "#{created_userid},#{created_user},#{created_time}," +
                    "#{created_userid},#{created_user},#{created_time})";

            int insertCnt = sqlMapper.insert(SQL, scan_detail_ent);

            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);

            saveResult = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_detail()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_detail()异常:" + ex.getMessage());
            saveResult = false;
        }

        return resultInfo;
    }

    /**
     * 抵达模块数据保存
     * 数据保存到 Scan_Detail 表
     */
    public ResultInfo save_data_detail_arrive(ScanDataListEnt dataEnt, SqlSession sen, ResultInfo resultInfo) {
        boolean saveResult = true;
        SqlMapper sqlMapper = new SqlMapper(sen);
        try {
            for (int ix = 0; ix < dataEnt.getDetail().size(); ix++) {

                ScanData scanData = dataEnt.getDetail().get(ix);
                String id = scanData.getCacheId();

                // 检查数据是否重复:
                String SQL = "select count(1) as cnt from Scan_Detail where id = #{id}";
                Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
                // 检查有无,没有则插入一条,有则update.
                Object cnt = rlt_one.get("cnt");
                int li_cnt = Integer.parseInt(cnt.toString());
                if (li_cnt > 0) {
                    // 重复则跳过;
                    continue;
                }

                // 数据结构拷贝到 Scan_Detail 中;
                Scan_Detail scan_detail_ent = ConvertDataSchemaUtils.Conver2Scan_Detail(scanData, dataEnt);

                // 维护与主表的对应关系(scan.id = scan_detaul.scan_id);
                // 根据 scan_detail_ent 构造插入语句,执行.
                SQL = " insert into Scan_Detail(" +
                        "id,scan_id,scan_time,goods_type," +
                        "goods_code,goods_barcode,gps_coordx,gps_coordy,gc_flag," +
                        "Task_Param_1,Task_Param_2,Task_Param_3," +
                        "created_userid,created_user,created_time," +
                        "updated_userid,updated_user,updated_time) " +
                        " values (" +
                        "#{id},#{scan_id},#{scan_time},#{goods_type}," +
                        "#{goods_code},#{goods_barcode},#{gps_coordx},#{gps_coordy},#{gc_flag}," +
                        "#{task_param_1}, #{task_param_2},#{task_param_3}," +
                        "#{created_userid},#{created_user},#{created_time}," +
                        "#{created_userid},#{created_user},#{created_time})";

                int insertCnt = sqlMapper.insert(SQL, scan_detail_ent);

                logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

                // 方便debug窗口查看的;
                System.out.println("insertCnt = " + insertCnt);

                saveResult = true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_detail()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_detail()异常:" + ex.getMessage());
            saveResult = false;
        }

        return resultInfo;
    }

    /**
     * object.Detail （数据灌入）==>  Scan_Detail_Air
     */
    public ResultInfo save_data_air(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Air air_ent = ConvertDataSchemaUtils.Conver2Scan_Detail_Air(scanData, dataEnt);

            // 检查数据是否重复:
            String id = air_ent.getScan_detail_id();
            String SQL = "select count(1) as cnt from Scan_Detail_Air where Scan_Detail_Id = #{id}";

            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL = " insert into scan_detail_air(" +
                    "scan_detail_id,scan_ID,scan_time,main_goods_id,pack_no," +
                    "pack_barcode,flight,gc_flag," +
                    "created_userid,created_user,created_time," +
                    "updated_userid,updated_user,updated_time) " + //
                    "values(" +
                    "#{scan_detail_id},#{scan_id},#{scan_time},#{main_goods_id},#{pack_no}," +
                    "#{pack_barcode},#{flight},#{gc_flag}," +
                    "#{created_userid},#{created_user},#{created_time}," +
                    "#{created_userid},#{created_user},#{created_time})"; //

            int insertCnt = sqlMapper.insert(SQL, air_ent);

            logger.debug("sqlMapper.SQL=" + SQL+"\r\n"+air_ent.toString());
            logger.debug("save_data_air.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);

        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_air()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_air()异常:" + ex.getLocalizedMessage());
        }

        return resultInfo;
    }

    /**
     * 【装车、卸车】都进入到 Scan_Detail_Load 表
     */
    public ResultInfo save_data_load(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Load data = ConvertDataSchemaUtils.Conver2Scan_Detail_Load(scanData, dataEnt);

            String id = data.getScan_detail_id();
            String SQL = "select count(1) as cnt from Scan_Detail_Load where Scan_Detail_Id = #{id}";
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return  resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL = " insert into Scan_Detail_Load(" +
                    "Scan_Detail_ID, Scan_ID, Scan_Time, Main_Goods_ID, " +
                    "Pack_No, Pack_Barcode, Load_Comp_ID, Load_Comp_Name," +
                    "GC_Flag, Created_UserID, Created_User, Created_Time," +
                    "updated_userid,updated_user,updated_time) " + //
                    " values(" +
                    "#{scan_detail_id},#{scan_id},#{scan_time},#{main_goods_id}," +
                    "#{pack_no},#{pack_barcode},#{load_comp_id},#{load_comp_name}," +
                    "#{gc_flag},#{created_userid},#{created_user},#{created_time}," +
                    "#{created_userid},#{created_user},#{created_time})";

            int insertCnt = sqlMapper.insert(SQL, data);

            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);

        } catch (Exception ex) {
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_load()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_load()异常:" + ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * 【陆运】都进入到Scan_Detail_Car表
     */
    public ResultInfo save_data_car(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            // 拷贝对象:
            Scan_detail_car car_ent = ConvertDataSchemaUtils.Conver2Scan_Detail_Car(scanData, dataEnt);

            // 检查数据是否重复:
            String Scan_Detail_Id = car_ent.getScan_detail_id();
            String SQL = "select count(1) as cnt from Scan_Detail_Car where Scan_Detail_Id = #{Scan_Detail_Id}";
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, Scan_Detail_Id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                // 重复则跳过;
                return resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL =   " insert into Scan_Detail_Car(" +
                    " Scan_Detail_ID, Scan_ID, Scan_Time, Main_Goods_ID, Pack_No," +
                    " Pack_Barcode, License_Plate, Car_No, Driver_Phone, Remark," +
                    " GC_Flag, Created_UserID, Created_User, Created_Time," +
                    " updated_userid,updated_user,updated_time) " +
                    " values(" +
                    "#{scan_detail_id},#{scan_id},#{scan_time},#{main_goods_id},#{pack_no}," +
                    "#{pack_barcode},#{license_plate},#{car_no}, #{driver_phone},#{remark}," +
                    "#{gc_flag},#{create_userid},#{create_user},#{create_time}," +
                    "#{create_userid},#{create_user},#{create_time}" +
                    ")";

            int insertCnt = sqlMapper.insert(SQL, car_ent);

            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);

        } catch (Exception ex) {
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_car()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_car()异常:" + ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * 【铁运】都进入到Scan_Detail_Railway表
     */
    public ResultInfo save_data_railway(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_RailWay railway_ent = ConvertDataSchemaUtils.Conver2Scan_Detail_RailWay(scanData, dataEnt);

            // 检查数据是否重复:
            String Scan_Detail_Id = railway_ent.getScan_detail_id();
            String SQL = "select count(1) as cnt from Scan_Detail_RailWay where Scan_Detail_Id = #{Scan_Detail_Id}";
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, Scan_Detail_Id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return resultInfo ;
            }

            // 插入记录(左右字段赋值);
            SQL =   " insert into Scan_Detail_RailWay(" +
                    " Scan_Detail_ID, Scan_ID, Scan_Time, Main_Goods_ID, Pack_No," +
                    " Pack_Barcode, Wagon_Code, Train_No, GC_Flag, " +
                    " Created_UserID,Created_User, Created_Time," +
                    " updated_userid,updated_user,updated_time) " +
                    " values(" +
                    "#{scan_detail_id},#{scan_id},#{scan_time},#{main_goods_id},#{pack_no}," +
                    "#{pack_barcode},#{wagon_code},#{train_no}, #{gc_flag}," +
                    "#{create_userid},#{create_user},#{create_time}," +
                    "#{create_userid},#{create_user},#{create_time})";

            int insertCnt = sqlMapper.insert(SQL, railway_ent);

            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);


        } catch (Exception ex) {
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_railway()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_railway()异常:" + ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * 海运
     */
    public ResultInfo save_data_sea(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Sea sea_ent = ConvertDataSchemaUtils.Conver2Scan_Detail_Sea(scanData, dataEnt);

            // 检查数据是否重复:
            String Scan_Detail_Id = sea_ent.getScan_detail_id();
            String SQL = "select count(1) as cnt from Scan_Detail_Freighter where Scan_Detail_Id = #{Scan_Detail_Id}";
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, Scan_Detail_Id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return  resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL =   " insert into Scan_Detail_Freighter(" +
                    " Scan_Detail_ID,Scan_ID, Scan_Time,Main_Goods_ID,Pack_No," +
                    " Pack_Barcode,Freighter_Name,Sailing_No,ClassNumber,GC_Flag," +
                    " Created_UserID, Created_User, Created_Time," +
                    " updated_userid,updated_user,updated_time) " +
                    " values(" +
                    "#{scan_detail_id},#{scan_id},#{scan_time},#{main_goods_id},#{pack_no}," +
                    "#{pack_barcode},#{freighter_name},#{sailing_no},#{class_no},#{gc_flag}," +
                    "#{created_userid},#{created_user},#{created_time}," +
                    "#{created_userid},#{created_user},#{created_time})";

            int insertCnt = sqlMapper.insert(SQL, sea_ent);

            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_sea()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_sea()异常:" + ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * 集装箱
     */
    public ResultInfo save_data_container(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Container sea_ent = ConvertDataSchemaUtils.Conver2Scan_Detail_Container(scanData, dataEnt);

            // 检查数据是否重复:
            String Scan_Detail_Id = sea_ent.getScan_detail_id();
            String SQL = "select count(1) as cnt from Scan_Detail_Container where Scan_Detail_Id = #{Scan_Detail_Id}";
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, Scan_Detail_Id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                // 重复则跳过;
                return  resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL =   " insert into Scan_Detail_Container(" +
                    " Scan_Detail_ID,Scan_ID,Scan_Time,Main_Goods_ID,Pack_No," +
                    " Pack_Barcode,Container_No,Freighter_Name,Sailing_No,GC_Flag," +
                    " Created_UserID,Created_User,Created_Time," +
                    " updated_userid,updated_user,updated_time) " +
                    " values(" +
                    "#{scan_detail_id},#{scan_id},#{scan_time},#{main_goods_id},#{pack_no}," +
                    "#{pack_barcode},#{container_no},#{freighter_name},#{sailing_no},#{gc_flag}," +
                    "#{create_userid},#{create_user},#{create_time}," +
                    "#{create_userid},#{create_user},#{create_time})";

            int insertCnt = sqlMapper.insert(SQL, sea_ent);

            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);

        } catch (Exception ex) {
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_container()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_container()异常:" + ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * 货场
     */
    public ResultInfo save_data_storage(ActionRequest actionRequest, ScanDataListEnt dataEnt, ScanData scanData, SqlSession sen, ResultInfo resultInfo) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Scan_Detail_Storage sea_ent = ConvertDataSchemaUtils.Conver2Scan_Detail_Storage(scanData, dataEnt);

            // 检查数据是否重复:
            String Scan_Detail_Id = sea_ent.getScan_detail_id();
            String SQL = "select count(1) as cnt from Scan_Detail_Storage where Scan_Detail_Id = #{Scan_Detail_Id}";
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, Scan_Detail_Id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                return  resultInfo;
            }

            // 插入记录(左右字段赋值);
            SQL =   " insert into Scan_Detail_Storage(" +
                    " Scan_Detail_ID,Scan_ID,Scan_Time,Main_Goods_ID,Pack_No," +
                    " Pack_Barcode,Storage_No,Storage_Manager,GC_Flag," +
                    " Created_UserID,Created_User,Created_Time," +
                    " updated_userid,updated_user,updated_time) " +
                    " values(" +
                    "#{scan_detail_id},#{scan_id},#{scan_time},#{main_goods_id},#{pack_no}," +
                    "#{pack_barcode},#{storage_no},#{storage_manager},#{gc_flag}," +
                    "#{create_userid},#{create_user},#{create_time}," +
                    "#{create_userid},#{create_user},#{create_time})";

            int insertCnt = sqlMapper.insert(SQL, sea_ent);

            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            resultInfo.setSuccess(1);
            resultInfo.setMessage("save_data_storage()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_storage()异常:" + ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * 退件、异常、拍照保存
     * 1--退运
     * 2--异常
     * 3--拍照
     * 处理逻辑，先根据barcode判断Scan_Exception表中是否存在该数据，存在则删除重新保存
     * 这个应该改为如果存在，则更新数据，后面注意一下
     */
    public ResultInfo save_data_exception(ScanDataListEnt dataEnt, SqlSession sen, ResultInfo resultInfo, String ecp_type) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            Header header = dataEnt.getHeader();
            for (int ix = 0; ix < dataEnt.getDetail().size(); ix++) {

                ScanData scanData = dataEnt.getDetail().get(ix);
                scanData.setScaned(ecp_type);
                resultInfo = UploadPicUtil.savePicture(scanData, dataEnt.getHeader(), sqlMapper, resultInfo);
                if (resultInfo.getSuccess() != 0){
                    return  resultInfo;
                }

                // 拷贝对象:
                ExceptionData data = ConvertDataSchemaUtils.Conver2Scan_Exceptin(scanData, dataEnt, ecp_type);

                // 检查数据是否重复:
                String id = data.getId();
                String SQL = "select count(1) as cnt from Scan_Exception where ID = #{id}";
                Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
                Object cnt = rlt_one.get("cnt");
                int li_cnt = Integer.parseInt(cnt.toString());
                if (li_cnt > 0) {    // 重复则跳过;
                    continue;
                }

                ActionRequest actionRequest = new ActionRequest();
                actionRequest.setPack_barcode(scanData.getPackBarcode());
                actionRequest.setLink_num(Integer.parseInt(header.getLink_no()));
                actionRequest.setBs_type(ecp_type);

                //异常件、退件处理，如果Scan_Exception表存在，则删除
                String SQL2 = " select count(id) " +
                        " FROM Scan_Exception " +
                        " WHERE Pack_Barcode = #{pack_barcode}" +
                        " AND Link_No = #{link_num}" +
                        " AND Exception_Type = #{bs_type}";//用这个字段代替
                Map<String, Object> map2 = sqlMapper.selectOne(SQL2, actionRequest);
                if(map2 != null){

                    String SQL3 = " delete " +
                            " FROM Scan_Exception " +
                            " WHERE Pack_Barcode = #{pack_barcode}" +
                            " AND Link_No = #{link_num}" +
                            " AND Exception_Type = #{bs_type}";//用这个字段代替
                    sqlMapper.delete(SQL3, actionRequest);
                }

                // 插入记录(左右字段赋值);
                SQL =   " insert into Scan_Exception(" +
                        " ID,Route_Points_ID,Link_No,Exception_Type," +
                        " Scan_Time,Main_Goods_ID,Pack_Barcode,Pack_No,Except_Desc," +
                        " GC_Flag,Created_UserID,Created_User,Created_Time," +
                        " Updated_UserID,Updated_User,Updated_Time) " +
                        " values(" +
                        "#{id},#{route_points_id},#{link_no},#{exception_type}," +
                        "#{scan_time},#{main_goods_id},#{pack_barcode},#{pack_no},#{except_desc}," +
                        "#{gc_flag},#{create_userid},#{create_user},#{create_time}," +
                        "#{create_userid},#{create_user},#{create_time})";

                int insertCnt = sqlMapper.insert(SQL, data);

                logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

                // 方便debug窗口查看的;
                System.out.println("异常表insertCnt = " + insertCnt);

                //异常件发消息，调用后台API(给节点负责人发送短信及邮箱)
                if(data.getException_type().equals("2")){
                    String result = HttpRequest.sendPost(Constant.URL_SEND_EXCEPTION_MSG, "ExceptionIds=" + data.getId());
                    System.out.println("消息发送结果: " + result);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();;
            resultInfo.setSuccess(-1);
            resultInfo.setMessage("save_data_exception()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_exception()异常:" + ex.getMessage());
        }

        return resultInfo;
    }


    /**
     * 单件录入
     */
    public ResultInfo save_data_single(ScanDataListEnt dataEnt, SqlSession sen, ResultInfo resultInfo) {

        resultInfo.setSuccess(0);
        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            ScanData scanData = dataEnt.getDetail().get(0);
            scanData.setScaned("0");
            UploadPicUtil.savePicture(scanData, dataEnt.getHeader(), sqlMapper, resultInfo);

            // 拷贝对象:
            SingleData data = ConvertDataSchemaUtils.Conver2Scan_Single(scanData, dataEnt);

            // 检查数据是否重复:
            String id = data.getId();
            String SQL = "select count(1) as cnt from Scan_Input_Goods where ID = #{id}";
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
            Object cnt = rlt_one.get("cnt");
            int li_cnt = Integer.parseInt(cnt.toString());
            if (li_cnt > 0) {
                // 重复则跳过;
                return resultInfo;
            }

            //单价录入表中已经存在的包装号码，未审核的（check_status＝0），覆盖
            int insertCnt = 0;
            Map<String, Object> maps1 = new SingleDao().getCheckStatus(scanData.getPackNumber(), sqlMapper);
            if (maps1 != null && maps1.size() > 0) {

                SQL =   " update Scan_Input_Goods set " +
                        " ID=#{id},Route_Points_ID=#{route_points_id},Link_No=#{link_no}," +
                        " Main_Goods_ID=#{main_goods_id},Scan_Time=#{scan_time},Pack_Barcode=#{pack_barcode}," +
                        " Goods_Len=#{goods_len},Goods_Width=#{goods_width},Goods_Height=#{goods_height}," +
                        " Gross_Weight=#{gross_weight},Created_UserID=#{created_userid}," +
                        " Created_User=#{created_user},Created_Time=#{created_time}" +
                        " where Pack_No=#{pack_no}";

                insertCnt = sqlMapper.update(SQL, data);
            }else{

                // 插入记录(左右字段赋值);
                SQL =   " insert into Scan_Input_Goods(" +
                        " ID,Route_Points_ID,Link_No,Main_Goods_ID,Scan_Time," +
                        " Pack_No,Pack_Barcode,GC_Flag,Product_Name,Logistics_Info," +
                        " Goods_Len,Goods_Width,Goods_Height,Gross_Weight," +
                        " Created_UserID,Created_User,Created_Time," +
                        " Updated_UserID, Updated_User, Updated_Time)" +
                        " values(" +
                        "#{id},#{route_points_id},#{link_no},#{main_goods_id},#{scan_time}," +
                        "#{pack_no},#{pack_barcode},#{gc_flag},#{product_name},#{logistics_info}," +
                        "#{goods_len}, #{goods_width},#{goods_height},#{gross_weight}," +
                        "#{created_userid},#{created_user},#{created_time}," +
                        "#{created_userid},#{created_user},#{created_time})";

                insertCnt = sqlMapper.insert(SQL, data);
            }

            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

            // 方便debug窗口查看的;
            System.out.println("insertCnt = " + insertCnt);
        } catch (Exception ex) {
            resultInfo.setSuccess(-1);
            resultInfo.setMessage("save_data_single()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_single()异常:" + ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * 拍照模块图片保存
     */
    public ResultInfo save_data_takephoto(ScanDataListEnt dataEnt, SqlSession sen, ResultInfo resultInfo, String ecp_type) {

        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            for (int ix = 0; ix < dataEnt.getDetail().size(); ix++) {

                ScanData scanData = dataEnt.getDetail().get(ix);
                scanData.setScaned(ecp_type);
                resultInfo = UploadPicUtil.savePicture(scanData, dataEnt.getHeader(), sqlMapper, resultInfo);
                if (resultInfo.getSuccess() != 0) {
                    break;
                }

                // 拷贝对象:
                ExceptionData data = ConvertDataSchemaUtils.Conver2Scan_Exceptin(scanData, dataEnt, ecp_type);

                // 检查数据是否重复:
                String id = data.getId();
                String SQL = "select count(1) as cnt from Scan_Exception where ID = #{id}";
                Map<String, Object> rlt_one = sqlMapper.selectOne(SQL, id);
                Object cnt = rlt_one.get("cnt");
                int li_cnt = Integer.parseInt(cnt.toString());
                if (li_cnt > 0) {
                    // 重复则跳过;
                    continue;
                }

                // 插入记录(左右字段赋值);
                SQL =   " insert into Scan_Exception(" +
                        " ID,Route_Points_ID,Link_No,Exception_Type," +
                        " Scan_Time,Main_Goods_ID,Pack_Barcode,Pack_No," +
                        " GC_Flag,Created_UserID," +
                        " Created_User,Created_Time) " +
                        " values(" +
                        "#{id},#{route_points_id},#{link_no},#{exception_type}," +
                        "#{scan_time},#{main_goods_id},#{pack_barcode},#{pack_no}," +
                        "#{gc_flag},#{create_userid}," +
                        "#{create_user},#{create_time})";

                int insertCnt = sqlMapper.insert(SQL, data);

                logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);

                // 方便debug窗口查看的;
                System.out.println("insertCnt = " + insertCnt);
            }

        } catch (Exception ex) {
            ex.printStackTrace();;
            resultInfo.setSuccess(-1);
            resultInfo.setMessage("save_data_exception()异常:" + ex.getMessage());
            logger.error(Inner_log_Errcode + "save_data_exception()异常:" + ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * 判断主表中是否存在该货物匹配的条码
     * 如果存在，则跳出
     * 不存在，则根据main_goods_id给该条码赋值
     * @param actionRequest
     * @param scanData
     * @param sen
     */
    public static void checkExistBarcode(ActionRequest actionRequest, ScanData scanData, SqlSession sen){

        SqlMapper sqlMapper = new SqlMapper(sen);

        actionRequest.setTask_id(scanData.getMainGoodsId());//这里赋值注意一下，

        try{

            String SQL = "select mcg.id from main_containergoods mcg " +
                    "where mcg.project=#{project_id}" +
                    "and mcg.id<>#{task_id} and mcg.pack_barcode = #{pack_barcode}";

            Map<String, Object> map = sqlMapper.selectOne(SQL, actionRequest);
            if(map != null && map.size() > 0){//如果主表中存在该货物，且条码不为空，则跳出，不作修改
                return;
            }

            String SQL2 = "update main_containergoods set pack_barcode = #{pack_barcode} where id = #{project_id} and isnull(pack_barcode,'') = '' ";
            int cnt = sqlMapper.update(SQL2, actionRequest);

            sen.commit();
            System.out.println("货物条码为空，更新主表中的货物条码: " + scanData.getPackBarcode());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static ResultInfo updateRulerStatus(ActionRequest actionRequest, ResultInfo resultInfo){

        SqlSession sen = MyBatisUtil.getSqlSession(false);
        SqlMapper sqlMapper = new SqlMapper(sen);

        try{

            String SQL = " UPDATE dbo.Route_Points" +
                    " SET    Confirm_User_ID = #{user_id} ," +
                    "        Confirm_Time = #{time} ," +
                    "        Confirm_Status = 1" +
                    " WHERE  ID = #{node_id}";

            int cnt = sqlMapper.update(SQL, actionRequest);
            if(cnt <= 0){
                resultInfo.setSuccess(1);
                resultInfo.setMessage("确认失败");
                resultInfo.setCode("2001");
            }else{
                sen.commit();
            }

        }catch (Exception e){
            e.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("确认失败-" + e.getLocalizedMessage());
            resultInfo.setCode("1001");
        }finally {
            sen.close();
            sen = null;
        }

        return resultInfo;
    }

}
