package com.zd.pojo;

/**
 * {
 * "username": "lmt",
 * "token": "12312312312321sqweqweqweqweqwe",
 * "expiredate": "2017-04-01 15:27:23"
 * }
 */
public class UserToken {
    private String username;

    private String token;

    private String logindate;

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return this.token;
    }
    public void setLogindate(String logindate){
        this.logindate = logindate;
    }
    public String getLogindate(){
        return this.logindate;
    }
}
