package com.zd.pojo;

/**
 * Created by Administrator on 2017-06-05.
 */
public class Scan_Detail_Pack {

    private String Scan_Detail_ID;//		uniqueidentifier	not null,		-- 扫描明细主键ID
    private String Scan_ID;//				uniqueidentifier	not null,		-- 扫描表主键ID
    private String Scan_Time;//			datetime			not null,		-- 扫描时间
    private String Part_Goods_ID;//		uniqueidentifier	not null,		-- 分箱单ID
    private String Goods_No;//			varchar(20)			not null,		-- 商品号码
    private String Goods_BarCode;//		varchar(20),						-- 商品条码
    private String Product_Name;//		varchar(50),						-- 商品品名
    private String Pack_Company_ID;//		uniqueidentifier	not null,		-- 包装公司ID
    private String Pack_Company_Name;//	varchar(50)			not null,		-- 包装公司名

    private String Pack_Pattern;
    private String Pack_Info;//			varchar(500),						-- 包装信息

    private String Pack_No;//包装号码

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
    public String getPack_Company_ID() {
        return Pack_Company_ID;
    }
    public void setPack_Company_ID(String pack_Company_ID) {
        Pack_Company_ID = pack_Company_ID;
    }
    public String getPack_Company_Name() {
        return Pack_Company_Name;
    }
    public void setPack_Company_Name(String pack_Company_Name) {
        Pack_Company_Name = pack_Company_Name;
    }

    public String getPack_Info() {
        return Pack_Info;
    }
    public void setPack_Info(String pack_Info) {
        Pack_Info = pack_Info;
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

    public String getPack_Pattern() {
        return Pack_Pattern;
    }

    public void setPack_Pattern(String pack_Pattern) {
        Pack_Pattern = pack_Pattern;
    }


    public String getPack_No() {
        return Pack_No;
    }

    public void setPack_No(String pack_No) {
        Pack_No = pack_No;
    }
}
