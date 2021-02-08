package com.zg.restboot.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;

/**
 * 返回值对象
 */
@Accessors(chain = true)
@Getter
@Setter
public class ErrorResult implements Serializable {
    /**
     * Http 状态码
     */
    private int status;

    /**
     * 错误类型
     */
    private String error;

    /**
     * 异常信息
     */
    private String message;

    /**
     * Debug信息
     */
    private String details;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求时间戳
     */
    private String timestamp = Instant.now().atZone(ZoneId.systemDefault()).toString();

    public static ErrorResult build(){
        return new ErrorResult();
    }

    public static ErrorResult wrapException(Exception ex){
        ErrorResult result = new ErrorResult();
        result.setMessage(ex.getLocalizedMessage());
        result.setDetails(ex.toString());
        return result;
    }

    public ErrorResult setHttpStatus(HttpStatus httpStatus){
        this.setStatus(httpStatus.value());
        this.setError(httpStatus.name());
        return this;
    }
}
