package com.zd.pojo;

/**
 * Created by zhangxiusheng on 17/3/26.
 */

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//--- 未使用...
// responseData
public class ResponseData implements Serializable {
    private int success;

    private String code;

    private String message;

    // private List<Data> data;
    private List<Map<String, Object>> data;

    public void setSuccess( int success ) {
        this.success = success;
    }

    public int getSuccess() {
        return this.success;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData( List<Map<String, Object>> data ) {
        this.data = data;
    }

    public List<Map<String, Object>> getData() {
        return this.data;
    }
}
