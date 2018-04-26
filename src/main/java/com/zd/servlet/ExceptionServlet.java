package com.zd.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zd.common.JsonUtils;
import com.zd.controller.UploadDataController;
import com.zd.filter.EncodeFilter;
import com.zd.object.ResultInfo;
import com.zd.object.ScanDataListEnt;
import com.zd.service.UploadDataInterface;
import com.zd.util.Base64Coder;
import com.zd.util.CnfgUtil;
import com.zd.util.CommandTools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.*;
import java.util.Properties;

/**
 * 异常件、退件、拍照
 * Created by Administrator on 2017-05-15.
 */
@WebServlet(name = "ExceptionServlet")
public class ExceptionServlet extends HttpServlet {

    private String base64;
    ResultInfo resultInfo = new ResultInfo();

    // 扫描上传的数据结构类型
    private static final TypeReference<ScanDataListEnt> Request_type = new TypeReference<ScanDataListEnt>() {};
    private static final Logger logger = LoggerFactory.getLogger(ExceptionServlet.class);   //添加一个日志器

    @Autowired
    @Qualifier("uploaddataService")
    private UploadDataInterface uploadDataSvc;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().append(resultInfo.getMessage());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String postData = request.getParameter("data");

        String headerData = request.getParameter("header");
        if (StringUtils.isEmpty(postData)) {
            resultInfo.setSuccess(-1);
            resultInfo.setCode("404");
            resultInfo.setMessage("请求数据格式错误");
            resultInfo.setData(null);
        }

        // 上传的数据包..
        ScanDataListEnt scandataPackage = JsonUtils.readValueByType(postData, Request_type);

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        // 数据存储到数据库
        if ("upload_scandata".equals(scandataPackage.getOperation_type())) {
            resultInfo = uploadDataSvc.upload(scandataPackage, null);
        } else {
            resultInfo.setSuccess(-1);
            resultInfo.setCode("404");
            resultInfo.setMessage("操作类型代码operation_type错误");
            resultInfo.setData(null);
        }
//        boolean flag = CommandTools.base64ToFile(msg);
//        response.setCharacterEncoding("utf-8");
//
//        if(flag){
//            response.getWriter().append("图片上传成功" );
//        }else{
//            response.getWriter().append("图片上传失败");
//        }

    }


}
