package com.zd.pojo;

/**
 * Created by Administrator on 2017-06-05.
 */
public class Scan_Detail_Strap {

    private String Scan_Detail_ID;//		uniqueidentifier		not null,		-- 扫描明细主键ID
    private String Scan_ID;//				uniqueidentifier		not null,		-- 扫描表主键ID
    private String Scan_Time;//			datetime				not null,		-- 扫描时间
    private String Main_Goods_ID;//		uniqueidentifier		not null,		-- 总箱单ID
    private String Pack_No;//			varchar(20)				not null,		-- 包装号码
    private String Pack_Barcode;//		varchar(20)				not null,		-- 包装条码
    private String Strap_Company_ID;//	uniqueidentifier		not null,		-- 绑扎公司ID
    private String Strap_Company_Name;//	varchar(50)				not null,		-- 绑扎公司名
    private String Strap_User_ID;//		uniqueidentifier		not null,		-- 绑扎员ID
    private String Strap_User_Name;//		varchar(50)				not null,		-- 绑扎员姓名
    private String Strap_Info;//			varchar(200)			null,			-- 绑扎信息
    private String GC_Flag;//			bit,
    private String Created_UserID;//	uniqueidentifier,
    private String Created_User;//	nvarchar(50),
    private String Created_Time;//	datetime,
    private String Updated_UserID;//	uniqueidentifier,
    private String Updated_User;//	nvarchar(50),
    private String Updated_Time;//	datetime,

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
    public String getMain_Goods_ID() {
        return Main_Goods_ID;
    }
    public void setMain_Goods_ID(String main_Goods_ID) {
        Main_Goods_ID = main_Goods_ID;
    }
    public String getPack_No() {
        return Pack_No;
    }
    public void setPack_No(String pack_No) {
        Pack_No = pack_No;
    }
    public String getPack_Barcode() {
        return Pack_Barcode;
    }
    public void setPack_Barcode(String pack_Barcode) {
        Pack_Barcode = pack_Barcode;
    }
    public String getStrap_Company_ID() {
        return Strap_Company_ID;
    }
    public void setStrap_Company_ID(String strap_Company_ID) {
        Strap_Company_ID = strap_Company_ID;
    }
    public String getStrap_Company_Name() {
        return Strap_Company_Name;
    }
    public void setStrap_Company_Name(String strap_Company_Name) {
        Strap_Company_Name = strap_Company_Name;
    }
    public String getStrap_User_ID() {
        return Strap_User_ID;
    }
    public void setStrap_User_ID(String strap_User_ID) {
        Strap_User_ID = strap_User_ID;
    }
    public String getStrap_User_Name() {
        return Strap_User_Name;
    }
    public void setStrap_User_Name(String strap_User_Name) {
        Strap_User_Name = strap_User_Name;
    }
    public String getStrap_Info() {
        return Strap_Info;
    }
    public void setStrap_Info(String strap_Info) {
        Strap_Info = strap_Info;
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
