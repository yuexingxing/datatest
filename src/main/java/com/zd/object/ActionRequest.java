package com.zd.object;

/**
 * Created by Administrator on 2017-04-05.
 */
public class ActionRequest {

    private String id;
    private String user_name;
    private String user_id;
    private String platform_id;//平台ID
    private String project_id;
    private String route_id;
    private String node_id;
    private int node_num;
    private int link_num;
    private String load_task_id;
    private String task_id;
    private String task_name;
    private String type = "";
    private String bs_type;
    private int flag;
    private String pack_barcode;
    private String pack_no;
    private String rfid;
    private String time;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatform_id() { return platform_id; }
    public void setPlatform_id(String platform_id) {
        this.platform_id = platform_id;
    }

    public String getPack_no() { return pack_no; }
    public void setPack_no(String pack_no) {
        this.pack_no = pack_no;
    }

    public String getPack_barcode() {
        return pack_barcode;
    }
    public void setPack_barcode(String pack_barcode) {
        this.pack_barcode = pack_barcode;
    }

    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getLink_num() {
        return link_num;
    }
    public void setLink_num(int link_num) {
        this.link_num = link_num;
    }

    public int getNode_num() {
        return node_num;
    }
    public void setNode_num(int node_num) {
        this.node_num = node_num;
    }

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProject_id() {
        return project_id;
    }
    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getNode_id() {
        return node_id;
    }
    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getRoute_id() {
        return route_id;
    }
    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getTask_name() {
        return task_name;
    }
    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getLoad_task_id() {
        return load_task_id;
    }
    public void setLoad_task_id(String load_task_id) { this.load_task_id = load_task_id; }

    public String getTask_id() {
        return task_id;
    }
    public void setTask_id(String task_code) {
        this.task_id = task_code;
    }

    public String getBs_type() {
        return bs_type;
    }
    public void setBs_type(String bs_type) {
        this.bs_type = bs_type;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    /**
     * 根据传参类型判断
     * 如果字段有null的,则说明参数格式错误
     */
    public boolean isValid(String type) {

        if(type.equals("project")){
            return !(null == getUser_id());
        }else if(type.equals("route")){
            return !(null == getUser_id() || null == getProject_id());
        }else if(type.equals("node")){
            return !(null == getUser_id() || null == getRoute_id());
        }else if(type.equals("land")){
            return !(null == getUser_id() || null == getNode_id());
        }else  if(type.equals("air")){
            return !(null == getUser_id() || null == getNode_id());
        }

        return true;
    }
}
