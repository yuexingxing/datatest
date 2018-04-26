package com.zd.object;

import org.junit.Test;

import java.io.Serializable;

/**
 * 用户实体类
 */
public class User implements Serializable {
    //id
    private int id;
    //用户名
    private String userName;
    //密码
    private String password;
    //真实姓名
    private String trueName;
    //邮件
    private String email;
    //电话号码
    private String phone;
    //性别
    private String sex;
    //用户组
    private int group_id;

    //密码
    private String new_password;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName( String trueName ) {
        this.trueName = trueName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex( String sex ) {
        this.sex = sex;
    }

    public int getGroup_Id() {
        return group_id;
    }

    public void setGroup( int group_id ) {
        this.group_id = group_id;
    }

    public  String getNew_password() {
        return new_password;
    }
    public void   setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public User() {
    }
}