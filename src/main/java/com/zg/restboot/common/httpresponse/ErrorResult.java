package com.zg.restboot.common.httpresponse;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Instant;

/**
 * 返回值对象
 */
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ErrorResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 异常码
     */
    private String error;

    /**
     * 异常信息
     */
    private String message;

    /**
     * Debug信息
     */
    private String debug;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求时间
     */
    private Instant timestamp = Instant.now();
}
