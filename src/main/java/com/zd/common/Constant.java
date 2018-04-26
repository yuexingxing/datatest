package com.zd.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Created by zhangxiusheng on 17/3/20.
 */
public class Constant {

    private static final Logger logger = LoggerFactory.getLogger(Constant.class);
    public static String appkey;
    public static String appid;
    public static String format;
    public static String version;
    public static String umeng_id;
    public static String environment;
    public static String zhuge_id;
    public static String growingIO_id;

    // 数据签名验证Key
    public static String DATA_SIGN_KEY = "hgjkbiuytfghj7865rtyfghvbnjhiuo908";

    //--------------------------------------------------------------
    // 是否需要加密解密
    public static boolean Need_Encryp = false;
    // 需要加密对应的APP程序版本号:
    public static ArrayList<String> Need_Encryp_APPVER_List = new ArrayList<String>(Arrays.asList("2.0", "3.0", "4.0"));
    // 需要阻挡使用的APP版本号:
    public static ArrayList<String> FORBID_APPVER_List = new ArrayList<String>(Arrays.asList("1.0"));
    //--------------------------------------------------------------
    // 登录token过期时间 单位秒 (默认24小时)
    public static int token_timeout = 24 * 60 * 60;
    // redis 服务器IP地址和端口号

     public static String Redis_IP = "127.0.0.1";
//    public static String Redis_IP = "192.168.3.60";//
    public static int Redis_Port = 6379;

    /**
     * 获取当前程序目录 --> 只是为了得到配置文件中的数据
     */
    public static String Get_workPath() {
        String inf_dirPath = "";
        File directory = new File("");//设定为当前文件夹
        try {
            inf_dirPath = directory.getCanonicalPath();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "";
        }

        String osname = SeparatorUtils.getOSname().toUpperCase();
        if (osname.contains("WINDOWS")) {
            // windows ...
            inf_dirPath = inf_dirPath.replace("classes\\", "").replace("/", "\\");
            if (inf_dirPath.startsWith("\\")) {
                inf_dirPath = inf_dirPath.substring(1, inf_dirPath.length());
                inf_dirPath = inf_dirPath.replace("classes\\", "");
            }
        } else {
            inf_dirPath = inf_dirPath.replace("classes/", "");
        }
        return inf_dirPath;
    }

}
