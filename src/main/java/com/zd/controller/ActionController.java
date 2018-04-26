package com.zd.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zd.common.JsonUtils;
import com.zd.object.ActionRequest;
import com.zd.object.ResultInfo;
import com.zd.object.ScanDataListEnt;
import com.zd.pojo.QueryData;
import com.zd.service.ActionInterface;
import com.zd.util.CommandTools;
import net.sf.json.JSONObject;
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
 * 公共模块
 * Created by Administrator on 2017-04-05.
 */
@Controller
@RequestMapping("/action")
public class ActionController {

    private static final Logger logger = LoggerFactory.getLogger(ActionController.class);
    private static final TypeReference<ScanDataListEnt> Request_type = new TypeReference<ScanDataListEnt>() {};

    @Autowired
    @Qualifier("actionService")
    private ActionInterface actionService;

    @RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo query(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            QueryData lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Query_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            // 陆运查询
            resultInfo = actionService.query(lrEnt);

        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取动作查询异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/gettasklist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getTaskList(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            resultInfo = actionService.getTaskList(lrEnt);

        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取任务列表异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/getgoodsdetail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getGoodsDetail(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            resultInfo = actionService.getGoodsDetail(lrEnt);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取商品明细异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/getflag", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getFlag(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            resultInfo = actionService.getFlag(lrEnt);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "getflag异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/arrive", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getArriveData(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "action")){
                return resultInfo;
            }

            resultInfo = actionService.getArriveData(lrEnt);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取到达信息异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/getcompanylist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getCompanyList(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest actionRequest = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 获取任务列表
            resultInfo = actionService.getCompanyList(actionRequest.getPlatform_id());
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取装卸公司列表异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/exception/query", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo exception_query_by_barcode(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 获取任务列表
            resultInfo = actionService.getInfoByBarcode(lrEnt);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取装卸公司列表异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/getloadnum", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getLoadNumber(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.getLoadNum(lrEnt);
        }catch(Exception e){
            e.printStackTrace();;
            CommandTools.analyzeException(resultInfo, "获取载入数异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/back/checkbarcode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo checkBarcoder(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.backCheckBarcode(lrEnt);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "退运获取条码是否存在异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/single/checkbarcode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo singleCheckBarcoder(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.singleCheckBarcode(lrEnt);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "单件录入，条码查询异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/query/getlink", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo queryGetLink(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.queryGetLink(lrEnt.getNode_id());
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "查询可操作环节异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/query/gettaskname", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo queryGetTaskName(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.queryGetTaskName(lrEnt);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "查询任务列表异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/query/getgoodsdetail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo queryGetData(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.queryGetGoodsDetail(lrEnt);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "查询货物明细异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/query/getecpsolution", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo queryGetEcpSolution(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            QueryData queryData = JsonUtils.readValueByType(postData, CommandTools.Action_Query_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.queryGetEcpSolution(queryData.getGoods_id());
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "查询异常解决方案异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/exception/checkbarcode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo exceptionCheckBarcoder(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.getByBarcode(lrEnt);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取条码是否存在异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/takephoto/checkbarcode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo takephotoCheckBarcoder(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest lrEnt = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.getByBarcode_takephoto(lrEnt);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取条码是否存在异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/pack/getsumpackageinfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getSumPackageInfo(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            ScanDataListEnt scandataPackage = JsonUtils.readValueByType(postData, Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.getSumPackageInfo(scandataPackage);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取总箱单信息异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/pack/getsumpackageinfobybarcode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getSumPackageInfoByBarcode(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            ScanDataListEnt scandataPackage = JsonUtils.readValueByType(postData, Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            ActionRequest actionRequest = new ActionRequest();
            if(scandataPackage.getDetail().size() >= 0){
                actionRequest.setPack_barcode(scandataPackage.getDetail().get(0).getPackBarcode());
            }

            resultInfo = actionService.getSumPackageInfoByBarcode(actionRequest);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取总箱单信息异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/rfid/getgoodsinfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getRFIDInfo(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String postData = request.getParameter("data");

            if(CommandTools.isDataEmpty(request, resultInfo)){
                return resultInfo;
            }

            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            // 解析json对象，获取参数
            ActionRequest actionRequest = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);
            if(!CommandTools.isParameterValid(request, resultInfo, "loading")){
                return resultInfo;
            }

            resultInfo = actionService.getInfoByRFID(actionRequest);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取RFID信息异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/ruler/wxurl", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getRulerInfo(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMessage("获取成功");

        String postData = request.getParameter("data");

        try {

            ActionRequest actionRequest = JsonUtils.readValueByType(postData, CommandTools.Action_Request_type);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", "http://h5.netcargo.cn/search/project/detail/" + actionRequest.getNode_id());
            resultInfo.setData(jsonObject);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取微信扫码URL异常");
        }

        return resultInfo;
    }

    @RequestMapping(value = "/ruler/wx/getdata", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultInfo getWXInfo(HttpServletRequest request, HttpServletResponse response ) {
        ResultInfo resultInfo = new ResultInfo();

        logger.info("APP请求："+request.getRequestURI());
        logger.info("APP请求："+request.getParameter("data"));

        try {

            // 获取请求正文json:
            String node_id = request.getParameter("node_id");

            resultInfo = actionService.getWXInfo(node_id);
        }catch(Exception e){
            CommandTools.analyzeException(resultInfo, "获取微信扫码信息异常");
        }

        return resultInfo;
    }

}
