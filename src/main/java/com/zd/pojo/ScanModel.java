package com.zd.pojo;

import java.io.Serializable;

/**
 * 扫描表结构
 */
public class ScanModel implements Serializable {
    private String id;

    private String route_task_id;

    private String upload_time;

    private int load_number;

    private String link_no;

    private String route_points_id;

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

    public void setRoute_points_id( String route_points_id ) {
        this.route_points_id = route_points_id;
    }

    public String getRoute_points_id() {
        return this.route_points_id;
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
