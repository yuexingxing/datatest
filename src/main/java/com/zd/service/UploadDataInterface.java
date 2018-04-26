package com.zd.service;

import com.zd.object.ActionRequest;
import com.zd.object.ResultInfo;
import com.zd.object.ScanDataListEnt;

/**
 * 数据上传服务
 */
public interface UploadDataInterface {

    public ResultInfo upload(ScanDataListEnt scandataPackage, ActionRequest actionRequest);

    public ResultInfo upload_arrive(ScanDataListEnt scandataPackage, ActionRequest actionRequest);

    public ResultInfo upload_exception(ScanDataListEnt scandataPackage);

    public ResultInfo upload_back(ScanDataListEnt scandataPackage);

    public ResultInfo upload_single(ScanDataListEnt scandataPackage);

    public ResultInfo upload_takephoto(ScanDataListEnt scandataPackage);

    public ResultInfo update_ruler_state(ActionRequest actionRequest);
}

