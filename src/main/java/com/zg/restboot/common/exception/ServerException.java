package com.zg.restboot.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 服务端错误 异常类，状态码5xx
 *
 * @author wangyonghao
 * @date 2021/1/14
 */
public class ServerException extends ApiException {
    public ServerException() {
        super();
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable e) {
        super(e);
    }

    public ServerException(String message, Throwable e) {
        super(message, e);
    }
}
