package com.zd.pojo;

import org.apache.http.util.TextUtils;

/**
 * Created by Administrator on 2017-06-05.
 */
public class Scan_Detail_Offline {

    private String Scan_Detail_ID;//		uniqueidentifier	not null,		-- 扫描明细主键ID
    private String Scan_ID;//				uniqueidentifier	not null,		-- 扫描表主键ID
    private String Scan_Time;//			datetime			not null,		-- 扫描时间
    private String Part_Goods_ID;//		uniqueidentifier	not null,		-- 分箱单ID
    private String Goods_No;//			varchar(20)			not null,		-- 商品号码
    private String Goods_BarCode;//		varchar(20),						-- 商品条码
    private String CheckUser_ID;//		uniqueidentifier	not null,		-- 检验员ID
    private String CheckUser_Name;//		varchar(50)			not null,		-- 检验员名
    private String Goods_Len;//			int,								-- 长
    private String Goods_Width;//			int,								-- 宽
    private String Goods_Height;//		int,								-- 高
    private String Goods_Weight;//		decimal(10,3),						-- 毛重
    private String Product_Name;//		varchar(50),						-- 部件名称/商品品名
    private String Offline_Info;//		varchar(500),						-- 下线信息
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
    public String getCheckUser_ID() {
        return CheckUser_ID;
    }
    public void setCheckUser_ID(String checkUser_ID) {
        CheckUser_ID = checkUser_ID;
    }
    public String getCheckUser_Name() {
        return CheckUser_Name;
    }
    public void setCheckUser_Name(String checkUser_Name) {
        CheckUser_Name = checkUser_Name;
    }

    public String getGoods_Len() {

        if(TextUtils.isEmpty(Goods_Len)){
            return "0";
        }

        return Goods_Len;
    }
    public void setGoods_Len(String goods_Len) {
        Goods_Len = goods_Len;
    }
    public String getGoods_Width() {

        if(TextUtils.isEmpty(Goods_Width)){
            return "0";
        }
        return Goods_Width;
    }
    public void setGoods_Width(String goods_Width) {
        Goods_Width = goods_Width;
    }

    public String getGoods_Height() {

        if(TextUtils.isEmpty(Goods_Height)){
            return "0";
        }

        return Goods_Height;
    }
    public void setGoods_Height(String goods_Height) {
        Goods_Height = goods_Height;
    }

    public String getGoods_Weight() {

        if(TextUtils.isEmpty(Goods_Weight)){
            return "0";
        }

        return Goods_Weight;
    }
    public void setGoods_Weight(String goods_Weight) {
        Goods_Weight = goods_Weight;
    }

    public String getProduct_Name() {
        return Product_Name;
    }
    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }
    public String getOffline_Info() {
        return Offline_Info;
    }
    public void setOffline_Info(String offline_Info) {
        Offline_Info = offline_Info;
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
