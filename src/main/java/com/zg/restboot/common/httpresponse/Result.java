package com.zg.restboot.common.httpresponse;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 返回值对象
 */
@Getter
@Setter
public class Result implements Serializable {
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
    private Result() {}

    /**
     * 返回成功
     * @return
     */
    public static Result ok() {
        Result errorResult = new Result();
        errorResult.errcode = 0;
        return errorResult;
    }

    public static Result ok(Serializable data) {
        Result errorResult = new Result();
        errorResult.errcode = 0;
        errorResult.data = data;
        return errorResult;
    }

    public static Result ok(String message, Object data) {
        Result errorResult = new Result();
        errorResult.errcode = 0;
        errorResult.data = data;
        errorResult.message = message;
        return errorResult;
    }

    public static Result error(String message) {
        Result errorResult = new Result();
        errorResult.errcode = 500;
        errorResult.message = message;
        return errorResult;
    }

    public static Result error(Integer code, String message) {
        Result errorResult = new Result();
        errorResult.errcode = code;
        errorResult.message = message;
        return errorResult;
    }
}
