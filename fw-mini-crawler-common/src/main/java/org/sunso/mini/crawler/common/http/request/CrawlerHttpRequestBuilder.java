package org.sunso.mini.crawler.common.http.request;

import org.sunso.mini.crawler.common.enums.ContentTypeEnum;

public class CrawlerHttpRequestBuilder {

    private CrawlerHttpRequestBuilder() {

    }

    public static CrawlerHttpRequestBuilder create() {
        return new CrawlerHttpRequestBuilder();
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
