package com.zg.restboot.common.httpresponse;

import java.io.Serializable;

/**
 * 返回值对象
 */
public class ErrorResult implements Serializable {
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
    private ErrorResult() {}

    /**
     * 返回成功
     * @return
     */
    public static ErrorResult ok() {
        ErrorResult errorResult = new ErrorResult();
        errorResult.errcode = 0;
        return errorResult;
    }

    public static ErrorResult ok(Serializable data) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.errcode = 0;
        errorResult.data = data;
        return errorResult;
    }

    public static ErrorResult ok(String message, Object data) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.errcode = 0;
        errorResult.data = data;
        errorResult.message = message;
        return errorResult;
    }

    public static ErrorResult error(String message) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.errcode = 500;
        errorResult.message = message;
        return errorResult;
    }

    public static ErrorResult error(Integer code, String message) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.errcode = code;
        errorResult.message = message;
        return errorResult;
    }
}
