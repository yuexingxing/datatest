package com.zd.pojo;

/**
 * Created by Administrator on 2017-06-05.
 */
public class Scan_Detail_Verify {

    private String scan_detail_id;
    private String scan_id;
    private String scan_time;
    private String main_goods_id;
    private String pack_no;
    private String pack_barcode;
    private String verify_comp_id;
    private String verify_comp_name;
    private String verify_user_id;
    private String verify_user_name;
    private String verify_result;
    private String gc_flag;

    private String Created_UserID;//		uniqueidentifier,
    private String Created_User;//		nvarchar(50),
    private String Created_Time;//		datetime,

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
    public String getVerify_comp_id() {
        return verify_comp_id;
    }
    public void setVerify_comp_id(String verify_comp_id) {
        this.verify_comp_id = verify_comp_id;
    }
    public String getVerify_comp_name() {
        return verify_comp_name;
    }
    public void setVerify_comp_name(String verify_comp_name) {
        this.verify_comp_name = verify_comp_name;
    }
    public String getVerify_user_id() {
        return verify_user_id;
    }
    public void setVerify_user_id(String verify_user_id) {
        this.verify_user_id = verify_user_id;
    }
    public String getVerify_user_name() {
        return verify_user_name;
    }
    public void setVerify_user_name(String verify_user_name) {
        this.verify_user_name = verify_user_name;
    }
    public String getVerify_result() {
        return verify_result;
    }
    public void setVerify_result(String verify_result) {
        this.verify_result = verify_result;
    }
    public String getGc_flag() {
        return gc_flag;
    }
    public void setGc_flag(String gc_flag) {
        this.gc_flag = gc_flag;
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
