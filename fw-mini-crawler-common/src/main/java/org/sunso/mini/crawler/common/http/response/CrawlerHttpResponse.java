package org.sunso.mini.crawler.common.http.response;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import lombok.Setter;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author sunso520
 * @Title:CrawlerHttpResponse
 * @Description: 爬虫Http请求的响应结果
 * 该响应结果包含：
 * <ul>
 *     <li>响应状态码</li>
 *     <li>响应字符集</li>
 *     <li>响应数据流</li>
 *     <li>响应数据字节流</li>
 * </ul>
 * @Created on 2023/10/12 10:27
 */
@Setter
public class CrawlerHttpResponse {
    /**
     * 默认的请求编码、URL的encode、decode编码
     */
    protected static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;
    /**
     * 编码
     */
    protected Charset charset = DEFAULT_CHARSET;

    /**
     * 响应数据字节流
     */
    private byte[] bodyBytes;

    /**
     * 响应状态码
     */
    private int status;
    private boolean charsetFromResponse;

    /**
     * 响应数据流
     */
    private InputStream in;

    public CrawlerHttpResponse() {

    }

    public CrawlerHttpResponse(String body) {
        if (body != null) {
            this.bodyBytes = body.getBytes(charset);
        }
    }

    public CrawlerHttpResponse(String body, Charset charset) {
        if (charset != null) {
            this.charset = charset;
        }
        if (body != null) {
            this.bodyBytes = body.getBytes(this.charset);
        }
    }

    public byte[] bodyBytes() {
        if (bodyBytes != null) {
            return bodyBytes;
        }
        if (in == null) {
            return null;
        }
        bodyBytes = IoUtil.readBytes(in);
        return bodyBytes;
    }

    public String body() {
        return HttpUtil.getString(bodyBytes(), this.charset, this.charsetFromResponse);
    }

    public int status() {
        return status;
    }

    public Charset charset() {
        return charset;
    }

    public static CrawlerHttpResponse create(String body) {
        return new CrawlerHttpResponse(body);
    }

    public InputStream getInputStream() {
        return in;
    }

    public boolean isHtml() {
        String body = body();
        if (body != null && body.contains("<html>")) {
            return true;
        }
        return false;
    }

    public String getResponseDataType() {
        if (isHtml()) {
            return "html";
        }
        return "json";
    }
}
