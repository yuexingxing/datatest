package com.zd.pojo;

import com.zd.object.picInfo;

import java.util.List;

/**
 * Created by Administrator on 2017-05-03.
 */
public class ScanData {

    public String CacheId = "";// CacheId任务单号
    public String taskId = ""; // 任务名称
    public String taskName = ""; // 任务名称
    public String ScanType = "";// 扫描类型
    public String ScanTime = "";// 扫描时间
    public String createTime;
    public String packBarcode = ""; // 包装条码
    public String packNumber = ""; // 包装号码
    public String VehicleNumbers = "";// 车牌号
    public String train = ""; // 车次
    public String deiverPhone = ""; // 司机手机号
    public String Memo = ""; // 备注、说明
    public String loginId = ""; // 登录人id
    public String loginName = ""; // 登录人姓名
    public String PlanCount = "";// 载入数
    public String PracticalCount = "";// 扫描数

    // 货场入库
    public String libraryNumber = ""; // 库位号
    public String libraryAdamin = ""; // 库管员

    // 海运装货，卸货
    public String Saillings_name = ""; // 船名
    public String Saillings = "";// 航次
    public String shipping_space = ""; // 舱位

    // 空运装货，卸货
    public String flight = ""; // 航班
    public String voyage = ""; // 航次

    // 铁运装货，卸货
    public String wagonNumber = ""; // 车皮编号

    // 集装箱，做箱，拆箱
    private String container_no;
    private String freighter_name;
    private String sailing_no;

    // 装卸，装货，卸货
    public String company = ""; // 装卸公司
    public String company_id = ""; // 装卸公司
    public String TelPerson = "";// 联系人
    public String AbnormalLink = "";// 异常节点
    public String Scaned = "";// 是否扫描
    public String AbnormalCause = "";// 异常原因
    public String ReturnedCargoPic = "";// 退运图片
    public String ReturnedCargoFile = "";// 退运图片路径
    public String UploadStatus = "";// 上传状态，默认为0

    public String scanUser = "";//扫描人
    public String scanUserId = "";//扫描人ID

    public String loadCompanyId = "";//装卸公司id
    public String loadCompanyName = "";//装卸公司名称

    public String licensePlate = "";//车牌号
    public String carNo = "";//车次\

    public String Length;//长
    public String Width;//宽
    public String Height;//高
    public String Weight;//重
    public String V3;//体积
    public String ChargeTon;//计费吨

    public String storageNo;//货场编号
    public String storageManager;//货场负责人

    public String MainGoodsId = "";// 商品id
    public String GoodsName = "";//商品名称

    public String PackName = "";//包装名称

    private List<picInfo> picture;

    private String singleTaskId;//这个id是每次生成一条数据，自动生成一个任务id，用于解决多任务同时上传问题
    private String scan_id;
    private String scan_detail_id;

    public String MinutePackBarcode;//分箱单条码
    public String MinutePackNumber;//分箱单号码
    public String packMode = ""; //包装方式

    private String link;

    private List<picInfo> markPic;//码头拍照
    private List<picInfo> cargoPic;//货物拍照

    private String scale_supervisor = "";//打尺-监督员

    public String getScale_supervisor() {
        return scale_supervisor;
    }

    public void setScale_supervisor(String scale_supervisor) {
        this.scale_supervisor = scale_supervisor;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    private String linkMan;//联系人
    private String linkPhone;//联系电话

    public List<picInfo> getMarkPic(){
        return markPic;
    }
    public void setMarkPic(List<picInfo> MarkPic){
        this.markPic = MarkPic;
    }

    public List<picInfo> getCargoPic(){
        return cargoPic;
    }
    public void setCargoPic(List<picInfo> CargoPic){
        this.cargoPic = CargoPic;
    }

    public List<picInfo> getPicture(){
        return picture;
    }
    public void setPicture(List<picInfo> picture){
        this.picture = picture;
    }

    public String getScan_id() {
        return scan_id;
    }
    public void setScan_id(String scan_id) {
        this.scan_id = scan_id;
    }

    public String getScan_detail_id() {
        return scan_detail_id;
    }
    public void setScan_detail_id(String scan_detail_id) {
        this.scan_detail_id = scan_detail_id;
    }

    public String setSingleTaskId() {
        return singleTaskId;
    }
    public void getSingleTaskId(String singleTaskId) {
        this.singleTaskId = singleTaskId;
    }

    public String getStorageNo() {
        return storageNo;
    }
    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public String getStorageManager() {
        return storageManager;
    }
    public void setStorageManager(String storageManager) {
        this.storageManager = storageManager;
    }

    public String getLength() {
        return Length;
    }
    public void setLength(String len) {
        this.Length = len;
    }

    public String getWidth() {
        return Width;
    }
    public void setWidth(String width) {
        this.Width = width;
    }

    public String getHeight() {
        return Height;
    }
    public void setHeight(String height) {
        this.Height = height;
    }

    public String getV3() {
        return V3;
    }
    public void setV3(String V3) {
        this.V3 = V3;
    }

    public String getChargeTon() {
        return ChargeTon;
    }
    public void setChargeTon(String ChargeTon) {
        this.ChargeTon = ChargeTon;
    }

    public String getWeight() {
        return Weight;
    }
    public void setWeight(String weight) {
        this.Weight = weight;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCarNo() {
        return carNo;
    }
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getLoadCompanyId() {
        return loadCompanyId;
    }
    public void setLoadCompanyId(String loadCompanyId) {
        this.loadCompanyId = loadCompanyId;
    }

    public String getLoadCompanyName() {
        return loadCompanyName;
    }
    public void setLoadCompanyName(String loadCompanyName) {
        this.loadCompanyName = loadCompanyName;
    }

    public String getScanUser() {
        return scanUser;
    }
    public void setScanUser(String scanUser) {
        this.scanUser = scanUser;
    }

    public String getScanUserId() {
        return scanUserId;
    }
    public void setScanUserId(String scanUserId) {
        this.scanUserId = scanUserId;
    }

    public String getCacheId() {
        return CacheId;
    }
    public void setCacheId(String cacheId) {
        CacheId = cacheId;
    }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getScanType() {
        return ScanType;
    }
    public void setScanType(String scanType) {
        ScanType = scanType;
    }

    public String getScanTime() {
        return ScanTime;
    }
    public void setScanTime(String scanTime) {
        ScanTime = scanTime;
    }

    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPackBarcode() {
        return packBarcode;
    }
    public void setPackBarcode(String packBarcode) {
        this.packBarcode = packBarcode;
    }

    public String getPackNumber() {
        return packNumber;
    }
    public void setPackNumber(String packNumber) {
        this.packNumber = packNumber;
    }

    public String getVehicleNumbers() {
        return VehicleNumbers;
    }
    public void setVehicleNumbers(String vehicleNumbers) {
        VehicleNumbers = vehicleNumbers;
    }

    public String getTrain() {
        return train;
    }
    public void setTrain(String train) {
        this.train = train;
    }

    public String getDeiverPhone() {
        return deiverPhone;
    }
    public void setDeiverPhone(String deiverPhone) {
        this.deiverPhone = deiverPhone;
    }

    public String getMemo() {
        return Memo;
    }
    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPlanCount() {
        return PlanCount;
    }
    public void setPlanCount(String planCount) {
        PlanCount = planCount;
    }

    public String getPracticalCount() {
        return PracticalCount;
    }
    public void setPracticalCount(String practicalCount) {
        PracticalCount = practicalCount;
    }

    public String getLibraryNumber() {
        return libraryNumber;
    }
    public void setLibraryNumber(String libraryNumber) {
        this.libraryNumber = libraryNumber;
    }

    public String getLibraryAdamin() {
        return libraryAdamin;
    }
    public void setLibraryAdamin(String libraryAdamin) {
        this.libraryAdamin = libraryAdamin;
    }

    public String getSaillings_name() {
        return Saillings_name;
    }
    public void setSaillings_name(String saillings_name) {
        Saillings_name = saillings_name;
    }

    public String getSaillings() {
        return Saillings;
    }
    public void setSaillings(String saillings) {
        Saillings = saillings;
    }

    public String getShipping_space() {
        return shipping_space;
    }
    public void setShipping_space(String shipping_space) {
        this.shipping_space = shipping_space;
    }

    public String getFlight() {
        return flight;
    }
    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getVoyage() {
        return voyage;
    }
    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    public String getWagonNumber() {
        return wagonNumber;
    }
    public void setWagonNumber(String wagonNumber) {
        this.wagonNumber = wagonNumber;
    }

    public String getCompany_id() {
        return company_id;
    }
    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public String getTelPerson() {
        return TelPerson;
    }
    public void setTelPerson(String telPerson) {
        TelPerson = telPerson;
    }

    public String getAbnormalLink() {
        return AbnormalLink;
    }
    public void setAbnormalLink(String abnormalLink) {
        AbnormalLink = abnormalLink;
    }

    public String getScaned() {
        return Scaned;
    }
    public void setScaned(String scaned) {
        Scaned = scaned;
    }

    public String getAbnormalCause() {
        return AbnormalCause;
    }
    public void setAbnormalCause(String abnormalCause) {
        AbnormalCause = abnormalCause;
    }

    public String getReturnedCargoPic() {
        return ReturnedCargoPic;
    }
    public void setReturnedCargoPic(String returnedCargoPic) {
        ReturnedCargoPic = returnedCargoPic;
    }

    public String getReturnedCargoFile() {
        return ReturnedCargoFile;
    }
    public void setReturnedCargoFile(String returnedCargoFile) {
        ReturnedCargoFile = returnedCargoFile;
    }

    public String getUploadStatus() {
        return UploadStatus;
    }
    public void setUploadStatus(String uploadStatus) {
        UploadStatus = uploadStatus;
    }

    public String getGoodsName() {
        return GoodsName;
    }
    public void setGoodsName(String GoodsName) {
        GoodsName = GoodsName;
    }

    public String getMainGoodsId() {
        return MainGoodsId;
    }
    public void setMainGoodsId(String mainGoodsId) {
        MainGoodsId = mainGoodsId;
    }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getContainer_no() {
        return container_no;
    }

    public void setContainer_no(String container_no) {
        this.container_no = container_no;
    }

    public String getFreighter_name() {
        return freighter_name;
    }

    public void setFreighter_name(String freighter_name) {
        this.freighter_name = freighter_name;
    }

    public String getSailing_no() {
        return sailing_no;
    }

    public void setSailing_no(String sailing_no) {
        this.sailing_no = sailing_no;
    }

    public String getPackMode() {
        return packMode;
    }

    public void setPackMode(String packMode) {
        this.packMode = packMode;
    }

    public String getPackName() {
        return PackName;
    }

    public void setPackName(String packName) {
        PackName = packName;
    }

    public String getMinutePackBarcode() {
        return MinutePackBarcode;
    }

    public void setMinutePackBarcode(String minutePackBarcode) {
        MinutePackBarcode = minutePackBarcode;
    }

    public String getMinutePackNumber() {
        return MinutePackNumber;
    }

    public void setMinutePackNumber(String minutePackNumber) {
        MinutePackNumber = minutePackNumber;
    }
}
