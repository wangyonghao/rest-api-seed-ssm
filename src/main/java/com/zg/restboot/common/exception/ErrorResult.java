package com.zg.restboot.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;

/**
 * 返回值对象
 */

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
    @JsonIgnore
    private String debug;

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

    public static ErrorResult wrap(HttpServletRequest request, Exception ex){
        ErrorResult result = new ErrorResult();
        result.setPath(request.getRequestURI());
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setError(HttpStatus.INTERNAL_SERVER_ERROR.name());
        result.setMessage(ex.getLocalizedMessage());
        result.setDebug(ex.toString());
        return result;
    }
}
