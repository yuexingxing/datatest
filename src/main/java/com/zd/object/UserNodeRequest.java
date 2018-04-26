package com.zd.object;

/**
 * Created by Administrator on 2017-04-06.
 */
public class UserNodeRequest {

    private String user_id;

    private String project_id;

    private String route_id;

    private String node_id;

    public void setNode_id( String node_id ) {
        this.node_id = node_id;
    }

    public String getNode_id() {
        return this.node_id;
    }

    public void setUser_id( String user_id ) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setProject_id( String project_id ) {
        this.project_id = project_id;
    }

    public String getProject_id() {
        return this.project_id;
    }

    public void setRoute_id( String route_id ) {
        this.route_id = route_id;
    }

    public String getRoute_id() {
        return this.route_id;
    }

    /**
     * 如果字段有null的,则说明参数格式错误
     */
    public boolean isValid() {
        return !(null == getUser_id() || null == getProject_id() || null == getRoute_id());
    }
}
