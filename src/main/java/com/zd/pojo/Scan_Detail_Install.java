package com.zd.pojo;

/**
 * Created by Administrator on 2017-06-05.
 */
public class Scan_Detail_Install {

    private String Scan_Detail_ID;//		uniqueidentifier	not null,		-- 扫描明细主键ID
    private String Scan_ID;//				uniqueidentifier	not null,		-- 扫描表主键ID
    private String Scan_Time;//			datetime			not null,		-- 扫描时间
    private String Part_Goods_ID;//		uniqueidentifier	not null,		-- 分箱单ID
    private String Goods_No;//			varchar(20)			not null,		-- 商品号码
    private String Goods_BarCode;//		varchar(20),						-- 商品条码
    private String Product_Name;//		varchar(50),						-- 商品品名
    private String Installer_ID;//		uniqueidentifier	not null,		-- 安装员ID
    private String Installer_Name;//		varchar(50)			not null,		-- 安装员姓名
    private String Install_Info;//		varchar(500),						-- 安装信息
    private String GC_Flag;//				bit,
    private String Created_UserID;//		uniqueidentifier,
    private String Created_User;//		nvarchar(50),
    private String Created_Time;//		datetime,
    private String Updated_UserID;//		uniqueidentifier,
    private String Updated_User;//		nvarchar(50),
    private String Updated_Time;//		datetime,

    public String getScan_Detail_ID() {
        return Scan_Detail_ID;
    }
    public void setScan_Detail_ID(String scan_Detail_ID) {
        Scan_Detail_ID = scan_Detail_ID;
    }
    public String getScan_ID() {
        return Scan_ID;
    }
    public void setScan_ID(String scan_ID) {
        Scan_ID = scan_ID;
    }
    public String getScan_Time() {
        return Scan_Time;
    }
    public void setScan_Time(String scan_Time) {
        Scan_Time = scan_Time;
    }
    public String getPart_Goods_ID() {
        return Part_Goods_ID;
    }
    public void setPart_Goods_ID(String part_Goods_ID) {
        Part_Goods_ID = part_Goods_ID;
    }
    public String getGoods_No() {
        return Goods_No;
    }
    public void setGoods_No(String goods_No) {
        Goods_No = goods_No;
    }
    public String getGoods_BarCode() {
        return Goods_BarCode;
    }
    public void setGoods_BarCode(String goods_BarCode) {
        Goods_BarCode = goods_BarCode;
    }
    public String getProduct_Name() {
        return Product_Name;
    }
    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }
    public String getInstaller_ID() {
        return Installer_ID;
    }
    public void setInstaller_ID(String installer_ID) {
        Installer_ID = installer_ID;
    }
    public String getInstaller_Name() {
        return Installer_Name;
    }
    public void setInstaller_Name(String installer_Name) {
        Installer_Name = installer_Name;
    }
    public String getInstall_Info() {
        return Install_Info;
    }
    public void setInstall_Info(String install_Info) {
        Install_Info = install_Info;
    }
    public String getGC_Flag() {
        return GC_Flag;
    }
    public void setGC_Flag(String gC_Flag) {
        GC_Flag = gC_Flag;
    }
    public String getCreated_UserID() {
        return Created_UserID;
    }
    public void setCreated_UserID(String created_UserID) {
        Created_UserID = created_UserID;
    }
    public String getCreated_User() {
        return Created_User;
    }
    public void setCreated_User(String created_User) {
        Created_User = created_User;
    }
    public String getCreated_Time() {
        return Created_Time;
    }
    public void setCreated_Time(String created_Time) {
        Created_Time = created_Time;
    }
    public String getUpdated_UserID() {
        return Updated_UserID;
    }
    public void setUpdated_UserID(String updated_UserID) {
        Updated_UserID = updated_UserID;
    }
    public String getUpdated_User() {
        return Updated_User;
    }
    public void setUpdated_User(String updated_User) {
        Updated_User = updated_User;
    }
    public String getUpdated_Time() {
        return Updated_Time;
    }
    public void setUpdated_Time(String updated_Time) {
        Updated_Time = updated_Time;
    }
}
