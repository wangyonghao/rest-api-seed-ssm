package com.zg.restboot.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 客户端错误 异常类, 状态码4xx
 * @author wangyonghao
 * @date 2021/1/22
 */
public class ClientException extends ApiException {
    public ClientException() {
        super();
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(Throwable e) {
        super(e);
    }

    public ClientException(String message, Throwable e) {
        super(message, e);
    }
}
