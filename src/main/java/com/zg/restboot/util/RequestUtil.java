package com.zg.restboot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * 请求处理工具
 * @author wangyonghao
 * @date 2021/2/6
 */
@Slf4j
public final class RequestUtil {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.valueOf("application/*+json"),
            MediaType.MULTIPART_FORM_DATA
    );

    public static String getRequestString(HttpServletRequest request) {
        StringBuilder log = new StringBuilder(1024);
        log.append(request.getMethod().toUpperCase());
        log.append(" ").append(getRequestURL(request));

        if(StringUtils.isNotBlank(request.getQueryString())){
            log.append("?").append(request.getQueryString());
        }
        log.append(" ").append(getRequestBodyString(request));
        return log.toString();
    }

    public static String getRequestBodyString(HttpServletRequest rawRequest) {
        // 如果未缓存request, Spring mvc 在解析参数之后，requestBody 流将会被关闭。
        // 必须使用 ContentCachingRequestWrapper 缓存，才能被读取到
        if (!(rawRequest instanceof ContentCachingRequestWrapper)) {
            return "";
        }
        ContentCachingRequestWrapper request = (ContentCachingRequestWrapper) rawRequest;

        byte[] content = request.getContentAsByteArray();
        // body 为空时返回空字符串
        if(content.length == 0){
            return "";
        }

        MediaType mediaType = MediaType.valueOf(request.getContentType());
        boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));

        StringBuilder requestBodyString = new StringBuilder(content.length);
        if (visible) {
            try {
                String contentString = new String(content, request.getCharacterEncoding());
                requestBodyString.append(contentString);
            } catch (UnsupportedEncodingException e) {
                requestBodyString.append("[").append(content.length).append(" bytes content]");
            }
        } else {
            requestBodyString.append("[").append(content.length).append(" bytes content]");
        }
        return requestBodyString.toString();
    }

    public static String getRequestURL(HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        String scheme = request.getScheme();
        int port = request.getServerPort();
        if (port < 0) {
            port = 80;
        }

        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if (scheme.equals("http") && port != 80 || scheme.equals("https") && port != 443) {
            url.append(':');
            url.append(port);
        }

        url.append(request.getRequestURI());
        return url.toString();
    }
}
