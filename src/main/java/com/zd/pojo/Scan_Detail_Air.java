package com.zd.pojo;

/**
 * scan_detail_air 表对应的Model类
 Scan_Detail_ID, Scan_ID, Scan_Time, Main_Goods_ID, Pack_No, Pack_Barcode, Flight_No, Flight, GC_Flag,
 Created_UserID, Created_User, Created_Time, Updated_UserID, Updated_User, Updated_Time
 *
 */
public class Scan_Detail_Air {

    private String scan_detail_id;
    private String scan_id;
    private String scan_time;
    private String main_goods_id;
    private String pack_no;
    private String pack_barcode;
    private String flight_no;
    private String flight;
    private String gc_flag;
    private String created_userid;
    private String created_user;
    private String created_time;
    private String updated_userid;
    private String updated_user;
    private String updated_time;

    public void setScan_detail_id(String scan_detail_id ) {
        this.scan_detail_id = scan_detail_id;
    }

    public String getScan_detail_id() {
        return this.scan_detail_id;
    }

    public void setScan_id( String scan_id ) {
        this.scan_id = scan_id;
    }

    public String getScan_id() {
        return this.scan_id;
    }

    public void setScan_time( String scan_time ) {
        this.scan_time = scan_time;
    }

    public String getScan_time() {
        return this.scan_time;
    }

    public void setMain_goods_id( String main_goods_id ) {
        this.main_goods_id = main_goods_id;
    }

    public String getMain_goods_id() {
        return this.main_goods_id;
    }

    public void setPack_no( String pack_no ) {
        this.pack_no = pack_no;
    }

    public String getPack_no() {
        return this.pack_no;
    }

    public void setPack_barcode( String pack_barcode ) {
        this.pack_barcode = pack_barcode;
    }

    public String getPack_barcode() {
        return this.pack_barcode;
    }

    public void setFlight_no( String flight_no ) {
        this.flight_no = flight_no;
    }

    public String getFlight_no() {
        return this.flight_no;
    }

    public void setFlight( String flight ) {
        this.flight = flight;
    }

    public String getFlight() {
        return this.flight;
    }

    public void setGc_flag( String gc_flag ) {
        this.gc_flag = gc_flag;
    }

    public String getGc_flag() {
        return this.gc_flag;
    }

    public void setCreated_userid( String created_userid ) {
        this.created_userid = created_userid;
    }

    public String getCreated_userid() {
        return this.created_userid;
    }

    public void setCreated_user( String created_user ) {
        this.created_user = created_user;
    }

    public String getCreated_user() {
        return this.created_user;
    }

    public void setCreated_time( String created_time ) {
        this.created_time = created_time;
    }

    public String getCreated_time() {
        return this.created_time;
    }

    public void setUpdated_userid( String updated_userid ) {
        this.updated_userid = updated_userid;
    }

    public String getUpdated_userid() {
        return this.updated_userid;
    }

    public void setUpdated_user( String updated_user ) {
        this.updated_user = updated_user;
    }

    public String getUpdated_user() {
        return this.updated_user;
    }

    public void setUpdated_time( String updated_time ) {
        this.updated_time = updated_time;
    }

    public String getUpdated_time() {
        return this.updated_time;
    }
}
