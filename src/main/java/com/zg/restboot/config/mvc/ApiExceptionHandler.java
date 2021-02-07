package com.zg.restboot.config.mvc;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.zg.restboot.common.exception.ClientException;
import com.zg.restboot.common.exception.ErrorResult;
import com.zg.restboot.common.exception.ServerException;
import com.zg.restboot.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 统一异常处理器，异常时统一打印请求信息，并包装为统一结构体
 *
 * @author wangyonghao
 */

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * 状态码 - 400
     * 1. 参数绑定错误
     * 2. 格式校验错误
     * 3. 自定义抛出的ClientException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ClientException.class, MethodArgumentTypeMismatchException.class, BindException.class})
    public ErrorResult handlerClientException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if(ex instanceof ClientException){
            log.debug(RequestUtil.getRequestString(request)+", error:"+ex.getLocalizedMessage());
        }else{
            log.debug(RequestUtil.getRequestString(request).toString(),ex);
        }

        return ErrorResult.wrapException(ex)
                .setPath(RequestUtil.getRequestURL(request).toString())
                .setStatus(HttpStatus.BAD_REQUEST.value())
                .setError(HttpStatus.BAD_REQUEST.name());
    }

    /**
     * 请求不存在 - 404
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorResult handlerException(NoHandlerFoundException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error(RequestUtil.getRequestString(request) + ", error: " + ex.getLocalizedMessage(),ex.getCause());
        return ErrorResult.wrapException(ex)
                .setPath(RequestUtil.getRequestURL(request).toString())
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setError(HttpStatus.NOT_FOUND.name());
    }

    /**
     * 服务端异常 - 500
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, ServerException.class})
    public ErrorResult handlerException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if(ex instanceof ServerException){
            // 只打印 RootCause 信息
            log.debug(RequestUtil.getRequestString(request)+", error:"+ex.getLocalizedMessage(),ex.getCause());
        }else{
            log.error(RequestUtil.getRequestString(request).toString(),ex);
        }
        return ErrorResult.wrapException(ex)
                .setPath(RequestUtil.getRequestURL(request).toString())
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setError(HttpStatus.INTERNAL_SERVER_ERROR.name());
    }
}
