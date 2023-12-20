package org.sunso.mini.crawler.common.http.request;

import org.sunso.mini.crawler.common.enums.ContentTypeEnum;

/**
 * @author sunso520
 * @Title:CrawlerHttpRequestBuilder
 * @Description: 爬虫Http请求定义构建类<br>
 * @Created on 2023/10/12 11:21
 */
public class CrawlerHttpRequestBuilder {

    private CrawlerHttpRequestBuilder() {

    }

    public static CrawlerHttpRequestBuilder create() {
        return new CrawlerHttpRequestBuilder();
    }

    public static CrawlerHttpRequest postContentTypeXWwwFormUrlEncoded(String url) {
        CrawlerHttpPostRequest postRequest = new CrawlerHttpPostRequest(url);
        postRequest.setContentType(ContentTypeEnum.applicationXWwwForm.getKey());
        return postRequest;
    }

    public static CrawlerHttpRequest postContentTypeJson(String url) {
        CrawlerHttpPostRequest postRequest = new CrawlerHttpPostRequest(url);
        postRequest.setContentType(ContentTypeEnum.applicationJson.getKey());
        return postRequest;
    }

    public static CrawlerHttpRequest get(String url) {
        CrawlerHttpGetRequest get = new CrawlerHttpGetRequest();
        get.setUrl(url);
        return get;
    }
}
