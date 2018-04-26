package com.zd.pojo;

/**
 * Created by zhangxiusheng on 17/3/26.
 */

//--- 未使用...
import java.io.Serializable;

public class ResultModel<T> implements Serializable{

    private static final long serialVersionUID = -4493182332936313944L;
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
     * 数据
     */
    private T data;


    public ResultModel() {
    }
    public ResultModel(int success,String code, String message) {
        super();
        this.success = success;
        this.code = code;
        this.message = message;
    }
    public ResultModel( int success,String code, String message, T data) {
        super();
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResultModel [success=" + success + ", code=" + code + ", message=" + message + ", data=" + data + "]";
    }

}