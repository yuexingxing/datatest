package com.zd.common;

import com.zd.common.sign.SignUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 验证数据签名的正确性
     */
    public static boolean checkSign( HttpServletRequest request ) {
        // 获取请求get参数列表
        Map<String, String> map = getQueryParamsMap(request);

        String App_sign = "";
        if (map.containsKey("sign")) {
            App_sign = map.get("sign").toString();
            map.remove("sign");
        }

        // 开始APP来源的验证签名:
        String local_md5_str = SignUtils.getMd5Sign(map, Constant.DATA_SIGN_KEY);

        System.out.println("    ||  HttpUtils.checkSign刷新的datasign  = " + local_md5_str);
        //  System.out.println("||  sign = " + App_sign);

        return local_md5_str.equals(App_sign);
    }

    /**
     * 返回http请求中get参数列表
     */
    public static Map<String, String> getQueryParamsMap( HttpServletRequest request ) {
        Map<String, String> params_One = new HashMap<String, String>();
        try {
            Map<String, String[]> params_Many = request.getParameterMap();
            // 注意：key有可能是重复的；
            for (String key : params_Many.keySet()) {
                String[] values = params_Many.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    // 避免重复的key进来，这里只记录第一个.
                    if (!params_One.containsKey(key)) {
                        params_One.put(key, value);
                    }
                }
            }
        } catch (Exception ex) {
            logger.debug("HttpUtils.getQueryParamsMap异常:" + ex.getMessage());
        }
        return params_One;
    }


}
