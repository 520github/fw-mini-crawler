package org.sunso.mini.crawler.enginer.builder;

import lombok.Data;
import org.junit.Test;
import org.sunso.mini.crawler.annotation.html.*;
import org.sunso.mini.crawler.annotation.request.Response;
import org.sunso.mini.crawler.annotation.request.ResponseStatus;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.enginer.BaseTest;
import org.sunso.mini.crawler.handler.ConsoleCrawlerHandler;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CrawlerEnginerBuilderAllUrlTest extends BaseTest {

    @Test
    public void CrawlerEnginerBuilderTest() {
        String url = "https://www.baidu.com";
        CrawlerEnginerBuilder.create()
                .request(
                        CrawlerHttpRequestBuilder.get(url)
                )
                .urlCrawlerResult(url, BaiduUrl.class)
                .defaultSingleCrawlerEnginer()
                .startCrawler();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @CrawlerResultDefine(handlers = ConsoleCrawlerHandler.class)
    class BaiduUrl implements CrawlerResult {
        @Response
        private CrawlerHttpResponse response;

        @ResponseStatus
        private String responseStatus;

        //@ResponseBody
        private String responseBody;

        @HtmlMap(key = {"attr.href"}, value = {"attr.title","text"}, keyExpressionFilter = "key.startsWith('http')")
        @HtmlCssPath("a[href]")
        private Map<String, String> urlMap;
    }


}
