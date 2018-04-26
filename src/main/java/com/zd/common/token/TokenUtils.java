package com.zd.common.token;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.zd.common.DateTimeUtils;
import com.zd.common.SerializeUtil;
import com.zd.common.redis.JedisTool;
import com.zd.pojo.UserData;
import com.zd.pojo.UserToken;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
//import org.apache.commons.lang.time.FastDateFormat;// FastDateFormat 用你,为毛不行。。。

/**
 * Created by zhangxiusheng on 17/3/27.
 * 放到redis中做过期管理;
 * 只有两个逻辑:
 * 1)没有的踢出去;
 * 2)有的更新token时间戳,过期时间再议;
 */
public class TokenUtils {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    private static final String privateKey = "qwsdcvgy789op;.,mnhyt5432wdfghjko0-=";

    public static String getToken( String password, String date ) {
        return Hashing.md5().newHasher().
                putString(password, Charsets.UTF_8).
                putString(privateKey, Charsets.UTF_8).
                putString(date, Charsets.UTF_8).hash().toString();
    }

    public static String getToken( String password, Date date ) {
        return Hashing.md5().newHasher().
                putString(password, Charsets.UTF_8).
                putString(privateKey, Charsets.UTF_8).
                putString(getDate(date), Charsets.UTF_8).hash().toString();
    }

    public static String getToken( String password ) {
        return Hashing.md5().newHasher().
                putString(password, Charsets.UTF_8).
                putString(privateKey, Charsets.UTF_8).putString(getDate(), Charsets.UTF_8).hash().toString();
    }

    public static boolean validToken( String token, String password ) {
        String confirm = getToken(password);
        if (confirm.equals(token)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDate() {
        Date date = new Date(System.currentTimeMillis());
        return FastDateFormat.getInstance("yyyyMMddHH").format(date);
    }

    public static String getDate( Date now ) {

        return FastDateFormat.getInstance("yyyyMMddHH").format(now);
    }

    public static String getNextHour( Date now ) {
        Date date = new Date(now.getTime() + 60 * 60 * 1000);
        return FastDateFormat.getInstance("yyyyMMddHH").format(date);
    }


    /**
     * 检查是否存在token,如果没有,踢回去::拒绝调用接口;
     * 返回如下对象是给程序自己用...
     * {
     * "username": "lmt",
     * "token": "12312312312321sqweqweqweqweqwe",
     * "logindate": "2017-04-01 15:27:23"
     * }
     */
    public static UserToken CreateUserToken( UserData userData ) {
        UserToken utk = new UserToken();
        try {
            String username = userData.getUsername();

            DateTimeUtils dtu = new DateTimeUtils();
            String NowTimeStr = dtu.getNowTimeStr();

            // 产生token的种子数值:
            String tokenKey = username + "#" + NowTimeStr;
            String tokenStr = getToken(tokenKey);

            // 设置登录帐号对应的 token 值
//            JedisTool.setKV(username, tokenStr);

            //  // 设置用户token获取的时间 -- 此方法作废..
            //  JedisTool.setKV(tokenStr, NowTimeStr);

            // 存储token创建时间到用户对象
            userData.setLoginTime(NowTimeStr);

            // 用户tokenString字符串存储对应的用户对象(对象序列化,包括登录时间--也就是token的刷新时间)
//            JedisTool.getInstance().set(tokenStr.getBytes(), SerializeUtil.serialize(userData));

            // 用户登录token相关返回:
            utk.setUsername(username);
            utk.setToken(tokenStr);
            utk.setLogindate(NowTimeStr);

            //
            logger.debug("CreateUserToken:username=" + username + ";    tokenStr=" + tokenStr + " ;    NowTimeStr=" + NowTimeStr);

        } catch (Exception ex) {
            logger.error("CreateUserToken异常:" + ex.getMessage() + "\r\n...当前登录用户:" + userData.getUsername());
        }
        return utk;
    }

}