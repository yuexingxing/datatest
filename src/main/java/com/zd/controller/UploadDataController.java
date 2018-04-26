package com.zd.controller;

/**
 * 文件上传控制器,作用:接收上传的Post数据,
 * 1) 写入到文件（每次post上传的数据,写一个json数据文件）
 * 2) 写入到Redis队列
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.zd.common.JsonUtils;
import com.zd.object.ActionRequest;
import com.zd.object.Header;
import com.zd.object.ResultInfo;
import com.zd.object.ScanDataListEnt;
import com.zd.service.UploadDataInterface;
import com.zd.util.CommandTools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求参数:
 * 【URL】
 * "token": "e04de2b9004ee917ded1546e6cc217a0",
 * <p/>
 * http://localhost:8080/uploaddata/scandata?appid=zd_xl&timestamp=20161207150248&appver=1.0.1&format=json&version=1.0&sign=124411e41bb6f0f087755af1eea768a8&salt=123321&token=e04de2b9004ee917ded1546e6cc217a0
 * <p/>
 * 【post】部分:
 * ... 扫描的数据结构...
 */
@Controller
@RequestMapping("/upload")
public class UploadDataController {

    // 内部错误编码,写入到日志,用于跟踪错误的
    private static final String Inner_log_Errcode = "【UploadDataController-ERR】-";

    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(UploadDataController.class);

    // 扫描上传的数据结构类型
    private static final TypeReference<ScanDataListEnt> Request_type = new TypeReference<ScanDataListEnt>() {};

    private static final TypeReference<ActionRequest> action_request_type = new TypeReference<ActionRequest>() {};

    @Autowired
    @Qualifier("uploaddataService")
    private UploadDataInterface uploadDataSvc;

    /**
     * 上传的扫描数据
     */
    @RequestMapping(value = "/scandata", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo upload_scandata( HttpServletRequest request, HttpServletResponse response ) {

        ResultInfo responseResult = new ResultInfo();
        try {

            String postData = request.getParameter("data");
            if (StringUtils.isEmpty(postData)) {
                responseResult.setSuccess(-1);
                responseResult.setCode("404");
                responseResult.setMessage("请求数据格式错误");
                responseResult.setData(null);
                return responseResult;
            }

            // 上传的数据包..
            ScanDataListEnt scandataPackage = JsonUtils.readValueByType(postData, Request_type);

            logger.info("APP请求："+request.getRequestURI());
            logger.info("APP请求："+request.getParameter("data"));

            // 数据存储到数据库
            if ("upload_scandata".equals(scandataPackage.getOperation_type())) {

                Header header = scandataPackage.getHeader();

                ActionRequest actionRequest = new ActionRequest();
                actionRequest.setProject_id(header.getProject_id());
                actionRequest.setUser_id(header.getScan_user_id());
                actionRequest.setNode_id(header.getRoute_points_id());
                actionRequest.setNode_num(Integer.parseInt(header.getNode_no()));
                actionRequest.setLink_num(Integer.parseInt(header.getLink_no()));
                actionRequest.setType(scandataPackage.getScan_type());
                actionRequest.setFlag(header.getFlag());
                actionRequest.setUser_name(header.getScan_user());

                responseResult = uploadDataSvc.upload(scandataPackage, actionRequest);
            } else {
                responseResult.setSuccess(-1);
                responseResult.setCode("404");
                responseResult.setMessage("操作类型代码operation_type错误");
                responseResult.setData(null);
                return responseResult;
            }

        } catch (Exception ex) {

            responseResult.setSuccess(-1);
            responseResult.setCode("404");
            responseResult.setMessage("上传扫描数据异常");
            responseResult.setData(null);

            // 异常信息记录到日志文件:
            logger.error(Inner_log_Errcode + "upload_scandata.Exception:" + ex.getMessage());

        }

        return responseResult;
    }

    /**
     * 确认抵达的扫描数据
     */
    @RequestMapping(value = "/arrive", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo upload_arrive( HttpServletRequest request, HttpServletResponse response ) {

        ResultInfo responseResult = new ResultInfo();
        try {

            String postData = request.getParameter("data");
            if (CommandTools.isDataEmpty(request, responseResult)) {
                return responseResult;
            }

            // 上传的数据包..
            ScanDataListEnt scandataPackage = JsonUtils.readValueByType(postData, Request_type);

            logger.info("APP请求："+request.getRequestURI());
            logger.info("APP请求："+request.getParameter("data"));

            // 数据存储到数据库
            if ("upload_scandata".equals(scandataPackage.getOperation_type())) {

                Header header = scandataPackage.getHeader();

                ActionRequest actionRequest = new ActionRequest();
                actionRequest.setUser_id(header.getScan_user_id());
                actionRequest.setNode_id(header.getRoute_points_id());
                actionRequest.setNode_num(Integer.parseInt(header.getNode_no()));
                actionRequest.setLink_num(Integer.parseInt(header.getLink_no()));
                actionRequest.setType(scandataPackage.getScan_type());
                actionRequest.setFlag(header.getFlag());

                responseResult = uploadDataSvc.upload_arrive(scandataPackage, actionRequest);
            } else {
                responseResult.setSuccess(-1);
                responseResult.setCode("201");
                responseResult.setMessage("操作类型代码operation_type错误");
                responseResult.setData(null);
                return responseResult;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            responseResult.setSuccess(-1);
            responseResult.setCode("204");
            responseResult.setMessage("上传抵达数据异常 ");
            responseResult.setData(null);

            // 异常信息记录到日志文件:
            logger.error(Inner_log_Errcode + "upload_scandata.Exception:" + ex.getMessage());

        }
        return responseResult;
    }

    /**
     * 异常件
     */
    @RequestMapping(value = "/exception", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo upload_exception( HttpServletRequest request, HttpServletResponse response ) {

        ResultInfo responseResult = new ResultInfo();
        try {

            String postData = request.getParameter("data");
            if (CommandTools.isDataEmpty(request, responseResult)) {
                return responseResult;
            }

            // 上传的数据包..
            ScanDataListEnt scandataPackage = JsonUtils.readValueByType(postData, Request_type);

            logger.info("APP请求："+request.getRequestURI());
            logger.info("APP请求："+request.getParameter("data"));

            // 数据存储到数据库
            if ("upload_scandata".equals(scandataPackage.getOperation_type())) {

                responseResult = uploadDataSvc.upload_exception(scandataPackage);
            } else {
                responseResult.setSuccess(-1);
                responseResult.setCode("201");
                responseResult.setMessage("操作类型代码operation_type错误");
                responseResult.setData(null);
                return responseResult;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            responseResult.setSuccess(-1);
            responseResult.setCode("204");
            responseResult.setMessage("上传异常件数据异常 ");
            responseResult.setData(null);

            // 异常信息记录到日志文件:
            logger.error(Inner_log_Errcode + "upload_scandata.Exception:" + ex.getMessage());

        }

        return responseResult;
    }

    /**
     * 退运
     */
    @RequestMapping(value = "/back", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo upload_back( HttpServletRequest request, HttpServletResponse response ) {

        ResultInfo responseResult = new ResultInfo();
        try {

            String postData = request.getParameter("data");
            String headerData = request.getParameter("header");
            if (CommandTools.isDataEmpty(request, responseResult)) {
                return responseResult;
            }

            // 上传的数据包..
            ScanDataListEnt scandataPackage = JsonUtils.readValueByType(postData, Request_type);

            logger.info("APP请求："+request.getRequestURI());
            logger.info("APP请求："+request.getParameter("data"));

            // 数据存储到数据库
            if ("upload_scandata".equals(scandataPackage.getOperation_type())) {
                responseResult = uploadDataSvc.upload_back(scandataPackage);
            } else {
                responseResult.setSuccess(-1);
                responseResult.setCode("201");
                responseResult.setMessage("操作类型代码operation_type错误");
                responseResult.setData(null);
                return responseResult;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            responseResult.setSuccess(-1);
            responseResult.setCode("204");
            responseResult.setMessage("上传退件数据异常 ");
            responseResult.setData(null);

            // 异常信息记录到日志文件:
            logger.error(Inner_log_Errcode + "upload_scandata.Exception:" + ex.getMessage());

        }

        return responseResult;
    }

    /**
     * 单件录入
     */
    @RequestMapping(value = "/single", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo upload_single( HttpServletRequest request, HttpServletResponse response ) {

        ResultInfo responseResult = new ResultInfo();
        try {

            String postData = request.getParameter("data");
            String headerData = request.getParameter("header");
            if (CommandTools.isDataEmpty(request, responseResult)) {
                return responseResult;
            }

            // 上传的数据包..
            ScanDataListEnt scandataPackage = JsonUtils.readValueByType(postData, Request_type);

            logger.info("APP请求："+request.getRequestURI());
            logger.info("APP请求："+request.getParameter("data"));

            // 数据存储到数据库
            if ("upload_scandata".equals(scandataPackage.getOperation_type())) {

                responseResult = uploadDataSvc.upload_single(scandataPackage);
            } else {
                responseResult.setSuccess(-1);
                responseResult.setCode("201");
                responseResult.setMessage("操作类型代码operation_type错误");
                responseResult.setData(null);
                return responseResult;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            responseResult.setSuccess(-1);
            responseResult.setCode("204");
            responseResult.setMessage("上传退件数据异常 ");
            responseResult.setData(null);

            // 异常信息记录到日志文件:
            logger.error(Inner_log_Errcode + "upload_scandata.Exception:" + ex.getMessage());

        }
        return responseResult;
    }

    /**
     * 拍照图片上传
     */
    @RequestMapping(value = "/takephoto", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo upload_takephoto( HttpServletRequest request, HttpServletResponse response ) {

        ResultInfo responseResult = new ResultInfo();
        try {

            String postData = request.getParameter("data");
            String headerData = request.getParameter("header");
            if (CommandTools.isDataEmpty(request, responseResult)) {
                return responseResult;
            }

            // 上传的数据包..
            ScanDataListEnt scandataPackage = JsonUtils.readValueByType(postData, Request_type);

            logger.info("APP请求："+request.getRequestURI());
            logger.info("APP请求："+request.getParameter("data"));

            // 数据存储到数据库
            if ("upload_scandata".equals(scandataPackage.getOperation_type())) {

                responseResult = uploadDataSvc.upload_takephoto(scandataPackage);
            } else {
                responseResult.setSuccess(-1);
                responseResult.setCode("201");
                responseResult.setMessage("操作类型代码operation_type错误");
                responseResult.setData(null);
                return responseResult;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            responseResult.setSuccess(-1);
            responseResult.setCode("204");
            responseResult.setMessage("上传退件数据异常 ");
            responseResult.setData(null);

            // 异常信息记录到日志文件:
            logger.error(Inner_log_Errcode + "upload_scandata.Exception:" + ex.getMessage());

        }
        return responseResult;
    }

    @RequestMapping(value = "/ruler/wx/commit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo commitWXInfo(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");
            ActionRequest actionRequest = JsonUtils.readValueByType(postData, action_request_type);
            actionRequest.setTime(CommandTools.getTime());

            resultInfo = uploadDataSvc.update_ruler_state(actionRequest);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "确认打尺完成异常");
        }

        return resultInfo;
    }

}
