package com.zg.restboot.configuration.http;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;
import sun.security.provider.certpath.OCSPResponse;

import java.io.Serializable;

/**
 * 返回值对象
 */
public class ResponseResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 业务编码
     */
    private Integer code = 0;

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
        responseResult.code = 0;
        return responseResult;
    }

    public static  ResponseResult ok(Serializable data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.code = 0;
        responseResult.data = data;
        return responseResult;
    }

    public static ResponseResult ok(String message, Object data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.code = 0;
        responseResult.data = data;
        responseResult.message = message;
        return responseResult;
    }

    public static ResponseResult error(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.code = 500;
        responseResult.message = message;
        return responseResult;
    }

    public static ResponseResult error(Integer code, String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.code = code;
        responseResult.message = message;
        return responseResult;
    }
}
