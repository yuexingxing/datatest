package com.zd.service;

import com.zd.object.ResultInfo;
import com.zd.object.UserProjectlistRequest;

/**
 * 项目服务接口
 */
public interface ProjectInterface {
    /**
     * 项目路径选择
     * @param user_id
     * @return
     */
    public ResultInfo getProjectRouteList(UserProjectlistRequest lrEnt);
}
