package com.zd.service;

import com.zd.object.ActionRequest;
import com.zd.object.ResultInfo;
import com.zd.object.UserNodeRequest;

/**
 * Created by Administrator on 2017-04-06.
 */
public interface NodeInterface {

    /**
     * 项目路径选择
     */
    public ResultInfo getNodeList(UserNodeRequest userNodeRequest );

    /**
     * 判断是否第一个节点
     */
    public ResultInfo checkFirstNode(UserNodeRequest userNodeRequest );

    public ResultInfo getTaskStatusData(ActionRequest request);//任务状态汇总
}