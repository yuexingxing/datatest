package com.zd.common.redis;

import java.util.HashMap;
import java.util.Map;

import com.zd.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by zhangxiusheng on 17/3/28.
 */
public class JedisTool {
    private static Logger logger = LoggerFactory.getLogger(JedisTool.class);
    //private static String Redis_IP = "192.168.3.60";//【注意】先写死,后面放配置文件中
    //private static int Redis_Port = 6379;
    /**
     * KV-缓存列表（内存）
     */
    private static Map<String, String> KV_Map = new HashMap<String, String>();
    public static Jedis jedis = null;
    private static boolean is_Redis_On = true;  // 单个redis服务器可用状态

    //    static {
    //        try {
    //            jedis = new Jedis(Constant.Redis_IP, Constant.Redis_Port);
    //            jedis.connect();
    //            is_Redis_On = true;
    //        } catch (Exception e) {
    //            logger.error(e.getMessage());
    //            e.printStackTrace();
    //        }
    //    }

    /**
     * 获取连接实例--单实例对象
     */
    public static Jedis getInstance() {
        if (null != jedis) {//&& jedis.isConnected()
            //            jedis.connect();
            //            if (jedis.isConnected()) {
            //                logger.debug("没有链接三..");
            //            } else {
            //                logger.debug("OK链接三..");
            //            }
            return jedis;
        } else {
            // 重新初始化:
            try {
                jedis = new Jedis(Constant.Redis_IP, Constant.Redis_Port);
                if (null == jedis) {
                    is_Redis_On = false;
                    jedis = null;
                    return null;
                }
                jedis.connect();
                is_Redis_On = true;

                return jedis;
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void disConnect() {
        try {
            jedis.disconnect();
        } catch (Exception ex) {
            System.out.println("disConnect():" + ex.getMessage());
        }
    }

    /**
     * 设置 k-v
     */
    public static void setKV( String key, String value ) {
        try {
            jedis = getInstance();
            if (is_Redis_On) {
                if (jedis.exists(key)) {
                    jedis.del(key);
                }
                jedis.set(key, value);
            } else {// 如果没有redis则设置到Map缓存中;
                if (KV_Map.containsKey(key)) {
                    KV_Map.remove(key);
                }
                KV_Map.put(key, value);
            }
        } catch (Exception ex) {
            logger.error("【setKV】key=[" + key + "]异常:" + ex.getMessage());
        }
    }

    /**
     * 获取k对应的v 如果无法链接到redis服务器，则 报告 java.net.SocketTimeoutException: connect
     * timed out异常:
     */
    public static String getKV( String key ) {
        String value = "";
        try {
            jedis = getInstance();
            if (is_Redis_On) {
                if (jedis.exists(key)) {
                    value = jedis.get(key);
                }
            } else {// 如果没有redis则设置到Map缓存中;
                if (KV_Map.containsKey(key)) {
                    value = KV_Map.get(key);
                }
            }
        } catch (Exception ex) {
            logger.error("【getKV】key=[" + key + "]异常:" + ex.getMessage());
        }
        return value;
    }

    /**
     * 移除 k-v
     */
    public static void removeKV( String key ) {
        try {
            jedis = getInstance();
            if (is_Redis_On) {
                if (jedis.exists(key)) {
                    jedis.del(key);
                }
            } else {
                if (KV_Map.containsKey(key)) {
                    KV_Map.remove(key);
                }
            }
        } catch (Exception ex) {
            logger.error("【removeKV】key=[\" + key + \"]异常:" + ex.getMessage());
        }
    }

}