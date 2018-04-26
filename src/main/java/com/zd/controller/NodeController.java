package com.zd.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zd.common.JsonUtils;
import com.zd.object.ActionRequest;
import com.zd.object.ResultInfo;
import com.zd.object.ScanDataListEnt;
import com.zd.service.NodeInterface;
import com.zd.util.CommandTools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zd.object.UserNodeRequest;

/**
 * 节点模块
 * Created by Administrator on 2017-04-06.
 */
@Controller
@RequestMapping("/node")
public class NodeController {

    //添加一个日志器
    private static final Logger log = LoggerFactory.getLogger(NodeController.class);

    // 获取项目列表的请求参数
    private static final TypeReference<UserNodeRequest> Request_type = new TypeReference<UserNodeRequest>() {};
    private static final TypeReference<ActionRequest> ActionRequest_type = new TypeReference<ActionRequest>() {};

    /**
     * 项目服务实现具体类型,用name定义
     */
    @Autowired
    @Qualifier("nodeService")
    private NodeInterface nodeService;

    @RequestMapping(value = "/getnodelist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getnodelist(HttpServletRequest request, HttpServletResponse response ) {

        ResultInfo responseResult = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("APP请求："+request.getRequestURI());
        log.info("APP请求："+request.getParameter("data"));

        try {
            // 获取请求正文json:
            String postData = request.getParameter("data");

            if (StringUtils.isEmpty(postData)) {
                responseResult.setSuccess(-1);
                responseResult.setCode("404");
                responseResult.setMessage("请求数据格式错误!");
                responseResult.setData(null);
                return responseResult;
            }

            // 获取请求对象包装类:
            UserNodeRequest lrEnt = JsonUtils.readValueByType(postData, Request_type);
//            if (!lrEnt.isValid()) {
//                responseResult.setSuccess(-1);
//                responseResult.setCode("404");
//                responseResult.setMessage("数据格式错误!");
//                responseResult.setData(null);
//                return responseResult;
//            }
            // 提取登录用的参数：用户id
            responseResult = nodeService.getNodeList(lrEnt);

        } catch (Exception ex) {

            responseResult.setSuccess(-1);
            responseResult.setCode("404");
            responseResult.setMessage("获取节点列表异常");
            responseResult.setData(null);

        }
        return responseResult;
    }

    @RequestMapping(value = "/checkfirstnode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo checkFirstNode(HttpServletRequest request, HttpServletResponse response){

        ResultInfo responseResult = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("APP请求："+request.getRequestURI());
        log.info("APP请求："+request.getParameter("data"));

        try {
            // 获取请求正文json:
            String postData = request.getParameter("data");

            if (StringUtils.isEmpty(postData)) {
                responseResult.setSuccess(-1);
                responseResult.setCode("404");
                responseResult.setMessage("请求数据格式错误!");
                responseResult.setData(null);
                return responseResult;
            }

            // 获取请求对象包装类:
            UserNodeRequest lrEnt = JsonUtils.readValueByType(postData, Request_type);
//            if (!lrEnt.isValid()) {
//                responseResult.setSuccess(-1);
//                responseResult.setCode("404");
//                responseResult.setMessage("数据格式错误!");
//                responseResult.setData(null);
//                return responseResult;
//            }
            // 提取登录用的参数：用户id
            responseResult = nodeService.checkFirstNode(lrEnt);

        } catch (Exception ex) {

            responseResult.setSuccess(-1);
            responseResult.setCode("404");
            responseResult.setMessage("获取路由列表异常,请重新尝试!");
            responseResult.setData(null);

        }

        return responseResult;
    }

    @RequestMapping(value = "/task/gettaskstatuslist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getTaskStatusInfo(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        log.info("APP请求："+request.getRequestURI());
        log.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            ActionRequest actionRequest = JsonUtils.readValueByType(postData, ActionRequest_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = nodeService.getTaskStatusData(actionRequest);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取任务状态汇总异常");
        }

        return resultInfo;
    }
}
