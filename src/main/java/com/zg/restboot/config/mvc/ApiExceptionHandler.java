package com.zg.restboot.config.mvc;

import com.zg.restboot.common.exception.ClientException;
import com.zg.restboot.common.exception.ErrorResult;
import com.zg.restboot.common.exception.ServerException;
import com.zg.restboot.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 统一异常处理器，异常时统一打印请求信息，并包装为统一结构体
 *
 * @author wangyonghao
 */

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @Resource
    MessageSource messageSource;
    /**
     * 状态码 - 400
     * 1. 参数绑定错误
     * 2. 格式校验错误
     * 3. 自定义抛出的ClientException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ClientException.class, MethodArgumentTypeMismatchException.class})
    public ErrorResult handlerClientException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug(RequestUtil.getRequestString(request),ex);
        return ErrorResult.wrapException(ex)
                .setPath(RequestUtil.getRequestURL(request))
                .setDetails(ex.getLocalizedMessage())
                .setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request, HttpServletResponse response) {
        return new ErrorResult()
                .setPath(RequestUtil.getRequestURL(request))
                .setMessage("请求数据格式不正确")
                .setDetails(ex.getLocalizedMessage())
                .setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    /**
     * 参数绑定错误
     * 参数绑定致命错误时，抛出 BindException 异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResult handleBindException(BindException ex, HttpServletRequest request) {
        List<String> messageList = ex.getBindingResult().getAllErrors()
                .stream().map(objectError -> objectError.getCode() + objectError.getDefaultMessage()).collect(Collectors.toList());
        return new ErrorResult()
                .setPath(RequestUtil.getRequestURL(request))
                .setMessage(StringUtils.join(messageList,","))
                .setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    /**
     * 格式验证错误 </br>
     * 数据校验失败时，抛出 MethodArgumentNotValidException 异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleBindException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> messageList = ex.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> messageSource.getMessage(fieldError.getField(),null, request.getLocale()) + fieldError.getDefaultMessage()).collect(Collectors.toList());
        return new ErrorResult()
                .setPath(RequestUtil.getRequestURL(request))
                .setMessage(StringUtils.join(messageList,","))
                .setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    /**
     * 请求不存在 - 404
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorResult handlerException(NoHandlerFoundException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error(RequestUtil.getRequestString(request) + ", error: " + ex.getLocalizedMessage(),ex.getCause());
        return new ErrorResult()
                .setPath(RequestUtil.getRequestURL(request))
                .setMessage(ex.getLocalizedMessage())
                .setDetails(ex.toString())
                .setHttpStatus(HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerException.class)
    public ErrorResult handlerServerException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.debug(RequestUtil.getRequestString(request), ex);
        return new ErrorResult()
                .setPath(RequestUtil.getRequestURL(request))
                .setMessage(ex.getLocalizedMessage())
                .setDetails(ex.toString())
                .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /**
     * 服务端异常 - 500
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult handlerException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.error(RequestUtil.getRequestString(request),ex);
        return new ErrorResult()
                .setPath(RequestUtil.getRequestURL(request))
                .setMessage("未知异常")
                .setDetails(ex.toString())
                .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
