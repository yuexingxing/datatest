package com.zd.pojo;

/**
 * 装车、卸车表, -->
 * 需要通过 Scan_Detail_Load.Scan_ID = Scan.ID 关联到 Scan 表,看 Scan.Link_No（根据PDA扫描类型，可以首先确定）,或者在Scan_Detail时回填;
 * 再关联到 Route_Points.Points_ID 对应节点类型 Base_Points.ID
 */
public class Scan_Detail_Load {
    private String scan_detail_id;

    private String scan_id;

    private String scan_time;

    private String main_goods_id;

    private String pack_no;

    private String pack_barcode;

    private String load_comp_id;

    private String load_comp_name;

    private String load_tools;

    private String linkman;

    private String gc_flag;

    private String created_userid;

    private String created_user;

    private String created_time;

    private String updated_userid;

    private String updated_user;

    private String updated_time;

    public void setScan_detail_id( String scan_detail_id ) {
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

    public void setLoad_comp_id( String load_comp_id ) {
        this.load_comp_id = load_comp_id;
    }

    public String getLoad_comp_id() {
        return this.load_comp_id;
    }

    public void setLoad_comp_name( String load_comp_name ) {
        this.load_comp_name = load_comp_name;
    }

    public String getLoad_comp_name() {
        return this.load_comp_name;
    }

    public void setLoad_tools( String load_tools ) {
        this.load_tools = load_tools;
    }

    public String getLoad_tools() {
        return this.load_tools;
    }

    public void setLinkman( String linkman ) {
        this.linkman = linkman;
    }

    public String getLinkman() {
        return this.linkman;
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
