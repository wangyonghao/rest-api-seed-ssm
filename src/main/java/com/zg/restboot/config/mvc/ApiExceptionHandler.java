package com.zg.restboot.config.mvc;

import com.zg.restboot.common.exception.ApiException;
import com.zg.restboot.common.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 统一异常处理器，异常时统一打印请求信息，并包装为统一结构体
 * @author wangyonghao
 */

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * 处理客户端错误-400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class,IllegalArgumentException.class})
    public ErrorResult handlerClientException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error(this.logRequest(request) + " , error: " + ex.getLocalizedMessage());
        return ErrorResult.wrap(request, ex);
    }

    /**
     * 处理客户端错误-404
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorResult handlerException(NoHandlerFoundException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error(this.logRequest(request) + " , error: " + ex.getLocalizedMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return ErrorResult.wrap(request, ex);
    }

    /**
     * 处理API错误
     * ClientException 4xx
     * ServerException 5xx
     */
    @ExceptionHandler(ApiException.class)
    public ErrorResult handlerApiException(ApiException ex, HttpServletRequest request, HttpServletResponse response) {
        if(Objects.isNull(ex.getCause())){
            log.error(this.logRequest(request) + " , error: " + ex.getLocalizedMessage());
        }else{
            log.error(this.logRequest(request) + " , error: " + ex.getLocalizedMessage(), ex);
        }
        response.setStatus(ex.status());
        return ErrorResult.wrap(request,ex);
    }

    /**
     * 处理第三方API错误-5xx
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult handlerThirdException(Exception ex, HttpServletRequest request) {
        log.error(this.logRequest(request),ex);
        return ErrorResult.wrap(request, ex);
    }

    /**
     * 异常处理兜底
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorResult handlerException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.error(this.logRequest(request),ex);
        return ErrorResult.wrap(request, ex);
    }

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.valueOf("application/*+json"),
            MediaType.MULTIPART_FORM_DATA
    );

    private String logRequest(HttpServletRequest request){
        StringBuilder sb = new StringBuilder(1024);
        // uri
        sb.append("path: ").append(request.getMethod()).append(" ").append(request.getRequestURI());
        // params
        sb.append(logParams(request));
        // body
        String bodyLog = logRequestBody(request).toString();
        if(StringUtils.isNotEmpty(bodyLog)){
            sb.append("   body: ").append(bodyLog);
        }
        return sb.toString();
    }

    private String logParams(HttpServletRequest request){
        String paramString = StringUtils.EMPTY;
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (!parameterMap.isEmpty()) {
            List<String> pairs = new ArrayList<>();
            parameterMap.forEach((name, values) -> {
                for (String value : values) {
                    pairs.add(name + "=" + StringUtils.trimToEmpty(value));
                }
            });
            paramString = StringUtils.join(pairs,"&");
        }
        if (StringUtils.equals(request.getContentType(), MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            try {
                paramString = URLDecoder.decode(paramString, request.getCharacterEncoding());
            } catch (UnsupportedEncodingException ignored) {
            }
        }
        return StringUtils.isEmpty(paramString) ? "" : ("?"+paramString);
    }

    private StringBuilder logRequestBody(HttpServletRequest rawRequest) {
        StringBuilder sb = new StringBuilder(1024);
        if(!(rawRequest instanceof ContentCachingRequestWrapper)){
            return sb;
        }
        ContentCachingRequestWrapper request = (ContentCachingRequestWrapper)rawRequest;
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0 && (!MediaType.APPLICATION_FORM_URLENCODED.includes(MediaType.valueOf(request.getContentType())))) {
            logContent(content, request.getContentType(), request.getCharacterEncoding(), "", sb);
        }
        return sb;
    }

    private void logContent(byte[] content, String contentType, String contentEncoding, String prefix, StringBuilder sb) {
        sb.append(prefix);
        MediaType mediaType = MediaType.valueOf(contentType);
        boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
        if (visible) {
            try {
                String contentString = new String(content, contentEncoding);
                sb.append(contentString).append(LINE_SEPARATOR);
            } catch (UnsupportedEncodingException e) {
                sb.append("[").append(content.length).append(" bytes content]").append(LINE_SEPARATOR);
            }
        } else {
            sb.append("[").append(content.length).append(" bytes content]").append(LINE_SEPARATOR);
        }
    }
}
