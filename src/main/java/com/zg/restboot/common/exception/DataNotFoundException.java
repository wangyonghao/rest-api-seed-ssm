package com.zg.restboot.common.exception;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
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

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T reference, String throwMesssage){
        if(Objects.isNull(reference)){
            throw new DataNotFoundException(throwMesssage);
        }
        return reference;
    }
}
