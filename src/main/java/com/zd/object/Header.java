package com.zd.object;

/**
 * 扫描上传数据的头部
 */
public class Header {

    private String id;

    private String scan_id;
    private String node_id;

    private String project_id;

    private String route_task_id;

    private String upload_time;

    private int load_number;

    private String link_no;

    private String node_no;

    private String route_points_id;

    private String scan_user;

    private String scan_user_id;

    private String scan_time;

    private String gps_coordx;
    private String gps_coordy;
    private String device_id;

    private int flag;
    private String  gc_flag;
    private String  scan_type;

    public String getNode_id() {
        return node_id;
    }
    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public void setFlag( int flag ) {
        this.flag = flag;
    }
    public int getFlag() {
        return this.flag;
    }

    public String getGc_flag() {
        return gc_flag;
    }
    public void setGc_flag(String gc_flag) {
        this.gc_flag = gc_flag;
    }

    public String getScan_type() {
        return scan_type;
    }
    public void setScan_type(String scan_type) {
        this.scan_type = scan_type;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getScan_id() {
        return scan_id;
    }

    public void setScan_id(String scan_id) {
        this.scan_id = scan_id;
    }


    public void setRoute_task_id( String route_task_id ) {
        this.route_task_id = route_task_id;
    }

    public String getRoute_task_id() {
        return this.route_task_id;
    }

    public void setUpload_time( String upload_time ) {
        this.upload_time = upload_time;
    }

    public String getUpload_time() {
        return this.upload_time;
    }

    public void setLoad_number( int load_number ) {
        this.load_number = load_number;
    }

    public int getLoad_number() {
        return this.load_number;
    }

    public void setLink_no( String link_no ) {
        this.link_no = link_no;
    }

    public String getLink_no() {
        return this.link_no;
    }

    public void setNode_no( String node_no ) {
        this.node_no = node_no;
    }

    public String getNode_no() {
        return this.node_no;
    }

    public void setRoute_points_id( String route_points_id ) {
        this.route_points_id = route_points_id;
    }

    public String getRoute_points_id() {
        return this.route_points_id;
    }

    public void setScan_user( String scan_user ) {
        this.scan_user = scan_user;
    }

    public String getScan_user() {
        return this.scan_user;
    }

    public void setScan_user_id( String scan_user_id ) {
        this.scan_user_id = scan_user_id;
    }

    public String getScan_user_id() {
        return this.scan_user_id;
    }

    public void setScan_time( String scan_time ) {
        this.scan_time = scan_time;
    }

    public String getScan_time() {
        return this.scan_time;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getGps_coordx() {
        return gps_coordx;
    }

    public void setGps_coordx(String gps_coordx) {
        this.gps_coordx = gps_coordx;
    }

    public String getGps_coordy() {
        return gps_coordy;
    }

    public void setGps_coordy(String gps_coordy) {
        this.gps_coordy = gps_coordy;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
