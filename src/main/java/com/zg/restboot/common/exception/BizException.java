package com.zg.restboot.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 业务异常基类，对应http状态码为501
 * 当用户行为不符合业务规则时抛出，例如
 */
@Getter
@Setter
public class BizException extends RuntimeException {
    private String message; // 业务异常描述

    public BizException(String message) {
        super(message);
        this.message = message;
    }
}
