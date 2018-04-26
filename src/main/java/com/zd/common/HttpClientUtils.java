package com.zd.common;

import com.fasterxml.jackson.core.type.TypeReference;
// import com.zd.common.JsonUtils;
import com.zd.common.sign.SignUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * @author wdx
 * @ClassName: HttpClientUtils
 * @Description: HttpClientUtils
 * @date 2016年4月10日 下午9:45:33
 */

public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * @param url
     * @param paraMap
     * @param type
     * @return Object
     * @throws IOException
     * @Description: post方式请求axpApi
     */

    public static Object post(String url, Object paraMap, TypeReference<?> type) throws IOException {

        logger.debug("Http Post Url:" + url);

        //序列化成 json
        String data = JsonUtils.translateToJson(paraMap);
        //准备发送的参数
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("timestamp", System.currentTimeMillis() + "");
        paramMap.put("data", data);
        paramMap.put("appid", Constant.appid);
        paramMap.put("format", Constant.format);
        paramMap.put("version", Constant.version);

        //生成签名
        String sign = SignUtils.getMd5Sign(paramMap, Constant.appkey);
        paramMap.put("sign", sign);

        //http post 参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
        while ($it.hasNext()) {
            Map.Entry<String, String> entry = $it.next();
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        logger.debug("Http Post Para:" + paramMap);

        //http post 请求
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        CloseableHttpResponse response = httpclient.execute(httpPost);

        int status = response.getStatusLine().getStatusCode();

        logger.debug("Http Post Status:" + status);

        if (status != 200)
            return null;

        try {
            //返回结果 格式转换
            String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            logger.debug("Http Post Result:" + result);
            return JsonUtils.readValueByType(result, type);
        } finally {
            response.close();
        }
    }

    public static Object post(String url, Object paraMap, String timestamp, TypeReference<?> type) throws IOException {
        logger.debug("Http Post Url:" + url);

        // 序列化成 json
        String data = JsonUtils.translateToJson(paraMap);
        // 准备发送的参数
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("timestamp", timestamp);
        paramMap.put("data", data);
        paramMap.put("appid", Constant.appid);
        paramMap.put("format", Constant.format);
        paramMap.put("version", Constant.version);

        // 生成签名
        String sign = SignUtils.getMd5Sign(paramMap, Constant.appkey);
        paramMap.put("sign", sign);

        // http post 参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
        while ($it.hasNext()) {
            Map.Entry<String, String> entry = $it.next();
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        logger.debug("Http Post Para:" + paramMap);

        // http post 请求
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        CloseableHttpResponse response = httpclient.execute(httpPost);

        int status = response.getStatusLine().getStatusCode();

        logger.debug("Http Post Status:" + status);

        if (status != 200)
            return null;

        try {
            // 返回结果 格式转换
            String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            logger.debug("Http Post Result:" + result);
            return JsonUtils.readValueByType(result, type);
        } finally {
            response.close();
        }
    }

    public static Object post(String url, Object paraMap, String timestamp, String token, TypeReference<?> type)
            throws IOException {
        logger.debug("Http Post Url:" + url);

        // 序列化成 json
        String data = JsonUtils.translateToJson(paraMap);
        // 准备发送的参数
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("timestamp", timestamp);
        paramMap.put("token", token);
        paramMap.put("data", data);
        paramMap.put("appid", Constant.appid);
        paramMap.put("format", Constant.format);
        paramMap.put("version", Constant.version);


        // 生成签名
        String sign = SignUtils.getMd5Sign(paramMap, Constant.appkey);
        paramMap.put("sign", sign);

        // http post 参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
        while ($it.hasNext()) {
            Map.Entry<String, String> entry = $it.next();
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        logger.debug("Http Post Para:" + paramMap);

        // http post 请求
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        CloseableHttpResponse response = httpclient.execute(httpPost);

        int status = response.getStatusLine().getStatusCode();

        logger.debug("Http Post Status:" + status);

        if (status != 200)
            return null;

        try {
            // 返回结果 格式转换
            String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            logger.debug("Http Post Result:" + result);
            return JsonUtils.readValueByType(result, type);
        } finally {
            response.close();
        }
    }

    public static Object getToken(String url, Object paraMap, TypeReference<?> type) throws IOException {

        Map<String, String> paramMap = new HashMap<String, String>();
        //公共参数
        paramMap.put("appid", "cw_packet");
        paramMap.put("appver", "1.0.0");
        paramMap.put("token", "");
        paramMap.put("timestamp", "2017022016321231");
        //业务参数
        paramMap.put("userId", "10001");
        paramMap.put("key", "cw68d0eac528w97a62db334ea9v16g10");
        //生成签名
        String appkey = "cw34feac0528297a62db334ea9516410";
        String sign = SignUtils.getMd5Sign(paramMap, appkey);
        paramMap.put("sign", sign);
        //发送请求
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
        while ($it.hasNext()) {
            Map.Entry<String, String> entry = $it.next();
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        logger.debug("Http Post Para:" + paramMap);
        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse response = new DefaultHttpClient().execute(httppost);
        //返回结果

        Integer status = response.getStatusLine().getStatusCode();
        if (status != 200)
            return null;

        try {
            String result = EntityUtils.toString(response.getEntity());
            logger.debug("Http result:" + result);
            return JsonUtils.readValueByType(result, type);
        } finally {
            //response.close();
        }

    }
}

