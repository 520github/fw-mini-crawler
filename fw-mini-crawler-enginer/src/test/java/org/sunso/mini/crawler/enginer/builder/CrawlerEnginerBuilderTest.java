package org.sunso.mini.crawler.enginer.builder;

import lombok.Data;
import org.junit.Test;
import org.sunso.mini.crawler.common.annotation.html.HtmlAttr;
import org.sunso.mini.crawler.common.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.common.annotation.html.HtmlText;
import org.sunso.mini.crawler.common.annotation.html.HtmlUrl;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.enginer.BaseTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CrawlerEnginerBuilderTest extends BaseTest {

    @Test
    public void CrawlerEnginerBuilderTest() {
        String url = "http://www.zhongzhenglawyer.com/Content/2232432.html";
        CrawlerEnginerBuilder.create()
                .request(CrawlerHttpRequestBuilder.get(url))
                .urlCrawlerResult(url, ArticleList.class)
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
    class ArticleList implements CrawlerResult {

        @HtmlText
        @HtmlCssPath("div.ModuleNewsListGiant div.BodyCenter li")
        private List<ArticleListOne> articleList;
    }

    @Data
    class ArticleListOne implements CrawlerResult {

        @HtmlUrl(value = "href")
        @HtmlCssPath("a")
        private String detailUrl;

        @HtmlAttr("data-src")
        @HtmlCssPath("img")
        private String img;

        @HtmlAttr("title")
        @HtmlCssPath("img")
        private String title;

        @HtmlText
        @HtmlCssPath("time")
        private String time;
    }
}
