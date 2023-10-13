package org.sunso.mini.crawler.parser;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpEmptyRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

public abstract class AbstractHtmlCrawlerParser extends BaseTest {
    protected UnionCrawlerParser unionCrawlerParser = new UnionCrawlerParser();


    protected CrawlerHttpRequest getEmptyRequest() {
        CrawlerHttpEmptyRequest request = new CrawlerHttpEmptyRequest();
        request.setUrl("http://empty");
        return request;
    }

    protected CrawlerHttpResponse getEmptyResponse() {
        return CrawlerHttpResponse.create("");
    }
}
