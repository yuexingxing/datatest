package com.zd.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zd.common.JsonUtils;
import com.zd.object.ActionRequest;
import com.zd.object.ResultInfo;
import com.zd.pojo.QueryData;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.util.TextUtils;
import org.apache.ibatis.session.SqlSession;
import sun.misc.BASE64Decoder;
import sun.misc.Service;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Document;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工具类
 * Created by Administrator on 2017-04-07.
 */
public class CommandTools {

    public static final TypeReference<ActionRequest> Action_Request_type = new TypeReference<ActionRequest>() {};
    public static final TypeReference<QueryData> Action_Query_type = new TypeReference<QueryData>() {};

    /**
     * 判断请求参数是否为空
     * 如果为空返回true
     * 否则返回false
     * @param request
     * @param resultInfo
     * @return
     */
    public static boolean isDataEmpty(HttpServletRequest request, ResultInfo resultInfo){

        String data = request.getParameter("data");
        if (StringUtils.isEmpty(data)){

            resultInfo.setSuccess(-1);
            resultInfo.setCode("404");
            resultInfo.setMessage("参数不能为空");
            resultInfo.setData(null);

            return true;
        }

        return false;
    }

    /**
     * 判断请求参数是否合法
     * @param request
     * @param resultInfo
     * @param type
     * @return
     */
    public static boolean isParameterValid(HttpServletRequest request, ResultInfo resultInfo, String type){

        String postData = request.getParameter("data");

        try {
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, Action_Request_type);
            if (!lrEnt.isValid(type)) {
                resultInfo.setSuccess(-1);
                resultInfo.setCode("404");
                resultInfo.setMessage("请求数据格式错误");
                resultInfo.setData(null);
                return false;
            }
        }catch(Exception e){
            resultInfo.setSuccess(-1);
            resultInfo.setCode("404");
            resultInfo.setMessage("请求数据解析异常");
            resultInfo.setData(null);
            return false;
        }

        return true;
    }

    /**
     * 判断解析json异常处理
     * @param
     * @param resultInfo
     * @param type
     */
    public static void analyzeException(ResultInfo resultInfo, String type){

        resultInfo.setSuccess(-1);
        resultInfo.setCode("404");
        resultInfo.setMessage(type);
        resultInfo.setData(null);
    }

    public static ResultInfo post(String sql) throws IOException {

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            // 查询项目列表:
            final List<Map<String, Object>> maps = sqlMapper.selectList(sql);

            if (null != maps & maps.size() > 0) {
                rltInfo.setSuccess(0);
                rltInfo.setMessage("查询成功!");
                rltInfo.setData(maps);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setMessage("查询失败!");
                rltInfo.setData(maps);
            }

        } catch (Exception ex) {
            rltInfo.setSuccess(-1);
            rltInfo.setMessage("查询失败!");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 获取uuid随机数
     * @return
     */
    public static String getUUID(){

        return UUID.randomUUID() + "";
    }

    /**
     * 获取当前系统时间
     * @return
     */
    public static String getTime(){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getTimeName(){

        SimpleDateFormat date = new SimpleDateFormat("YYYYMMdd_HHmmss");
        return date.format(new Date());
    }

    public static String getFolderName(){

        SimpleDateFormat date = new SimpleDateFormat("YYYY-MM-dd");
        return date.format(new Date());
    }

    /**
     * base64转化为图片并保存到服务器中
     * @param base64
     * @return
     */
    public static boolean base64ToFile(String base64){

        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        String serverPath = getPicPath();

        try{

            byte[] bytes1 = decoder.decodeBuffer(base64);

            File file = new File(serverPath);
            if(!file.exists()){
                file.mkdirs();
            }

            OutputStream out = new FileOutputStream(file.getAbsolutePath() + "/" + CommandTools.getTimeName() + ".jpg");
            out.write(bytes1);
            out.flush();
            out.close();

            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 配置文件获取
     * 获取保存图片的服务器地址
     * @return
     */
    public static String getPicPath(){

        try{
            InputStream inputStream = CnfgUtil.class.getClassLoader().getResourceAsStream("config/file.properties");
            Properties pro = new Properties();
            pro.load(inputStream);
            String fileType = pro.getProperty("file.local_path");

            return fileType;
        }catch(Exception e){
            e.printStackTrace();;
        }

        return null;
    }

    /**
     * 获取任务id
     *  @user_id		VARCHAR(50) ,
     *  @node_id		VARCHAR(50) ,
     *  @type			VARCHAR(50) ,--操作类型
     *  @load_task_id	VARCHAR(50) ,
     *  @node_num		INT ,
     *  @link_num		INT ,
     *  @flag			INT ,
     *  @task_name		VARCHAR(100),
     * */
    public static ResultInfo getTaskId(ActionRequest request, SqlSession sen, ResultInfo rltInfo){

        try {
            SqlMapper sqlMapper = new SqlMapper(sen);

            if(TextUtils.isEmpty(request.getLoad_task_id())){
                request.setLoad_task_id(null);
            }

//            request.setType("pack");
//            request.setUser_id("F629A004-D188-4ADB-B657-97445CB54B82");
//            request.setNode_id("609E55E3-8678-408D-A0BC-80715ED57ECA");
//            request.setFlag(0);
//            request.setLink_num(1);
//            request.setNode_num(1);
//            request.setTask_name("上海正中物流有限公司");

            String strReq = "exec dbo.app_set_task #{user_id}, #{node_id}, #{type}, #{load_task_id}, #{node_num}, #{link_num}, #{flag}, #{task_name}";
            Map<String, Object> maps = sqlMapper.selectOne(strReq, request);

            if(maps != null && maps.size() > 0){
                rltInfo.setSuccess(0);
                rltInfo.setCode(maps.get("Task_ID").toString());
                rltInfo.setMessage("获取taskId成功");
                System.out.println("获取taskId成功： " + rltInfo.getCode());
            }else{
                rltInfo.setSuccess(1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("获取taskId失败");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("taskId获取异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    public  void test(){


    }

    /**
     * 获取图片路径，根据当天日期创建文件夹
     * 这里用的是物理路径不是居然对路径
     * int type 1-Web相对路径 attachment_url  /ScanException/loginbg.jpg
     *          2-存储物理相对路径 attachment_path  \ScanException\loginbg.jpg
     * @return
     */
    public static String getPicFolderName(int type){

        if(type == 1){

            return "ScanException/" + CommandTools.getFolderName() + "/";
        }

        return "ScanException\\" + CommandTools.getFolderName() + "\\";
    }

    /**
     * long类型时间转换
     * @param time
     * @return
     */
    public static String getLong2Time(long time){

        if(time < 1000){
            return "";
        }

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }
}
