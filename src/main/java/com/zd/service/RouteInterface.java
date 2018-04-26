package com.zd.service;

import com.zd.object.ResultInfo;
import com.zd.object.UserRouteRequest;

/**
 * 项目服务接口
 */
public interface RouteInterface {
    /**
     * 项目路径选择
     */
    public ResultInfo getRouteList( UserRouteRequest userrouteReq );
}
