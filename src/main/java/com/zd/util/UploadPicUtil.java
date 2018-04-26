package com.zd.util;

import com.zd.dao.PictureDao;
import com.zd.object.Header;
import com.zd.object.ResultInfo;
import com.zd.object.picInfo;
import com.zd.pojo.AttachmentInfo;
import com.zd.pojo.ScanData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * 图片上传类
 * Created by Administrator on 2017-05-26.
 */
public class UploadPicUtil {

    private static final Logger logger = LoggerFactory.getLogger(UploadPicUtil.class);

    /**
     * 上传到远程服务器
     * @param base64
     * @param oldFileName
     * @param newFileName
     */
    public static void uploadToServer(String base64, String oldFileName, String newFileName){

        try{

            System.out.println("base64.length = " + base64.length());

            //创建url地址
            URL url = new URL(Constant.URL_UPLOAD_PICTURE);

            //打开连接
            URLConnection conn = url.openConnection();

            //转换成HttpURL
            HttpURLConnection httpConn = (HttpURLConnection) conn;

            //打开输入输出的开关
            httpConn.setDoInput(true);

            httpConn.setDoOutput(true);

            //设置请求方式
            httpConn.setRequestMethod("POST");

            //设置请求的头信息
            httpConn.setRequestProperty("Content-type", "text/xml;charset=UTF-8");

            //拼接请求消息
            StringBuffer soapRequestData = new StringBuffer();
            soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            soapRequestData.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
            soapRequestData.append("<soap:Body>");
            soapRequestData.append("    <TransImageString xmlns=\"http://tempuri.org/\">");
            soapRequestData.append("        <imageString>" +  base64  + "</imageString>");
            soapRequestData.append("        <oldfilename>" + oldFileName  + "</oldfilename>");
            soapRequestData.append("        <newfilename>" + newFileName + "</newfilename>");
            soapRequestData.append("    </TransImageString>");
            soapRequestData.append("</soap:Body>");
            soapRequestData.append("</soap:Envelope>");

            System.out.println(oldFileName);

            //获得输出流
            OutputStream out = httpConn.getOutputStream();

            //发送数据
            out.write(soapRequestData.toString().getBytes());

            //判断请求成功
            if(httpConn.getResponseCode() == 200){

                //获得输入流
                InputStream in = httpConn.getInputStream();

                //使用输入流的缓冲区
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuffer sb = new StringBuffer();

                String line = null;

                //读取输入流
                while((line = reader.readLine()) != null){

                    sb.append(line);
                }

                String str = new String(sb.toString().getBytes(), "utf-8");

                System.out.println(str);
                logger.info(str);
                //	        //创建sax的读取器
                //
//					        SAXReader saxReader = new SAXReader();
                //
                //	        //创建文档对象
                //
                //	        Document doc = saxReader.read(new StringReader(sb.toString()));
                //
                //	        //获得请求响应return元素
                //
                //	        List<Element> eles = doc.selectNodes("//return");
                //
                //	        for(Element ele : eles){
                //
                //	           System.out.println(ele.getText());
                //
                base64 = null;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 图片保存
     * @param scanData
     */
    public static ResultInfo savePicture(ScanData scanData, Header header, SqlMapper sqlMapper, ResultInfo resultInfo){

        PictureDao pictureDao = new PictureDao();

        List<picInfo> MarkPic = scanData.getMarkPic();//唛头图片

        AttachmentInfo attachmentInfo = new AttachmentInfo();
        attachmentInfo.setCreated_userid(header.getScan_user_id());
        attachmentInfo.setCreated_user(header.getScan_user());
        attachmentInfo.setCreated_time(scanData.getScanTime());

        if(MarkPic != null && MarkPic.size() > 0) {

            for (int i = 0; i < MarkPic.size(); i++) {

                attachmentInfo.setSort_no(i + "");//图片显示顺序

                //这个名字会涉及到带/的问题，服务器会把这个认为路径/文件夹而报错，提示找不到路径
                String webFileName = CommandTools.getPicFolderName(1) + scanData.getScan_id() + "_" + scanData.getPackBarcode() + "_" + i + ".jpg";
                String serverFileName = CommandTools.getPicFolderName(2) + scanData.getScan_id() + "_" + scanData.getPackBarcode() + "_" + i + ".jpg";

                attachmentInfo.setAttachment_url(webFileName);
                attachmentInfo.setAttachment_path(serverFileName);

                resultInfo = pictureDao.save(scanData, attachmentInfo, sqlMapper);
                uploadToServer(MarkPic.get(i).getBase64(), serverFileName, serverFileName);
            }
        }

        return  resultInfo;
    }
}
