package com.zg.restboot.common.exception;

/**
 * API错误 异常根类
 * @author wangyonghao
 * @date 2021/1/22
 */
public class ApiException extends RuntimeException {
    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable e) {
        super(e);
    }

    public ApiException(String message, Throwable e) {
        super(message, e);
    }
}
