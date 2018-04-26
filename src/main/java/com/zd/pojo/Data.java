package com.zd.pojo;

import java.io.Serializable;

/**
 * Created by zhangxiusheng on 17/3/26.
 */
public class Data implements Serializable {
    private String token;

    private int firstLogin;

    private int smsCount;

    private int verifyStatus;

    private String timeout;

    public void setToken( String token ) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setFirstLogin( int firstLogin ) {
        this.firstLogin = firstLogin;
    }

    public int getFirstLogin() {
        return this.firstLogin;
    }

    public void setSmsCount( int smsCount ) {
        this.smsCount = smsCount;
    }

    public int getSmsCount() {
        return this.smsCount;
    }

    public void setVerifyStatus( int verifyStatus ) {
        this.verifyStatus = verifyStatus;
    }

    public int getVerifyStatus() {
        return this.verifyStatus;
    }

    public void setTimeout( String timeout ) {
        this.timeout = timeout;
    }

    public String getTimeout() {
        return this.timeout;
    }
}