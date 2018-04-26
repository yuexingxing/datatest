package com.zd.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-06-26.
 */
public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, String message) {

        StringBuffer sb = new StringBuffer();
        try {
            URL urls = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) urls.openConnection();
            uc.setRequestMethod("POST");
            uc.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            uc.setRequestProperty("charset", "UTF-8");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setReadTimeout(10000);
            uc.setConnectTimeout(10000);
            OutputStream os = uc.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.write(message.getBytes("utf-8"));
            dos.flush();
            os.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc
                    .getInputStream(), "utf-8"));
            String readLine = "";
            while ((readLine = in.readLine()) != null) {
                sb.append(readLine);
            }
            in.close();
        } catch (Exception e) {
            e.getMessage();
        }

        return sb.toString();
    }
}