package com.zg.restboot.common.exception;

import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * 数据不存在 异常类
 * @author wangyonghao
 * @date 2021/1/15
 */
public class DataNotFoundException extends ClientException {
    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(Throwable e) {
        super(e);
    }

    public DataNotFoundException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * 状态码 404
     * @return
     */
    @Override
    public int status() {
        return HttpStatus.NOT_FOUND.value();
    }

    public static void throwWhenNull(Object obj, String throwMesssage){
        if(Objects.isNull(obj)){
            throw new DataNotFoundException(throwMesssage);
        }
    }
}
