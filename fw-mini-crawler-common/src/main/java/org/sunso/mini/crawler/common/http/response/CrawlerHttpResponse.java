package org.sunso.mini.crawler.common.http.response;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import lombok.Setter;

import java.io.InputStream;
import java.nio.charset.Charset;

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
    private byte[] bodyBytes;
    private int status;
    private boolean charsetFromResponse;
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
        return IoUtil.readBytes(in);
    }

    public String body() {
        return HttpUtil.getString(bodyBytes(), this.charset, this.charsetFromResponse);
    }

    public int status() {
        return status;
    }

    public static CrawlerHttpResponse create(String body) {
        return new CrawlerHttpResponse(body);
    }



}
