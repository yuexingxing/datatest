package com.zd.object;

import com.zd.pojo.ScanData;

import java.util.List;

/**
 * PDA上传的数据结构对应;
 */
public class ScanDataListEnt {
    private String operation_type;

    private String scan_type;

    public String scan_type_link;

    private Header header;

    private List<ScanData> detail;

//    private List<ScanData> detail;

    public void setOperation_type( String operation_type ) {
        this.operation_type = operation_type;
    }

    public String getOperation_type() {
        return this.operation_type;
    }

    public void setScan_type( String scan_type ) {
        this.scan_type = scan_type;
    }

    public String getScan_type() {
        return this.scan_type;
    }

    public void setScan_type_link( String scan_type_link ) {
        this.scan_type_link = scan_type_link;
    }

    public String getScan_type_link() {
        return this.scan_type_link;
    }

    public void setHeader( Header header ) {
        this.header = header;
    }

    public Header getHeader() {
        return this.header;
    }

    public void setDetail( List<ScanData> detailOLD ) {
        this.detail = detailOLD;
    }

    public List<ScanData> getDetail() {
        return this.detail;
    }

//    public void setScanDataList( List<ScanData> scanDataList ) {
//        this.detail = scanDataList;
//    }
//
//    public List<ScanData> getScanDataList() {
//        return this.detail;
//    }
}
