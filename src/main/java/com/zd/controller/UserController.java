package com.zd.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zd.common.ConvertUtils;
import com.zd.common.token.TokenUtils;
import com.zd.object.ActionRequest;
import com.zd.object.LoginRequest;
import com.zd.object.ResultInfo;
import com.zd.object.User;
import com.zd.pojo.UserToken;
import com.zd.service.UserInterface;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zd.common.JsonUtils;
import com.zd.pojo.UserData;

import java.util.List;
import java.util.Map;

/**
 * 本机调试的部署目录:
 * /Users/zhangxiusheng/Library/Caches/IntelliJIdea15/tomcat/U nnamed_datatest
 * Created by zhangxiusheng on 17/3/25.
 * 用户资料相关
 * 【1】登录
 * /user/login
 * <p/>
 * 【2】
 */
@Controller
@RequestMapping("/user")
public class UserController {

    // 内部错误编码,写入到日志,用于跟踪错误的
    private static final String Inner_log_Errcode = "【UserController-ERR】-";

    //添加一个日志器
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // 登录请求参数
    private static final TypeReference<LoginRequest> LoginRequest_type = new TypeReference<LoginRequest>() {
    };

//    private static final TypeReference<List<UserData>> UserData_type = new TypeReference<List<UserData>>() {
//    };

    /**
     * 自动装配一个指定名称的接口实现类 userService_v001
     */
    @Autowired
    @Qualifier("userService")
    private UserInterface userService;

    /**
     * 设计的请求参数:
     * 【URL】
     * （&sign=ad782c1d763af00a1130e9020af4eff5 是正确的,备份测试用）
     * http://localhost:8080/user/login?appid=zd_xl&timestamp=20161207150248&sign=ad782c1d763af00a1130e9020af4eff5&appver=2.2.9&format=json&version=1.0&salt=123321
     * <p/>
     * 【post】部分:
     * [{"username":"001","userpwd":"b76986e808df6e87647d7e576e1f8b5e"}]
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo login( HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo loginResult = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("APP请求："+request.getRequestURI());
        log.info("APP请求："+request.getParameter("data"));
        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");
            if (StringUtils.isEmpty(postData)) {
                loginResult.setSuccess(-1);
                loginResult.setCode("404");
                loginResult.setMessage("请求数据格式错误!");
                loginResult.setData(null);
                return loginResult;
            }

            // 获取请求对象包装类::
            // {"username":"lmt","userpwd":"08C52FC23BA98DC1913922C97A781317"}
            LoginRequest lrEnt = JsonUtils.readValueByType(postData, LoginRequest_type);
            if (!lrEnt.isValid()) {
                loginResult.setSuccess(-1);
                loginResult.setCode("404");
                loginResult.setMessage("请求参数不能为空");
                loginResult.setData(null);
                return loginResult;
            }

            // 提取登录用的参数：用户名,密码
            loginResult = userService.login(lrEnt.getUsername(), lrEnt.getUserpwd());

            // 根据登录状态做一些表的处理:刷新token等.
            if (0 == loginResult.getSuccess()) {
                // 获取用户对象:
                Object userDataObj = loginResult.getData();

                // json数组直接转化为对象列表数组
                List<UserData> lst_user = (List<UserData>) userDataObj;

                // 合法登录则刷新token值
                if (null != lst_user && lst_user.size() > 0) {
                    Map<String, Object> map = (Map<String, Object>) lst_user.get(0);

                    //从 HashMap 中获取用户对象:
                    UserData userData = new UserData();
                    Class<?> type = userData.getClass();
                    UserData userDataX = (UserData) ConvertUtils.mapToObjectByreflect(map, type);

                    // 创建或者更新用户的登录token相关（token和过期时间);
                    // 并且把用户对象放到redis中
                    UserToken usrtk = TokenUtils.CreateUserToken(userDataX);

                    // 讲刷新的token,返回给APP
                    loginResult.setToken(usrtk.getToken());

                    // System.out.println("【登录】token=" + usrtk.getToken() + ";" + usrtk.getUsername() + ";" + usrtk.getLogindate());
                    //【登录】token=c9f13600d62ed2d69a6e5ceaf0c7d68f;lmt;2017-04-01 16:01:22
                    //【登录】token=df45645fe9096d79daa6679d12303d76;lmt;2017-04-01 16:02:20
                }
            } else {
                // 登录错误,直接返回了.

            }
        } catch (Exception ex) {

            ex.printStackTrace();
            loginResult.setSuccess(-1);
            loginResult.setCode("404");
            loginResult.setMessage("登录异常");
            loginResult.setData(null);

            /**
             * ### Error querying database.  Cause: com.microsoft.sqlserver.jdbc.SQLServerException:
             * 驱动程序无法通过使用安全套接字层(SSL)加密与 SQL Server 建立安全连接。
             * 错误:“Broken pipe (Write failed) ClientConnectionId:174dbb19-f054-44f4-8e1a-b582ae35d3e9”。
             ### The error may involve SELECT.1888497166
             ### The error occurred while executing a query
             */

            log.error(Inner_log_Errcode + "login.Exception:" + ex.getMessage());
            // System.out.println(ex.getMessage());

        }

        return loginResult;
    }

    @RequestMapping(value = "/updatepassword", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo update_password(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        log.info("APP请求："+request.getRequestURI());
        log.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            LoginRequest lrEnt = JsonUtils.readValueByType(postData, LoginRequest_type);

            resultInfo = userService.update_password(lrEnt.getUsername(), lrEnt.getUserpwd(), lrEnt.getNew_userpwd());
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "更新密码异常");
        }

        return resultInfo;
    }

}
