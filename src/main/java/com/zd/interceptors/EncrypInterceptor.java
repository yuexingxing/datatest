package com.zd.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangxiusheng on 17/3/29.
 */
public class EncrypInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(EncrypInterceptor.class);

    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
        return true;//放行
    }

    public void afterCompletion( HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3 ) throws Exception {
        //String url = arg0.getRequestURI();
        //System.out.println("afterCompletion:" + url);
    }

    public void postHandle( HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3 ) throws Exception {
        //String url = arg0.getRequestURI();
        //System.out.println("postHandle:" + url);
    }

}
