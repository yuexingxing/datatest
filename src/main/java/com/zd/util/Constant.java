package com.zd.util;

/**
 * Created by Administrator on 2017-06-07.
 */
public class Constant {

    //南京--9510
//    public static final String URL_SEND_EXCEPTION_MSG = "http://122.225.117.142:9510/api/ScanException";//异常件发送邮箱通知
//    public static final String URL_UPLOAD_PICTURE = "http://47.92.77.121:9509/ImageUploadService.asmx?wsdl";//图片上传

    //ECS-正式库
    public static final String URL_SEND_EXCEPTION_MSG = "http://47.92.77.121:9512/api/ScanException";//
    public static final String URL_UPLOAD_PICTURE = "http://47.92.77.121:9516/ImageUploadService.asmx?wsdl";//

    public static String SCAN_TYPE_LAND = "land";  //陆运
    public static String SCAN_TYPE_SEA = "sea";    //海运
    public static String SCAN_TYPE_AIR = "air";    //空运
    public static String SCAN_TYPE_RAILEAY = "railway";  //铁运
    public static String SCAN_TYPE_LOAD = "load";   //装卸
    public static String SCAN_TYPE_STORAGE = "storage";  //货场
    public static String SCAN_TYPE_CONTAINER = "container";  //集装箱

    public static String SCAN_TYPE_VERIFY = "verify";  //校验
    public static String SCAN_TYPE_SCALE = "scale";  //打尺
    public static String SCAN_TYPE_PACK = "pack";  //包装
    public static String SCAN_TYPE_STRAP = "strap";  //绑扎
    public static String SCAN_TYPE_OFFLINE = "offline";  //下线
    public static String SCAN_TYPE_INSTALL = "install";  //安装
    public static String SCAN_TYPE_STICK = "stick";  //贴唛
}
