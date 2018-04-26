package com.zd.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by zhangxiusheng on 17/3/26.
 */

public class ResultInfo {
    /**
     * 0 success,-1 fail
     */
    private int success;
    /**
     * 错误编码
     */
    private String code;
    /**
     * 消息提示
     */
    private String message;
    /**
     * 只用{/user/login}登录结果才返回有效的token,其他情况无效
     */
    private String token;
    /**
     * 数据
     */
    private Object data;

    public ResultInfo() {
    }

    public ResultInfo( int success, String code ) {
        super();
        this.success = success;
        this.code = code;
        this.token = "";
    }

    public ResultInfo( int success, String code, String message ) {
        super();
        this.success = success;
        this.code = code;
        this.message = message;
        this.token = "";
    }

    public ResultInfo( int success, String code, String message, Object data ) {
        super();
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.token = "";
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData( Object data ) {
        this.data = data;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess( int success ) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    @Override
    public String toString() {

        String str = "ResultInfo [success=" + success + ", code=" + code + ", message=" + message + ", data=" + data + "]";
        System.out.println(data);
        return str;
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception ex) {
            return "{\"success\":0,\"code\":\"4001\",\"message\":\"" + ex.getMessage() + "\",\"data\":\"\"}";
        }
    }
}
