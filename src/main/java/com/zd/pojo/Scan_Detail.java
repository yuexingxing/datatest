package com.zd.pojo;

/**
 * 对应后台 Scan_Detail 表的数据结构
 */
public class Scan_Detail {
    private String id;

    private String scan_id;

    private String scan_time;

    private int goods_type;

    private String goods_id;

    private String goods_code;

    private String goods_barcode;

    private int piece_number;

    private String task_param_1;

    private String task_param_2;

    private String task_param_3;

    private String gps_coordx;

    private String gps_coordy;

    private String remark;

    private String gc_flag;

    private String created_userid;

    private String created_user;

    private String created_time;

    private String updated_userid;

    private String updated_user;

    private String updated_time;

    public void setId( String id ) {
        this.id = id;
    }

    public String getId() {
        return this.id;
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

    public void setGoods_type( int goods_type ) {
        this.goods_type = goods_type;
    }

    public int getGoods_type() {
        return this.goods_type;
    }

    public void setGoods_id( String goods_id ) {
        this.goods_id = goods_id;
    }

    public String getGoods_id() {
        return this.goods_id;
    }

    public void setGoods_code( String goods_code ) {
        this.goods_code = goods_code;
    }

    public String getGoods_code() {
        return this.goods_code;
    }

    public void setGoods_barcode( String goods_barcode ) {
        this.goods_barcode = goods_barcode;
    }

    public String getGoods_barcode() {
        return this.goods_barcode;
    }

    public void setPiece_number( int piece_number ) {
        this.piece_number = piece_number;
    }

    public int getPiece_number() {
        return this.piece_number;
    }

    public void setTask_param_1( String task_param_1 ) {
        this.task_param_1 = task_param_1;
    }

    public String getTask_param_1() {
        return this.task_param_1;
    }

    public void setTask_param_2( String task_param_2 ) {
        this.task_param_2 = task_param_2;
    }

    public String getTask_param_2() {
        return this.task_param_2;
    }

    public void setTask_param_3( String task_param_3 ) {
        this.task_param_3 = task_param_3;
    }

    public String getTask_param_3() {
        return this.task_param_3;
    }

    public void setGps_coordx( String gps_coordx ) {
        this.gps_coordx = gps_coordx;
    }

    public String getGps_coordx() {
        return this.gps_coordx;
    }

    public void setGps_coordy( String gps_coordy ) {
        this.gps_coordy = gps_coordy;
    }

    public String getGps_coordy() {
        return this.gps_coordy;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
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
