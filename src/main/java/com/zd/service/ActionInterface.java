package com.zd.service;

import com.zd.object.ActionRequest;
import com.zd.object.ResultInfo;
import com.zd.object.ScanDataListEnt;
import com.zd.pojo.QueryData;

/**
 * Created by Administrator on 2017-04-05.
 */
public interface ActionInterface {

    public ResultInfo getFlag(ActionRequest request);//获取flag

    public ResultInfo getGoodsDetail(ActionRequest request);//获取商品明细

    public ResultInfo getTaskList(ActionRequest request);//任务列表

    public ResultInfo getCompanyList(String platform_id);//获取装卸公司列表

    public ResultInfo getArriveData(ActionRequest request);//获取抵达相关信息

    public ResultInfo query(QueryData data);//查询

    public ResultInfo getInfoByBarcode(ActionRequest data);//根据条码获取相关信息

    public ResultInfo getLoadNum(ActionRequest request);//获取载入数

    public ResultInfo checkBarcode(String barcode);//判断条码是否存在

    public ResultInfo singleCheckBarcode(ActionRequest request);//单件录入，判断条码是否可操作

    public ResultInfo queryGetLink(String node_id);//查询--可操作环节

    public ResultInfo queryGetTaskName(ActionRequest request);//查询--任务列表

    public ResultInfo queryGetGoodsDetail(ActionRequest request);//查询--货物明细

    public ResultInfo queryGetEcpSolution(String id);//获取异常原因，解决方案

    public ResultInfo getByBarcode(ActionRequest request);//获取货物先关信息

    public ResultInfo getByBarcode_takephoto(ActionRequest request);//获取货物先关信息,拍照模块用

    public ResultInfo backCheckBarcode(ActionRequest request);//退运--判断货物是否存在

    public ResultInfo getSumPackageInfo(ScanDataListEnt scandataPackage);//获取总箱单信息

    public ResultInfo getInfoByRFID(ActionRequest request);//获取RFID绑定的货物

    public ResultInfo getWXInfo(String node_id);//微信扫码获取打尺详情

    public ResultInfo getSumPackageInfoByBarcode(ActionRequest request);
}
