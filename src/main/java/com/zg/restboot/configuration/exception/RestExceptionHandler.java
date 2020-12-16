package com.zg.restboot.configuration.exception;

import com.zg.restboot.configuration.http.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
public class RestExceptionHandler {
    /**
     * 处理业务异常
     * @param ex 业务异常
     * @param request 请求信息
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult handlerServiceException(ServiceException ex, HttpServletRequest request) {
        log.debug(this.logRequest(request),ex);
        return ResponseResult.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult handlerException(Exception ex, HttpServletRequest request) {
        log.error(this.logRequest(request),ex);
        return ResponseResult.error(500, "服务异常:"+ex.getMessage());
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
