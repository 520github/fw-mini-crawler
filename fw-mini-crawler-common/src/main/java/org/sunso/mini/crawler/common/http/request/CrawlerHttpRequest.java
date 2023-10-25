package org.sunso.mini.crawler.common.http.request;

import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEvent;

import java.util.Map;

public interface CrawlerHttpRequest {
    String getUrl();

    void setUrl(String url);

    String getContentType();

    void setContentType(String contentType);

    CrawlerHttpRequest addParameter(String name, String value);

    CrawlerHttpRequest setParameters(Map<String, String> parameters);

    String getParameter(String name);

    Map<String, String> getParameters();

    CrawlerHttpRequest addData(String name, Object value);

    CrawlerHttpRequest addData(Map<String, Object> data);

    Map<String, Object> getData();

    CrawlerHttpRequest setAttribute(String name, Object value);

    Object getAttribute(String name);

    String getAttributeString(String name);

    Map<String, Object> getAttributes();

    CrawlerHttpRequest setEvent(String eventKey, CrawlerHttpRequestEvent eventValue);

    CrawlerHttpRequestEvent getEvent(String eventKey);

    Map<String, CrawlerHttpRequestEvent> getEvents();

    void addHeader(String name, String value);

    Map<String, String> getHeaders();

    Map<String, String> getCookies();

    String getCookiesString();

    void addCookie(String name, String value);
}
