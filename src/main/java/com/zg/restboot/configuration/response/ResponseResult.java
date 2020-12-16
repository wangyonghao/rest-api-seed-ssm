package com.zg.restboot.configuration.response;

import java.io.Serializable;

/**
 * 返回值对象
 */
public class ResponseResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 业务编码
     */
    private Integer errcode = 0;

    /**
     * 业务信息
     */
    private String message = "success";

    /**
     * 业务内容
     */
    private Object data;
    private ResponseResult() {}

    /**
     * 返回成功
     * @return
     */
    public static ResponseResult ok() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.errcode = 0;
        return responseResult;
    }

    public static  ResponseResult ok(Serializable data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.errcode = 0;
        responseResult.data = data;
        return responseResult;
    }

    public static ResponseResult ok(String message, Object data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.errcode = 0;
        responseResult.data = data;
        responseResult.message = message;
        return responseResult;
    }

    public static ResponseResult error(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.errcode = 500;
        responseResult.message = message;
        return responseResult;
    }

    public static ResponseResult error(Integer code, String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.errcode = code;
        responseResult.message = message;
        return responseResult;
    }
}
