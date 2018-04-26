package com.zd.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zd.common.getRequestData;
import com.zd.object.ResultInfo;
import com.zd.service.ProjectInterface;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zd.common.JsonUtils;

import com.zd.object.UserProjectlistRequest;

/**
 *
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    //添加一个日志器
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // 内部错误编码,写入到日志,用于跟踪错误的
    private static final String Inner_log_Errcode = "【ProjectController-ERR】-";

    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    // 获取项目列表的请求参数
    private static final TypeReference<UserProjectlistRequest> Request_type = new TypeReference<UserProjectlistRequest>() {
    };

    /**
     * 项目服务实现具体类型,用name定义
     */
    @Autowired
    @Qualifier("projectService")
    private ProjectInterface projectService;

    /**
     * 请求参数:
     * 【URL】
     * "token": "e04de2b9004ee917ded1546e6cc217a0",
     * <p/>
     * http://localhost:8080/project/getroutelist?appid=zd_xl&timestamp=20161207150248&appver=1.0.1&format=json&version=1.0&sign=124411e41bb6f0f087755af1eea768a8&salt=123321&token=e04de2b9004ee917ded1546e6cc217a0
     * <p/>
     * 【post】部分:
     * {"user_id":"80D8FD19-4358-413F-A606-E45206E231A5"}
     * --> "token": "e04de2b9004ee917ded1546e6cc217a0"
     */
    @RequestMapping(value = "/getprojectlist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getProjectlist( HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo responseResult = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("program: " + request.getParameter("data"));
        try {
            // 获取请求正文json:
            String postData = request.getParameter("data");
            if (StringUtils.isEmpty(postData)) {
                responseResult.setSuccess(-1);
                responseResult.setCode("404");
                responseResult.setMessage("请求数据格式错误");
                responseResult.setData(null);
                return responseResult;
            }

            // 获取请求对象包装类:
            UserProjectlistRequest lrEnt = JsonUtils.readValueByType(postData, Request_type);
            if (!lrEnt.isValid()) {
                responseResult.setSuccess(-1);
                responseResult.setCode("404");
                responseResult.setMessage("数据格式错误");
                responseResult.setData(null);
                return responseResult;
            }

            // 提取登录用的参数：用户id
            responseResult = projectService.getProjectRouteList(lrEnt);

            // 结果直接返回到客户端

        } catch (Exception ex) {

            responseResult.setSuccess(-1);
            responseResult.setCode("404");
            responseResult.setMessage("获取项目列表异常");
            responseResult.setData(null);

            // 异常信息记录到日志文件:
            logger.error(Inner_log_Errcode + "getProjectlist.Exception:" + ex.getMessage());

        }
        return responseResult;
    }

}
