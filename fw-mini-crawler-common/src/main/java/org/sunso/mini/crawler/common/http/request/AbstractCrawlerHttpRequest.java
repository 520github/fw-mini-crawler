package org.sunso.mini.crawler.common.http.request;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCrawlerHttpRequest implements CrawlerHttpRequest {

    protected String url;

    protected String contentType;

    private Map<String, String> parameters = new HashMap<>();

    private Map<String, Object> data = new HashMap<>();

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
