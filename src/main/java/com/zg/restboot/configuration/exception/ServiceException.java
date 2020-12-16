package com.zg.restboot.configuration.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 服务异常
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {
    private int code = 500; // 异常码
    private String message; // 业务异常描述

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }

    public ServiceException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public ServiceException(int code,String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public ServiceException(int code, String message, Throwable e) {
        super(message, e);
        this.message = message;
        this.code = code;
    }
}
