package com.zd.controller;

/**
 * Created by zhangxiusheng on 17/3/20.
 */

import com.zd.common.FileUtils;
import com.zd.pojo.flinfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.List;
import java.util.Map;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import org.springframework.web.multipart.commons.*;
import org.springframework.web.multipart.*;

import java.util.Iterator;

/**
 * 请求路径::
 * 【1】查询文件列表:
 * http://localhost:8080/testdata/getlist/json
 * 【2】上传文件:
 * http://localhost:8080/testdata/upload
 * 【3】读取指定文件:
 * http://localhost:8080/testdata/readfile/login.json
 * 【4】删除指定文件:
 * http://localhost:8080/testdata/delfile/login.json
 * http://localhost:8080/testdata/delfile/list副本.json
 */

@Controller
@RequestMapping("/testdata")
public class TextDataController {
    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(TextDataController.class);

    //映射一个action
    @RequestMapping("/getdata")
    public String getdata() {
        //输出日志文件
        logger.info("getdata");
        //返回一个index.jsp这个视图
        return "index";
    }

    // 调用URL:http://localhost:8080/testdata/getlist/png
    //【注意】spring4.3以上的版本,不支持 @ResponseBody的bean配置:MappingJackson2HttpMessageConverter 这个函数--我日尼玛
    // 写一个post上传文件数据的函数,按照指定的文件名称,存储内容到直接下载的目录下;
    // 获取TestData目录下面所有指定后缀名的文件列表;
    @RequestMapping(value = "/getlist/{suffix}", method = RequestMethod.GET)
    @ResponseBody
    public List<flinfo> getlist( HttpServletRequest request, @PathVariable("suffix") String suffix ) {
        //String filePath = Constant.Get_workPath();
        String uploadFilePath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
        if (StringUtils.isEmpty(uploadFilePath)) {
            uploadFilePath = "/TestData";
        }
        List<flinfo> fileList = com.zd.common.FileUtils.listFileBySuffix(uploadFilePath, suffix);
        return fileList;
    }

    /**
     * 问题参考:
     * {Spring MVC @PathVariable 最后一个点（dot）以后的字符串（或说扩展名）丢失}
     * http://blog.csdn.net/will_awoke/article/details/38110191
     * <p/>
     * 参数:{flname:.*} 是支持中文,.点号;
     * <p/>
     * {flname:[a-zA-Z0-9\\.]+} 这个正则是支持完整文件名称（带后缀的）
     */
    @RequestMapping(value = "/readfile/{flname:[a-zA-Z0-9\\\\.]+}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String readfile( HttpServletRequest request, @PathVariable("flname") String flname ) {
        String uploadFilePath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
        if (StringUtils.isEmpty(uploadFilePath)) {
            uploadFilePath = "/TestData";
        }

        Path fl_path = Paths.get(uploadFilePath, flname);
        String svr_flname = fl_path.toString();
        File fl = new File(svr_flname);
        if (!fl.exists()) {
            return "文件不存在!";
        }
        String FileCommon = FileUtils.readFile(svr_flname);
        return FileCommon;
    }

    /**
     * 删除指定文件
     * 参数 {flname:.*} 是支持中文,.点号;
     */
    @RequestMapping(value = "/delfile/{flname:.*}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String delfile( HttpServletRequest request, @PathVariable("flname") String flname ) {
        String uploadFilePath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
        if (StringUtils.isEmpty(uploadFilePath)) {
            uploadFilePath = "/TestData";
        }

        Path fl_path = Paths.get(uploadFilePath, flname);
        String svr_flname = fl_path.toString();
        File fl = new File(svr_flname);
        if (fl.exists()) {
            fl.delete();
            return "删除成功!";
        } else {
            return "文件不存在了!";
        }
    }

    /**
     * 从controller开始
     * 控制器中添加，用 ModelAndView.addObject 方式传参数值，给jsp网页显示内容
     * URL:: http://localhost:8080/testdata/upload
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView add( Map<String, Object> map ) {
        return new ModelAndView("uploadfile");//跳转到addUser页面
    }

    /*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping("springUpload")
    public ModelAndView springUpload( HttpServletRequest request ) throws IllegalStateException, IOException {
        ModelAndView mav = new ModelAndView("success");
        try {
            String uploadFilePath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
            if (StringUtils.isEmpty(uploadFilePath)) {
                uploadFilePath = "/TestData";
            }

            boolean dirFlag = FileUtils.createDir(uploadFilePath);
            if (!dirFlag) {

            }

            long startTime = System.currentTimeMillis();
            //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            //检查form中是否有enctype="multipart/form-data"
            if (multipartResolver.isMultipart(request)) {

                //将request变成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                //获取multiRequest 中所有的文件名
                Iterator iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    //一次遍历所有文件
                    MultipartFile file = multiRequest.getFile(iter.next().toString());
                    if (file != null) {
                        // 于操作系统无关的路径拼接方式:
                        Path filePath = Paths.get(uploadFilePath, file.getOriginalFilename());
                        String path = filePath.toString();
                        //上传
                        file.transferTo(new File(path));
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("springUpload上传文件花费的时间：" + String.valueOf(endTime - startTime) + "ms");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        // 返回 ModelAndView
        return mav;

    }

}
