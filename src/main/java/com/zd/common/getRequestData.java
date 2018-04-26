package com.zd.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * Created by zhangxiusheng on 17/3/29.
 */
public class getRequestData {

    // ServletRequest HttpServletRequest
    private static Logger logger = LoggerFactory.getLogger(getRequestData.class);

    /**
     * 如果是加密的数据,则这里进行解密处理?
     *
     * @param request
     * @return
     */
    public static String getPostData( ServletRequest request ) {
        StringBuffer jbF = new StringBuffer();
        try {
            // 获取请求数据内容
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null)
                    jbF.append(line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            jbF.append(ex.getMessage());
        }
        return jbF.toString();
    }
}
