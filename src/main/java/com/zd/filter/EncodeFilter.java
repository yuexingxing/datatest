package com.zd.filter;

/**
 * Created by zhangxiusheng on 17/3/27.
 */

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.annotation.WebFilter;

/**
 * 常用的过滤器::
 * Authentication Filters
 * Logging and Auditing Filters
 * Image conversion Filters
 * Data compression Filters
 * Encryption Filters
 * Tokenizing Filters
 * Filters that trigger resource access events
 * XSL/T filters
 * Mime-type chain Filter
 */
@WebFilter("/EncodeFilter")
public class EncodeFilter implements Filter {
    Map<String, String> params = new HashMap<String, String>();

    /**
     * Default constructor.
     */
    public EncodeFilter() {

    }

    //@Override
    public void init( FilterConfig cfg ) throws ServletException {
        Enumeration<String> names = cfg.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, cfg.getInitParameter(name));
        }
        //!!
//        System.out.println("EncodeFilter.init(),cfg = " + cfg.toString());
        //:: EncodeFilter.init(),cfg = ApplicationFilterConfig[name=com.zd.filter.EncodeFilter, filterClass=com.zd.filter.EncodeFilter]
    }

    //@Override
    public void destroy() {
    //System.out.println("EncodeFilter.destroy !!");
    }

    /**
     * 发生在所有servlet的doGet或者doPost方法之前；-- 先行过滤处理一次;
     */
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
        String encodeCoding = params.get("EncodeCoding");

        if (!"UTF-8".equals(encodeCoding)) {
            encodeCoding = "UTF-8";
        }
        request.setCharacterEncoding(encodeCoding);
        response.setCharacterEncoding(encodeCoding);

        // pass the request along the filter chain
        chain.doFilter(request, response);
    }

}
