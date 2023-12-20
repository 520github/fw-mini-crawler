package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.sunso.mini.crawler.annotation.custom.CustomUrl;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlUrl;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.http.option.Option;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.parser.CrawlerParser;
import org.sunso.mini.crawler.parser.dto.HtmlAjaxDTO;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunso520
 * @Title: CrawlerFieldParserRequest
 * @Description: 爬虫字段解析请求参数<br>
 * @Created on 2023/10/12 10:06
 */
@Data
public class CrawlerFieldParserRequest {
    // http请求
    private CrawlerHttpRequest request;
    // http请求响应结果
    private CrawlerHttpResponse response;
    // 待解析字段
    private Field field;
    // 爬虫解析器
    private CrawlerParser crawlerParser;
    // 爬虫最终结果类
    private Class<? extends CrawlerResult> clazz;
    // html Ajax dto对象
    private HtmlAjaxDTO htmlAjaxDTO;


    public static CrawlerFieldParserRequest newInstance(CrawlerHttpRequest request, CrawlerHttpResponse response, CrawlerParser crawlerParser, Class<? extends CrawlerResult> clazz) {
        CrawlerFieldParserRequest instance = new CrawlerFieldParserRequest();
        instance.setRequest(request);
        instance.setResponse(response);
        instance.setCrawlerParser(crawlerParser);
        instance.setClazz(clazz);
        return instance;
    }

    /**
     * 克隆一份CrawlerFieldParserRequest， 排除掉CrawlerHttpResponse
     * @return
     */
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
        if (response == null) {
            return null;
        }
        return response.body();
    }

    public <T extends Annotation> T fetchFieldAnnotation(Class<T> annotationClass) {
        return getField().getAnnotation(annotationClass);
    }

    public HtmlCssPath fetchHtmlCssPath() {
        return fetchFieldAnnotation(HtmlCssPath.class);
    }

    public CustomUrl fetchCustomUrl() {
        return fetchFieldAnnotation(CustomUrl.class);
    }

    public CrawlerResultDefine fetchCrawlerResultDefine() {
        if (clazz == null) {
            return null;
        }
        return clazz.getAnnotation(CrawlerResultDefine.class);
    }


    public Option fetchOption() {
        return request.getOption();
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
        parasMap.putAll(request.getParameters());
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

    /**
     * 生成一个子CrawlerHttpRequest
     *
     * @param htmlUrl HtmlUrl注解
     * @param subUrl 子请求url
     * @return 返回CrawlerHttpRequest
     */
    public CrawlerHttpRequest subRequest(HtmlUrl htmlUrl, String subUrl) {
        CrawlerHttpRequest subRequest = CrawlerHttpRequestBuilder.get(subUrl);
        subRequest.setWaitTime(htmlUrl.waitTime());
        if (htmlUrl.copyHeader()) {
            subRequest.setHeaders(getRequest().getHeaders());
        }
        if (htmlUrl.copyCookies()) {
            subRequest.setCookies(getRequest().getCookies());
        }
        if (htmlUrl.copyAttribute()) {
            subRequest.setAttributes(getRequest().getAttributes());
        }
        if (StrUtil.isNotBlank(htmlUrl.urlAlias())) {
            subRequest.setUrlAlias(htmlUrl.urlAlias());
        }
        return subRequest;
    }

    /**
     * 生成一个子CrawlerHttpRequest
     *
     * @param customUrl CustomUrl注解
     * @param subUrl 子请求url
     *
     * @return 返回CrawlerHttpRequest
     */
    public CrawlerHttpRequest subRequest(CustomUrl customUrl, String subUrl) {
        CrawlerHttpRequest subRequest = CrawlerHttpRequestBuilder.get(subUrl);
        subRequest.setWaitTime(customUrl.waitTime());
        if (customUrl.copyHeader()) {
            subRequest.setHeaders(getRequest().getHeaders());
        }
        if (customUrl.copyCookies()) {
            subRequest.setCookies(getRequest().getCookies());
        }
        if (customUrl.copyAttribute()) {
            subRequest.setAttributes(getRequest().getAttributes());
        }
        if (StrUtil.isNotBlank(customUrl.urlAlias())) {
            subRequest.setUrlAlias(customUrl.urlAlias());
        }
        return subRequest;
    }
}
