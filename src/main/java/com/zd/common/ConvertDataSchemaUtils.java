package com.zd.common;

import com.zd.pojo.*;
import com.zd.object.*;
import com.zd.util.CommandTools;

/**
 * 数据格式转换工具类:
 *
 * scan.id = scan_detail.scan_id = scan_detail_xx.scan_id
 */
public class ConvertDataSchemaUtils {

    /**
     * 新建任务
     * route_task.id = scan.route_task_id
     * */
    public static RouteTaskInfo Conver2Scan_RouteTask(ScanData scanData, ScanDataListEnt dataEnt, String task_code) {

        RouteTaskInfo ent = new RouteTaskInfo();

        Header header = dataEnt.getHeader();

        ent.setId(header.getRoute_task_id());//任务ID
        ent.setTask_code(task_code);
        ent.setTask_name(scanData.getTaskName());
        ent.setRoute_points_id(header.getRoute_points_id());
        ent.setCreated_userid(header.getScan_user_id());
        ent.setCreated_user(header.getScan_user());

        ent.setGc_flag("0");
        ent.setBe_plan("1");

        return ent;
    }

    public static Header Conver2Scan(ScanDataListEnt dataEnt, ScanData scanData) {

        Header header = dataEnt.getHeader();
        header.setId(scanData.getScan_id());
        header.setGc_flag("0");
        header.setRoute_task_id(scanData.getTaskId());
        header.setLink_no(header.getLink_no());
        header.setDevice_id(header.getDevice_id());

        header.setScan_time(CommandTools.getTime());

        return header;
    }

    /**
     * PDA扫描数据转化为 【Scan_Detail】结构对象
     * ID, Scan_ID, Scan_Time, Goods_Type, Goods_ID, Goods_Code, Goods_Barcode, Piece_Number, Task_Param_1,
     * Task_Param_2, Task_Param_3, GPS_CoordX, GPS_CoordY, Remark, GC_Flag, Created_UserID, Created_User,
     * Created_Time, Updated_UserID, Updated_User, Updated_Time
     * ------>对应的上传字段:
     */
    public static Scan_Detail Conver2Scan_Detail(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail ent = new Scan_Detail();

        ent.setId(scanData.getScan_detail_id());//主键，随机生成
        ent.setScan_id(scanData.getScan_id());

        ent.setScan_time(scanData.getScanTime());
        ent.setGoods_type(1);

        if (dataEnt.getScan_type().toLowerCase().trim().equals("pack")){
            ent.setGoods_barcode(scanData.getMinutePackBarcode());
            ent.setGoods_code(scanData.getMinutePackNumber());
        }else{
            ent.setGoods_barcode(scanData.getPackBarcode());
            ent.setGoods_code(scanData.getPackNumber());
        }

        ent.setGoods_id(scanData.getMainGoodsId());

        ent.setTask_param_1(scanData.getSaillings_name());
        ent.setTask_param_2(scanData.getSaillings());
        ent.setTask_param_3(scanData.getShipping_space());

        if(scanData.getPlanCount() != null && scanData.getPlanCount() != ""){
            ent.setPiece_number(Integer.parseInt(scanData.getPlanCount()));
        }

        ent.setGc_flag("0");

        ent.setGps_coordx(header.getGps_coordx());
        ent.setGps_coordy(header.getGps_coordy());

        ent.setCreated_userid(header.getScan_user_id());
        ent.setCreated_user(header.getScan_user());
        ent.setCreated_time(CommandTools.getTime());

        return ent;
    }

    /**
     * 【Scan_Detail_Air】
     * PDA扫描明细上传到后台,转为MySql中对应的表的类型
     *
     * @param  : pda扫描表结构
     * @return 后台表结构
     * <p/>
     * Scan_Detail_ID, Scan_ID, Scan_Time, Main_Goods_ID, Pack_No, Pack_Barcode, Flight_No, Flight, GC_Flag,
     * Created_UserID, Created_User, Created_Time, Updated_UserID, Updated_User, Updated_Time
     */
    public static Scan_Detail_Air Conver2Scan_Detail_Air(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Air ent = new Scan_Detail_Air();

        ent.setScan_detail_id(scanData.getCacheId());
        ent.setScan_id(scanData.getScan_id());

        ent.setMain_goods_id(scanData.getMainGoodsId());
        ent.setPack_no(scanData.getPackNumber());
        ent.setPack_barcode(scanData.getPackBarcode());
        ent.setFlight(scanData.getFlight());
        ent.setScan_time(scanData.getScanTime());

        ent.setGc_flag("0");

        ent.setCreated_userid(header.getScan_user_id());
        ent.setCreated_user(header.getScan_user());
        ent.setCreated_time(CommandTools.getTime());

        return ent;
    }

    /**
     * 装车,卸车 扫描对象
     */
    public static Scan_Detail_Load Conver2Scan_Detail_Load(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Load ent = new Scan_Detail_Load();

        ent.setScan_detail_id(scanData.getCacheId());
        ent.setScan_id(scanData.getScan_id());

        ent.setScan_time(scanData.getScanTime());

        ent.setMain_goods_id(scanData.getMainGoodsId());
        ent.setPack_barcode(scanData.getPackBarcode());
        ent.setPack_no(scanData.getPackNumber());

        ent.setLoad_comp_id(scanData.getCompany_id());
        ent.setLoad_comp_name(scanData.getCompany());

        ent.setGc_flag("0");

        ent.setCreated_userid(dataEnt.getHeader().getScan_user_id());
        ent.setCreated_user(dataEnt.getHeader().getScan_user());
        ent.setCreated_time(CommandTools.getTime());

        return ent;
    }

    /**
     * 陆运
     *
     * */
    public static Scan_detail_car Conver2Scan_Detail_Car(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_detail_car ent = new Scan_detail_car();

        ent.setScan_detail_id(scanData.getCacheId());
        ent.setScan_id(scanData.getScan_id());

        ent.setMain_goods_id(scanData.getMainGoodsId());

        ent.setScan_time(scanData.getScanTime());
        ent.setPack_no(scanData.getPackNumber());
        ent.setPack_barcode(scanData.getPackBarcode());

        ent.setLicense_plate(scanData.getVehicleNumbers());
        ent.setCar_no(scanData.getTrain());

        ent.setDriver_phone(scanData.getDeiverPhone());
        ent.setRemark(scanData.getMemo());

        ent.setGc_flag("0");

        ent.setCreate_userid(dataEnt.getHeader().getScan_user_id());
        ent.setCreate_user(dataEnt.getHeader().getScan_user());
        ent.setCreate_time(CommandTools.getTime());

        return ent;
    }

    /**
     * 铁运
     *
     * */
    public static Scan_Detail_RailWay Conver2Scan_Detail_RailWay(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_RailWay ent = new Scan_Detail_RailWay();

        ent.setScan_detail_id(scanData.getCacheId());
        ent.setScan_id(scanData.getScan_id());

        ent.setMain_goods_id(scanData.getMainGoodsId());

        ent.setScan_time(scanData.getScanTime());
        ent.setPack_no(scanData.getPackNumber());
        ent.setPack_barcode(scanData.getPackBarcode());

        ent.setWagon_code(scanData.getWagonNumber());
        ent.setTrain_no(scanData.getTrain());

        ent.setGc_flag("0");

        ent.setCreate_userid(dataEnt.getHeader().getScan_user_id());
        ent.setCreate_user(dataEnt.getHeader().getScan_user());
        ent.setCreate_time(CommandTools.getTime());

        return ent;
    }

    /**
     * 海运
     *
     * */
    public static Scan_Detail_Sea Conver2Scan_Detail_Sea(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Sea ent = new Scan_Detail_Sea();

        ent.setScan_detail_id(scanData.getCacheId());
        ent.setScan_id(scanData.getScan_id());

        ent.setMain_goods_id(scanData.getMainGoodsId());

        ent.setScan_time(scanData.getScanTime());
        ent.setPack_no(scanData.getPackNumber());
        ent.setPack_barcode(scanData.getPackBarcode());

        ent.setSailing_no(scanData.getSaillings());
        ent.setFreighter_name(scanData.getSaillings_name());
        ent.setClass_no(scanData.getShipping_space());//

        ent.setGc_flag("0");

        ent.setCreated_userid(dataEnt.getHeader().getScan_user_id());
        ent.setCreated_user(dataEnt.getHeader().getScan_user());
        ent.setCreated_time(CommandTools.getTime());

        return ent;
    }

    /**
     * 集装箱
     *
     * */
    public static Scan_Detail_Container Conver2Scan_Detail_Container(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Container ent = new Scan_Detail_Container();

        ent.setScan_detail_id(scanData.getCacheId());
        ent.setScan_id(scanData.getScan_id());

        ent.setMain_goods_id(scanData.getMainGoodsId());

        ent.setScan_time(scanData.getScanTime());

        ent.setPack_no(scanData.getPackNumber());
        ent.setPack_barcode(scanData.getPackBarcode());

        ent.setContainer_no(scanData.getContainer_no());
        ent.setFreighter_name(scanData.getFreighter_name());
        ent.setSailing_no(scanData.getSailing_no());

        ent.setGc_flag("0");

        ent.setCreate_userid(dataEnt.getHeader().getScan_user_id());
        ent.setCreate_user(dataEnt.getHeader().getScan_user());
        ent.setCreate_time(CommandTools.getTime());

        return ent;
    }

    /**
     * 货场
     *
     * */
    public static Scan_Detail_Storage Conver2Scan_Detail_Storage(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Storage ent = new Scan_Detail_Storage();

        ent.setScan_detail_id(scanData.getCacheId());
        ent.setScan_id(scanData.getScan_id());

        ent.setScan_time(scanData.getScanTime());
        ent.setMain_goods_id(scanData.getMainGoodsId());

        ent.setPack_no(scanData.getPackNumber());
        ent.setPack_barcode(scanData.getPackBarcode());

        ent.setStorage_no(scanData.getLibraryNumber());
        ent.setStorage_manager(scanData.getLibraryAdamin());

        ent.setGc_flag("0");

        ent.setCreate_userid(dataEnt.getHeader().getScan_user_id());
        ent.setCreate_user(dataEnt.getHeader().getScan_user());
        ent.setCreate_time(CommandTools.getTime());

        return ent;
    }

    /**
     * 异常、退运、拍照
     *
     * */
    public static ExceptionData Conver2Scan_Exceptin(ScanData scanData, ScanDataListEnt dataEnt, String ecp_type) {

        Header header = dataEnt.getHeader();
        ExceptionData ent = new ExceptionData();

        ent.setId(scanData.getCacheId());
        ent.setRoute_points_id(header.getRoute_points_id());
        ent.setLink_no(header.getLink_no());
        ent.setException_type(ecp_type);
        ent.setScan_time(scanData.getScanTime());

        ent.setMain_goods_id(scanData.getMainGoodsId());

        ent.setPack_barcode(scanData.getPackBarcode());
        ent.setPack_no(scanData.getPackNumber());

        ent.setExcept_desc(scanData.getMemo());//异常描述、退运原因

        ent.setGc_flag("0");

        ent.setCreate_userid(dataEnt.getHeader().getScan_user_id());
        ent.setCreate_user(dataEnt.getHeader().getScan_user());
        ent.setCreate_time(CommandTools.getTime());

        return ent;
    }

    /**
     * 单件录入
     * type 1-单件录入 2-包装过来的
     * 根据type取值
     * */
    public static SingleData Conver2Scan_Single(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        SingleData ent = new SingleData();

        ent.setId(scanData.getCacheId());
        ent.setRoute_points_id(header.getRoute_points_id());
        ent.setLink_no(header.getLink_no());

        ent.setMain_goods_id(scanData.getMainGoodsId());

        ent.setPack_barcode(scanData.getPackBarcode());
        ent.setPack_no(scanData.getPackNumber());

        ent.setGoods_len(scanData.getLength());
        ent.setGoods_width(scanData.getWidth());
        ent.setGoods_height(scanData.getHeight());
        ent.setGross_weight(scanData.getWeight());

        ent.setScan_time(scanData.getScanTime());
        ent.setProduct_name(scanData.getGoodsName());
        ent.setLogistics_info(scanData.getMemo());

        ent.setGc_flag("0");
        ent.setCheck_status("0");

        ent.setCreated_userid(header.getScan_user_id());
        ent.setCreated_user(header.getScan_user());
        ent.setCreated_time(CommandTools.getTime());

        return ent;
    }

    /**
     * 拍照
     *
     * */
    public static AttachmentInfo Conver2Scan_Attachment(ScanData scanData, AttachmentInfo attachmentInfo) {

        attachmentInfo.setId(CommandTools.getUUID());
        attachmentInfo.setMain_id(scanData.getCacheId());
        attachmentInfo.setAttachment_type(scanData.getScaned());
        attachmentInfo.setUpload_time(CommandTools.getTime());

        attachmentInfo.setGc_flag("0");

        return attachmentInfo;
    }

    /**
     * 检验
     * @param scanData
     * @param dataEnt
     * @return
     */
    public static Scan_Detail_Verify Conver2Scan_Detail_Verify(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Verify ent = new Scan_Detail_Verify();

        ent.setScan_detail_id(scanData.getCacheId());
        ent.setScan_id(scanData.getScan_id());
        ent.setScan_time(scanData.getScanTime());

        ent.setMain_goods_id(scanData.getMainGoodsId());
        ent.setPack_no(scanData.getPackNumber());
        ent.setPack_barcode(scanData.getPackBarcode());

        ent.setVerify_comp_id(scanData.getCompany_id());
        ent.setVerify_comp_name(scanData.getCompany());

        ent.setVerify_user_id(header.getScan_user_id());
        ent.setVerify_user_name(header.getScan_user());

        ent.setVerify_result(scanData.getMemo());

        ent.setGc_flag("0");

        ent.setCreated_UserID(header.getScan_user_id());
        ent.setCreated_User(header.getScan_user());
        ent.setCreated_Time(CommandTools.getTime());

        return ent;
    }

    /**
     * 打尺
     * @param scanData
     * @param dataEnt
     * @return
     */
    public static Scan_Detail_Scale Conver2Scan_Detail_Scale(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Scale ent = new Scan_Detail_Scale();

        ent.setScan_detail_id(scanData.getCacheId());
        ent.setScan_id(scanData.getScan_id());
        ent.setScan_time(scanData.getScanTime());

        ent.setMain_goods_id(scanData.getMainGoodsId());
        ent.setPack_no(scanData.getPackNumber());
        ent.setPack_barcode(scanData.getPackBarcode());

        ent.setScale_company_id(scanData.getCompany_id());
        ent.setScale_company_name(scanData.getCompany());

        ent.setScale_user_id(header.getScan_user_id());
        ent.setScale_user_name(header.getScan_user());

        ent.setSupervise_Name(scanData.getScale_supervisor());

        ent.setGoods_len(scanData.getLength());
        ent.setGoods_width(scanData.getWidth());
        ent.setGoods_height(scanData.getHeight());
        ent.setGross_weight(scanData.getWeight());
        ent.setCharge_ton(scanData.getChargeTon());

        ent.setGc_flag("0");

        ent.setCreated_UserID(header.getScan_user_id());
        ent.setCreated_User(header.getScan_user());
        ent.setCreated_Time(CommandTools.getTime());

        return ent;
    }

    /**
     * 包装--总箱单
     * @param scanData
     * @param dataEnt
     * @return
     */
    public static Scan_Detail_Pack_Main Conver2Scan_Detail_Pack_Main(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Pack_Main data = new Scan_Detail_Pack_Main();

        data.setID(CommandTools.getUUID());
        data.setProject(header.getProject_id());

        data.setPack_BarCode(scanData.getPackBarcode());
        data.setPack_No(scanData.getPackNumber());
        data.setCompany_ID(scanData.getCompany_id());
        data.setGoods_Name(scanData.getMainGoodsId());

        data.setLength(scanData.getLength());
        data.setWidth(scanData.getWidth());
        data.setHeight(scanData.getHeight());
        data.setNet_Weight(scanData.getWeight());

        data.setGC_Flag("0");

        data.setCreated_UserID(header.getScan_user_id());
        data.setCreated_User(header.getScan_user());
        data.setCreated_Time(CommandTools.getTime());

        return data;
    }

    /**
     * 包装--分箱单
     * @param scanData
     * @param dataEnt
     * @return
     */
    public static Scan_Detail_Pack Conver2Scan_Detail_Pack(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Pack ent = new Scan_Detail_Pack();

        ent.setScan_Detail_ID(scanData.getCacheId());
        ent.setScan_ID(scanData.getScan_id());
        ent.setScan_Time(scanData.getScanTime());

        ent.setGoods_BarCode(scanData.getMinutePackBarcode());
        ent.setGoods_No(scanData.getMinutePackNumber());
        ent.setPart_Goods_ID(scanData.getMainGoodsId());

        ent.setProduct_Name(scanData.getGoodsName());

        ent.setPack_Company_ID(scanData.getCompany_id());
        ent.setPack_Company_Name(scanData.getCompany());

        ent.setPack_Pattern(scanData.getPackMode());//包装方式
        ent.setPack_Info(scanData.getMemo());
        ent.setPack_No(scanData.getPackNumber());

        ent.setGC_Flag("0");

        ent.setCreated_UserID(header.getScan_user_id());
        ent.setCreated_User(header.getScan_user());
        ent.setCreated_Time(CommandTools.getTime());

        return ent;
    }

    /**
     * 绑扎
     * @param scanData
     * @param dataEnt
     * @return
     */
    public static Scan_Detail_Strap Conver2Scan_Detail_Strap(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Strap ent = new Scan_Detail_Strap();

        ent.setScan_Detail_ID(scanData.getCacheId());
        ent.setScan_ID(scanData.getScan_id());
        ent.setScan_Time(scanData.getScanTime());

        ent.setMain_Goods_ID(scanData.getMainGoodsId());
        ent.setPack_Barcode(scanData.getPackBarcode());
        ent.setPack_No(scanData.getPackNumber());

        ent.setStrap_Company_ID(scanData.getCompany_id());
        ent.setStrap_Company_Name(scanData.getCompany());
        ent.setStrap_User_ID(header.getScan_user_id());
        ent.setStrap_User_Name(header.getScan_user());
        ent.setStrap_Info(scanData.getMemo());

        ent.setCreated_UserID(header.getScan_user_id());
        ent.setCreated_User(header.getScan_user());
        ent.setCreated_Time(CommandTools.getTime());

        ent.setGC_Flag("0");

        return ent;
    }

    /**
     * 下线
     * @param scanData
     * @param dataEnt
     * @return
     */
    public static Scan_Detail_Offline Conver2Scan_Detail_Offline(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Offline ent = new Scan_Detail_Offline();

        ent.setScan_Detail_ID(scanData.getCacheId());
        ent.setScan_ID(scanData.getScan_id());
        ent.setScan_Time(scanData.getScanTime());

        ent.setPart_Goods_ID(scanData.getMainGoodsId());
        ent.setGoods_No(scanData.getPackNumber());
        ent.setGoods_BarCode(scanData.getPackBarcode());

        ent.setCheckUser_ID(header.getScan_user_id());
        ent.setCheckUser_Name(header.getScan_user());

        ent.setGoods_Len(scanData.getLength());
        ent.setGoods_Width(scanData.getWidth());
        ent.setGoods_Height(scanData.getHeight());
        ent.setGoods_Weight(scanData.getWeight());

        ent.setProduct_Name(scanData.getGoodsName());
        ent.setOffline_Info(scanData.getMemo());

        ent.setGC_Flag("0");

        ent.setCreated_UserID(header.getScan_user_id());
        ent.setCreated_User(header.getScan_user());
        ent.setCreated_Time(CommandTools.getTime());

        return ent;
    }

    /**
     * 安装
     * @param scanData
     * @param dataEnt
     * @return
     */
    public static Scan_Detail_Install Conver2Scan_Detail_Install(ScanData scanData, ScanDataListEnt dataEnt) {

        Header header = dataEnt.getHeader();
        Scan_Detail_Install ent = new Scan_Detail_Install();

        ent.setScan_Detail_ID(scanData.getCacheId());
        ent.setScan_ID(scanData.getScan_id());
        ent.setScan_Time(scanData.getScanTime());

        ent.setPart_Goods_ID(scanData.getMainGoodsId());
        ent.setGoods_No(scanData.getPackNumber());
        ent.setGoods_BarCode(scanData.getPackBarcode());
        ent.setProduct_Name(scanData.getGoodsName());

        ent.setInstaller_ID(header.getScan_user_id());
        ent.setInstaller_Name(header.getScan_user());
        ent.setInstall_Info(scanData.getMemo());

        ent.setGC_Flag("0");

        ent.setCreated_UserID(header.getScan_user_id());
        ent.setCreated_User(header.getScan_user());
        ent.setCreated_Time(CommandTools.getTime());

        return ent;
    }
}
