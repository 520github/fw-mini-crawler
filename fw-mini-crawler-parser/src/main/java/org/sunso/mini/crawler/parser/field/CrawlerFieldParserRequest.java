package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.sunso.mini.crawler.annotation.html.HtmlUrl;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.parser.CrawlerParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
public class CrawlerFieldParserRequest {
    private CrawlerHttpRequest request;
    private CrawlerHttpResponse response;
    private Field field;
    private CrawlerParser crawlerParser;

    //private Map<String, Object> beanDataMap;


    public static CrawlerFieldParserRequest newInstance(CrawlerHttpRequest request, CrawlerHttpResponse response, CrawlerParser crawlerParser) {
        CrawlerFieldParserRequest instance = new CrawlerFieldParserRequest();
        instance.setRequest(request);
        instance.setResponse(response);
        instance.setCrawlerParser(crawlerParser);
        return instance;
    }

    public CrawlerFieldParserRequest cloneExcludeResponse() {
        CrawlerFieldParserRequest instance = new CrawlerFieldParserRequest();
        instance.setField(field);
        instance.setCrawlerParser(crawlerParser);
        instance.setRequest(request);
        return instance;
    }


    public String fetchRequestUrl() {
        return request.getUrl();
    }

    public String fetchResponseBody() {
        return response.body();
    }

    public <T extends Annotation> T fetchFieldAnnotation(Class<T> annotationClass) {
        return getField().getAnnotation(annotationClass);
    }

    public String fetchFieldName() {
        return getField().getName();
    }

    public String fetchRequestParameterValue(String name) {
        return getRequest().getParameter(name);
    }

    public Map<String,Object> fetchRequestData() {
        return getRequest().getData();
    }

    public JSON fetchRequestDataJson() {
        return JSONUtil.parseObj(fetchRequestData());
    }


    public Map<String,Object> fetchAllReplaceParams() {
        Map<String,Object> parasMap = new HashMap<>();
        parasMap.putAll(request.getAttributes());
        return parasMap;
    }

    public void commitRequestAttribute(String name, Object value) {
        if (getRequest() == null) {
            return;
        }
        getRequest().setAttribute(name, value);
    }

    public Object fetchRequestAttributeValue(String name) {
        return getRequest().getAttributes().get(name);
    }

    public CrawlerHttpRequest subRequest(HtmlUrl htmlUrl, String subUrl) {
        CrawlerHttpRequest subRequest = CrawlerHttpRequestBuilder.get(subUrl);
        subRequest.setWaitTime(htmlUrl.waitTime());
        if (htmlUrl.copyHeader()) {
            subRequest.addHeaders(getRequest().getHeaders());
        }
        if (htmlUrl.copyCookies()) {
            subRequest.addCookies(getRequest().getCookies());
        }
        if (htmlUrl.copyAttribute()) {
            subRequest.setAttributes(getRequest().getAttributes());
        }
        if (StrUtil.isNotBlank(htmlUrl.urlAlias())) {
            subRequest.setUrlAlias(htmlUrl.urlAlias());
        }
        return subRequest;
    }
}
