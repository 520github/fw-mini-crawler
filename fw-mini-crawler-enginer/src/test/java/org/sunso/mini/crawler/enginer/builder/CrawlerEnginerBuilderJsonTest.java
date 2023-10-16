package org.sunso.mini.crawler.enginer.builder;

import lombok.Data;
import org.junit.Test;
import org.sunso.mini.crawler.annotation.html.HtmlAttr;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlText;
import org.sunso.mini.crawler.annotation.html.HtmlUrl;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.request.*;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.enginer.BaseTest;
import org.sunso.mini.crawler.formatter.date.DateNormyyyyMMddFormatter;
import org.sunso.mini.crawler.formatter.money.Money2ChineseFormatter;
import org.sunso.mini.crawler.formatter.type.BigDecimalFormatter;
import org.sunso.mini.crawler.handler.ConsoleCrawlerHandler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CrawlerEnginerBuilderJsonTest extends BaseTest {

    @Test
    public void CrawlerEnginerBuilderTest() {
        String url = "https://dev-cqb.lddstp.com/zz-official/article/info/page/list";
        CrawlerEnginerBuilder.create()
                .request(
                        CrawlerHttpRequestBuilder.postContentTypeJson(url)
                                .addData("pageNum", "1")
                                .addData("pageSize", "3")
                                .addData("config", Config.newInstance())
                                .addData("configList", Arrays.asList(Config.newInstance(), Config.newInstance()))
                )
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
    @CrawlerResultDefine(handlers = ConsoleCrawlerHandler.class)
    class ArticleList implements CrawlerResult {
        @Response
        private CrawlerHttpResponse response;

        @ResponseStatus
        private String responseStatus;

        //@ResponseBody
        private String responseBody;

        @RequestData("pageNum")
        private Integer pageNum;

        @RequestData("pageSize")
        private Integer pageSize;

        @RequestData("configList")
        private List<Config> configList;

        @RequestData("config.loadConfig")
        private String loadConfig;

        @JsonPath("code")
        private String code;
        @JsonPath("msg")
        private String msg;
        @JsonPath("data.total")
        private Integer total;

        @JsonPath(value = "data.total", formatter = {BigDecimalFormatter.class, Money2ChineseFormatter.class})
        private String chinese;

        @JsonPath(value = "data.list.viewNum", expressionFilter = "viewNumList>0 && viewNumList<=5")
        private List<Integer> viewNumList;

        @JsonPath(value = "data.list", expressionFilter = "viewNum>0")
        private List<ArticleListOne> articleList;
    }

    @Data
    @CrawlerResultDefine(handlers = ConsoleCrawlerHandler.class)
    class ArticleListOne implements CrawlerResult {
        @Response
        private CrawlerHttpResponse response;

        @Request
        private CrawlerHttpRequest request;

        //@ResponseBody
        private String responseBody;

        @ResponseStatus
        private String responseStatus;

        @RequestData("pageNum")
        private Integer pageNum;

        @RequestData("pageSize")
        private Integer pageSize;

        @JsonPath("coverImg")
        private String img;

        @JsonPath("title")
        private String title;

        @JsonPath(value = "createDate", formatter = DateNormyyyyMMddFormatter.class)
        private Date time;

        @JsonPath("viewNum")
        private Integer viewNum;
    }

    @Data
    static class Config implements CrawlerResult {
        @JsonPath("loadConfig")
        private String loadConfig;
        @JsonPath("listConfig")
        private String listConfig;

        public static Config newInstance() {
            Config config = new Config();
            config.setLoadConfig("load");
            config.setListConfig("list");
            return config;
        }
    }
}
