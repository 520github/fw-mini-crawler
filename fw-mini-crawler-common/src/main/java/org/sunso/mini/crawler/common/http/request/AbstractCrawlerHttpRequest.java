package org.sunso.mini.crawler.common.http.request;

import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEvent;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCrawlerHttpRequest implements CrawlerHttpRequest {

    protected String url;

    protected String contentType;

    private Map<String, String> parameters = new HashMap<>();

    private Map<String, Object> data = new HashMap<>();

    private Map<String, Object> attributes = new HashMap<>();

    private Map<String, CrawlerHttpRequestEvent> events = new HashMap<>();

    private Map<String, String> cookies = new HashMap<>();

    private Map<String, String> headers = new HashMap<>();


    protected AbstractCrawlerHttpRequest() {

    }

    protected AbstractCrawlerHttpRequest(String url) {
        this.setUrl(url);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public AbstractCrawlerHttpRequest addParameter(String name, String value) {
        parameters.put(name, value);
        return this;
    }

    public AbstractCrawlerHttpRequest setParameters(Map<String, String> parameters) {
        parameters.putAll(parameters);
        return this;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public AbstractCrawlerHttpRequest addData(String name, Object value) {
        data.put(name, value);
        return this;
    }

    public AbstractCrawlerHttpRequest addData(Map<String, Object> data) {
        data.putAll(data);
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public CrawlerHttpRequest setAttribute(String name, Object value) {
        attributes.put(name, value);
        return this;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public String getAttributeString(String name) {
        Object value = getAttribute(name);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public CrawlerHttpRequest setEvent(String eventKey, CrawlerHttpRequestEvent eventValue) {
        events.put(eventKey, eventValue);
        return this;
    }

    public CrawlerHttpRequestEvent getEvent(String eventKey) {
        return events.get(eventKey);
    }

    public Map<String, CrawlerHttpRequestEvent> getEvents() {
        return events;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void addCookie(String name, String value) {
        cookies.put(name, value);
    }

    public String getCookiesString() {
        String flag = "; ";
        StringBuilder sb = new StringBuilder();
        for(String name: cookies.keySet()) {
            sb.append(name).append("=").append(cookies.get(name)).append(flag);
        }
        if (sb.toString().endsWith(flag)) {
            sb.delete(sb.length()-flag.length(), sb.length());
        }
        return sb.toString();
    }
}
