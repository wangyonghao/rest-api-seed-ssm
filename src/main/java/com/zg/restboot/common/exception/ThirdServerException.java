package com.zg.restboot.common.exception;

/**
 * 第三方API错误 异常类
 * @author wangyonghao
 * @version 2021.01.19
 */
public class ThirdServerException extends ServerException {
    public ThirdServerException() {
        super();
    }

    public ThirdServerException(String message) {
        super(message);
    }

    public ThirdServerException(Throwable e) {
        super(e);
    }

    public ThirdServerException(String message, Throwable e) {
        super(message, e);
    }
}
