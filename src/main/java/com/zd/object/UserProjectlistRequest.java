package com.zd.object;

/**
 * 用户项目列表,请求参数
 * {"user_id":"80D8FD19-4358-413F-A606-E45206E231A5"}
 */
public class UserProjectlistRequest {
    private String user_id;
    private String platform_id;

    public void setUser_id( String user_id ) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setPlatform_id( String platform_id ) {
        this.platform_id = platform_id;
    }

    public String getPlatform_id() {
        return this.platform_id;
    }

    /**
     * 如果字段有null的,则说明参数格式错误
     */
    public boolean isValid() {
        return !(null == getUser_id());
    }

}
