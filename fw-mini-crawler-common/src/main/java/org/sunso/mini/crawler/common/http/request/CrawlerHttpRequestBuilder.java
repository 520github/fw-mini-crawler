package org.sunso.mini.crawler.common.http.request;

public class CrawlerHttpRequestBuilder {

    private CrawlerHttpRequestBuilder() {

    }

    public static CrawlerHttpRequestBuilder create() {
        return new CrawlerHttpRequestBuilder();
    }

    public static CrawlerHttpRequest get(String url) {
        CrawlerHttpGetRequest get = new CrawlerHttpGetRequest();
        get.setUrl(url);
        return get;
    }
}
