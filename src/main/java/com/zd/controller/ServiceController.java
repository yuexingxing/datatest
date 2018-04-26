package com.zd.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zd.common.ConvertUtils;
import com.zd.common.JsonUtils;
import com.zd.common.token.TokenUtils;
import com.zd.dao.ServiceDataDao;
import com.zd.object.ActionRequest;
import com.zd.object.LoginRequest;
import com.zd.object.ResultInfo;
import com.zd.pojo.UserData;
import com.zd.pojo.UserToken;
import com.zd.service.ActionInterface;
import com.zd.service.UserInterface;
import com.zd.util.CommandTools;
import com.zd.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/service")
public class ServiceController {

    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    // 登录请求参数
    private static final TypeReference<LoginRequest> LoginRequest_type = new TypeReference<LoginRequest>() {
    };

    /**
     * 自动装配一个指定名称的接口实现类 userService_v001
     */
    @Autowired
    @Qualifier("actionService")
    private ActionInterface actionService;

    @RequestMapping(value = "/scale/getolddata", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getOldData( HttpServletRequest request, HttpServletResponse response ) {

        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        SqlSession sen = MyBatisUtil.getSqlSession();
        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            ActionRequest actionRequest = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            resultInfo = new ServiceDataDao().getOldScaleData(actionRequest, sen, resultInfo);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取打尺数据比对异常");
        }finally {
            sen.close();
            sen = null;
        }

        return resultInfo;
    }

    @RequestMapping(value = "/pack/getpackwaydata", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getPackWayData( HttpServletRequest request, HttpServletResponse response ) {

        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        SqlSession sen = MyBatisUtil.getSqlSession();
        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            ActionRequest actionRequest = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            resultInfo = new ServiceDataDao().getPackWayData(actionRequest, sen, resultInfo);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取包装方式异常");
        }finally {
            sen.close();
            sen = null;
        }

        return resultInfo;
    }

}
