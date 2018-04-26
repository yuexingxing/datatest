package com.zd.object;

/**
 * 用户登录参数
 * {"username": "001","userpwd": "b76986e808df6e87647d7e576e1f8b5e"}
 */
public class LoginRequest {
    private String username;
    private String userpwd;

    private String new_userpwd;

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUserpwd( String userpwd ) {
        this.userpwd = userpwd;
    }

    public String getUserpwd() {
        return this.userpwd;
    }

    public String getNew_userpwd() {
        return new_userpwd;
    }

    public void setNew_userpwd(String new_userpwd) {
        this.new_userpwd = new_userpwd;
    }

    /**
     * 如果字段有null的,则说明参数格式错误
     */
    public boolean isValid() {
        return !(null == getUsername() || null == getUserpwd());
    }
}
