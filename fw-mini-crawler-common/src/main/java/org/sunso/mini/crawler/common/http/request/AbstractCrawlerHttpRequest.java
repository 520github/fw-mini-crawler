package org.sunso.mini.crawler.common.http.request;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEvent;
import org.sunso.mini.crawler.common.http.option.Option;
import org.sunso.mini.crawler.common.http.option.OptionFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCrawlerHttpRequest implements CrawlerHttpRequest {

    protected String url;

    protected String urlAlias;

    protected String requestId;

    protected int sort = 0;

    protected long waitTime = 0;

    protected String contentType;

    private Map<String, String> parameters = new HashMap<>();

    private Map<String, Object> data = new HashMap<>();

    private Map<String, Object> attributes = new HashMap<>();

    private Map<String, CrawlerHttpRequestEvent> events = new HashMap<>();

    private Map<String, String> cookies = new HashMap<>();

    private Map<String, String> headers = new HashMap<>();

    private Option option = OptionFactory.getDefaultSwitchArgTrue();


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

    public String getUrlAlias() {
        return urlAlias;
    }

    public CrawlerHttpRequest setUrlAlias(String urlAlias) {
        this.urlAlias = urlAlias;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getAndSetRequestIdIfEmpty() {
        if (StrUtil.isBlank(requestId)) {
            setRequestId(IdUtil.fastSimpleUUID());
        }
        return requestId;
    }

    public CrawlerHttpRequest setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public int getSort() {
        return this.sort;
    }

    public CrawlerHttpRequest setSort(int sort) {
        this.sort = sort;
        return this;
    }

    public long getWaitTime() {
        return this.waitTime;
    }

    public CrawlerHttpRequest setWaitTime(long waitTime) {
        this.waitTime = waitTime;
        return this;
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
        this.parameters.putAll(parameters);
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

    public AbstractCrawlerHttpRequest setData(Map<String, Object> data) {
        this.data.putAll(data);
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public CrawlerHttpRequest setAttribute(String name, Object value) {
        attributes.put(name, value);
        return this;
    }

    public CrawlerHttpRequest setAttributes(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
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

    public CrawlerHttpRequest setEvents(Map<String, CrawlerHttpRequestEvent> events) {
        this.events.putAll(events);
        return this;
    }

    public CrawlerHttpRequestEvent getEvent(String eventKey) {
        return events.get(eventKey);
    }

    public Map<String, CrawlerHttpRequestEvent> getEvents() {
        return events;
    }

    public CrawlerHttpRequest addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public CrawlerHttpRequest setHeaders(Map<String, String> headerMap) {
        headers.putAll(headerMap);
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public CrawlerHttpRequest addCookie(String name, String value) {
        cookies.put(name, value);
        return this;
    }

    public CrawlerHttpRequest setCookies(Map<String, String> cookiesMap) {
        this.cookies.putAll(cookiesMap);
        return this;
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

    public CrawlerHttpRequest setOption(Option option) {
        this.option = option;
        return this;
    }

    public Option getOption() {
        return this.option;
    }
}
