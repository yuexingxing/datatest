package com.zd.object;

/**
 * 用户项目下面的路由列表
 * {"user_sid":"B37D2876-4821-4E22-AB95-E37E918FE0B0","project_fid":"8119B0DD-89D0-4F56-BC26-2300CFB742E6"}
 */
public class UserRouteRequest {
    private String user_id;

    private String project_id;

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

    /**
     * 如果字段有null的,则说明参数格式错误
     */
    public boolean isValid() {
        return !(null == getUser_id() || null == getProject_id());
    }
}
