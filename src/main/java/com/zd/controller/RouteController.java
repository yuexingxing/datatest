package com.zd.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zd.common.getRequestData;
import com.zd.object.ResultInfo;
import com.zd.service.RouteInterface;
import com.zd.service.RouteServiceImpl;
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

import com.zd.object.UserRouteRequest;

/**
 * URL:
 * /route/getroutelist
 * <p/>
 * 路径列表，条件：用户ID、项目ID
 * UserRouteRequest( {"user_id":"80D8FD19-4358-413F-A606-E45206E231A5","project_id":"80D8FD19-4358-413F-A606-E45206E231A5"})
 */
@Controller
@RequestMapping("/route")
public class RouteController {

    // 内部错误编码,写入到日志,用于跟踪错误的
    private static final String Inner_log_Errcode = "【RouteController-ERR】-";

    private static final Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);

    // 获取项目列表的请求参数
    private static final TypeReference<UserRouteRequest> Request_type = new TypeReference<UserRouteRequest>() {
    };

    /**
     * 项目服务实现具体类型,用name定义
     */
    @Autowired
    @Qualifier("routeService")
    private RouteInterface routeService;

    /**
     * 登录之后,根据用户明查询项目列表,返回一个json数组即可
     *
     * @return 测试数据:
     * {"user_id":"80D8FD19-4358-413F-A606-E45206E231A5","project_id":"80D8FD19-4358-413F-A606-E45206E231A5"}
     */
    @RequestMapping(value = "/getroutelist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getroutelist( HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo responseResult = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();
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
            UserRouteRequest lrEnt = JsonUtils.readValueByType(postData, Request_type);
            if (!lrEnt.isValid()) {
                responseResult.setSuccess(-1);
                responseResult.setCode("404");
                responseResult.setMessage("请求参数不能为空");
                responseResult.setData(null);
                return responseResult;
            }
            // 提取登录用的参数：用户id
            responseResult = routeService.getRouteList(lrEnt);

        } catch (Exception ex) {

            responseResult.setSuccess(-1);
            responseResult.setCode("404");
            responseResult.setMessage("获取路由列表异常");
            responseResult.setData(null);

            // 异常信息记录到日志文件:
            logger.error(Inner_log_Errcode + "getroutelist.Exception:" + ex.getMessage());

        }
        return responseResult;
    }
}
