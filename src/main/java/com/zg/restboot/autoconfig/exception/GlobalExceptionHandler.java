package com.zg.restboot.autoconfig.exception;

import com.google.common.base.Throwables;
import com.zg.restboot.common.exception.BizException;
import com.zg.restboot.common.httpresponse.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 异常处理器
 * @author wyh
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理业务异常
     * @param ex 业务异常
     * @param request 请求信息
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult handleServiceException(BizException ex, HttpServletRequest request) {
        log.debug(this.logRequest(request),ex);
        return ErrorResult.builder()
                .path(request.getMethod()+" "+request.getRequestURI())
                .timestamp(Instant.now())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message(ex.getMessage())
                .debug(ex.toString())
                .build();
    }

    /**
     * 处理未知异常
     * @param ex 业务异常
     * @param request 请求信息
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult handleDefaultException(Exception ex, HttpServletRequest request) {
        log.error(this.logRequest(request),ex);
        return ErrorResult.builder()
                .timestamp(Instant.now())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .path(request.getMethod()+" "+request.getRequestURI())
                .message(ex.getMessage())
                .debug(ex.toString())
                .build();
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
        sb.append(LINE_SEPARATOR).append("request: ").append(request.getMethod()).append(" ").append(request.getRequestURI());
        // params
        sb.append(logParams(request));
        // body
        String bodyLog = logRequestBody(request).toString();
        if(!StringUtils.isEmpty(bodyLog)){
            sb.append(LINE_SEPARATOR).append("   body: ").append(bodyLog);
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
