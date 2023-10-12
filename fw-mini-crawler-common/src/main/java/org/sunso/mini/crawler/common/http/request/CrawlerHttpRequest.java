package org.sunso.mini.crawler.common.http.request;

import java.util.Map;

public interface CrawlerHttpRequest {
    String getUrl();

    void setUrl(String url);

    String getContentType();

    void setContentType(String contentType);

    void addData(String name, String value);

    void addData(Map<String, Object> data);

    Map<String, Object> getData();

    void addHeader(String name, String value);

    Map<String, String> getHeaders();

    Map<String, String> getCookies();

    String getCookiesString();

    void addCookie(String name, String value);
}
