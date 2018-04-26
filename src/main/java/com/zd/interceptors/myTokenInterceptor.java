package com.zd.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zd.common.Constant;
import com.zd.common.redis.JedisTool;
import com.zd.controller.UserController;
import com.zd.object.ResultInfo;
import com.zd.pojo.UserData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zd.common.DateTimeUtils;
import com.zd.common.HttpUtils;
import com.zd.common.SerializeUtil;

/**
 * 测试URL
 * http://localhost:8080/home/index?token=123
 * 备注:通过用户token可以获取到用户信息 -- 其他 controller 可以不传user_id参数进来,但是为了兼容无redis的情况,就带了
 */
public class myTokenInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(myTokenInterceptor.class);
    //进入 Handler方法之前执行 用于身份认证、身份授权
    //这里用于token检查和维护刷新
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {

        if(1-1 == 0){
            return true;
        }

        // sign 测试时放过注释【TEST】
        String url = request.getRequestURI();

        // 一切post访问都需要验签:
        if (!request.getMethod().toLowerCase().equals("get")) {
            // 验证签名是否正确?
            boolean ckSignCheck = HttpUtils.checkSign(request);
            if (!ckSignCheck) {
                ResultInfo rlt = new ResultInfo();
                rlt.setCode("501");
                rlt.setSuccess(-1);
                rlt.setMessage("签名验证失败,请重新登录!");
                rlt.setData("");

                ObjectMapper objectMapper = new ObjectMapper();
                response.getWriter().write(objectMapper.writeValueAsString(rlt));
                response.getWriter().flush();
                return false;
            }
        }

        //判断url是否是公开 地址（实际使用时将公开 地址配置配置文件中）
//        if (url.indexOf("/user/login") >= 0) {
        if (url.contains("/user/login")) {
            //如果进行登陆提交，放行
            return true;//放行
        }

        //-------------------------------------------
        // 如果满足其他条件的,不拦截.
        //-------------------------------------------
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            ResultInfo rlt = new ResultInfo();
            rlt.setMessage("请登录");
            rlt.setCode("4001");
            rlt.setSuccess(1);
            rlt.setData("");

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(rlt));
            response.getWriter().flush();
            return false;
        }

        // 获取到token过期时间点
        // String rds_token_timeout_point = JedisTool.getKV(token); // redis存储用户内容太少,改为对象了;

        UserData userdata = null;
        byte[] user_byte_value = JedisTool.getInstance().get(token.getBytes());
        if (user_byte_value == null) {
            //【!!!】这里为空是因为UserData数据结构改变,redis不能获取下来导致的
            ResultInfo rlt = new ResultInfo();
            rlt.setMessage("token过期,请重新登录!");
            rlt.setCode("4001");
            rlt.setSuccess(1);
            rlt.setData("");

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(rlt));
            response.getWriter().flush();
            return false;
        }

        Object user_object = SerializeUtil.unserialize(user_byte_value);
        if (user_object == null) {
            ResultInfo rlt = new ResultInfo();
            rlt.setMessage("token过期,请重新登录!");
            rlt.setCode("4001");
            rlt.setSuccess(1);
            rlt.setData("");

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(rlt));
            response.getWriter().flush();
            return false;
        }

        // 获取对象内容:
        userdata = (UserData) user_object;
        //获取token缓存的上一次登录时间,跟当前时间对比,如果太老了,则当做过期处理.
        DateTimeUtils dtutl = new DateTimeUtils();
        String nowStr = dtutl.getNowTimeStr();
        long lastLoginDifTime = dtutl.getDifSeconds(userdata.getLoginTime());
        // 超过 24 小时 则让重新登录
        if (lastLoginDifTime > Constant.token_timeout) {
            JedisTool.removeKV("token@" + token);
            ResultInfo rlt = new ResultInfo();
            rlt.setMessage("token过期,请重新登录!");
            rlt.setCode("4001");
            rlt.setSuccess(1);
            rlt.setData("");

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(rlt));
            response.getWriter().flush();
            return false;
        }
        //.......................................................// sign 测试时放过注释【TEST】

        // 如果都合法,则更新reids中的用户最近一次登录的时间戳

        userdata.setLoginTime(nowStr);
        JedisTool.getInstance().set(token.getBytes(), SerializeUtil.serialize(userdata));
        // JedisTool.setKV(token, nowStr); -- 此方法作废了,只缓存了一个时间str,太少..
        //.......................................................// sign 测试时放过注释【TEST】
        return true;//放行
    }

    public void afterCompletion( HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3 ) throws Exception {
//        String url = arg0.getRequestURI();
//        System.out.println("afterCompletion:" + url);
    }

    public void postHandle( HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3 ) throws Exception {
//        String url = arg0.getRequestURI();
//        System.out.println("postHandle:" + url);
    }

}