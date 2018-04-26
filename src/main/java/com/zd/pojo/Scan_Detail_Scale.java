package com.zd.pojo;

/**
 * Created by Administrator on 2017-06-05.
 */
public class Scan_Detail_Scale {

    private String scan_detail_id;
    private String scan_id;
    private String scan_time;
    private String main_goods_id;
    private String pack_no;
    private String pack_barcode;
    private String scale_company_id;
    private String scale_company_name;
    private String goods_len;
    private String goods_width;
    private String goods_height;
    private String gross_weight;
    private String gc_flag;
    private String scale_user_id;
    private String scale_user_name;
    private String charge_ton;

    private String Supervise_Name;//监督员

    private String Created_UserID;//		uniqueidentifier,
    private String Created_User;//		nvarchar(50),
    private String Created_Time;//		datetime,

    public String getSupervise_Name() {
        return Supervise_Name;
    }

    public void setSupervise_Name(String supervise_Name) {
        Supervise_Name = supervise_Name;
    }

    public String getCharge_ton() {
        return charge_ton;
    }
    public void setCharge_ton(String charge_ton) {
        this.charge_ton = charge_ton;
    }

    public String getScan_detail_id() {
        return scan_detail_id;
    }
    public void setScan_detail_id(String scan_detail_id) {
        this.scan_detail_id = scan_detail_id;
    }
    public String getScan_id() {
        return scan_id;
    }
    public void setScan_id(String scan_id) {
        this.scan_id = scan_id;
    }
    public String getScan_time() {
        return scan_time;
    }
    public void setScan_time(String scan_time) {
        this.scan_time = scan_time;
    }
    public String getMain_goods_id() {
        return main_goods_id;
    }
    public void setMain_goods_id(String main_goods_id) {
        this.main_goods_id = main_goods_id;
    }
    public String getPack_no() {
        return pack_no;
    }
    public void setPack_no(String pack_no) {
        this.pack_no = pack_no;
    }
    public String getPack_barcode() {
        return pack_barcode;
    }
    public void setPack_barcode(String pack_barcode) {
        this.pack_barcode = pack_barcode;
    }
    public String getScale_company_id() {
        return scale_company_id;
    }
    public void setScale_company_id(String scale_company_id) {
        this.scale_company_id = scale_company_id;
    }
    public String getScale_company_name() {
        return scale_company_name;
    }
    public void setScale_company_name(String scale_company_name) {
        this.scale_company_name = scale_company_name;
    }
    public String getGoods_len() {
        return goods_len;
    }
    public void setGoods_len(String goods_len) {
        this.goods_len = goods_len;
    }
    public String getGoods_width() {
        return goods_width;
    }
    public void setGoods_width(String goods_width) {
        this.goods_width = goods_width;
    }
    public String getGoods_height() {
        return goods_height;
    }
    public void setGoods_height(String goods_height) {
        this.goods_height = goods_height;
    }
    public String getGross_weight() {
        return gross_weight;
    }
    public void setGross_weight(String gross_weight) {
        this.gross_weight = gross_weight;
    }
    public String getGc_flag() {
        return gc_flag;
    }
    public void setGc_flag(String gc_flag) {
        this.gc_flag = gc_flag;
    }
    public String getScale_user_id() {
        return scale_user_id;
    }
    public void setScale_user_id(String scale_user_id) {
        this.scale_user_id = scale_user_id;
    }
    public String getScale_user_name() {
        return scale_user_name;
    }
    public void setScale_user_name(String scale_user_name) {
        this.scale_user_name = scale_user_name;
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

}
