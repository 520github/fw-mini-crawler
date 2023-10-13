package org.sunso.mini.crawler.parser;

import lombok.Data;
import org.junit.Test;
import org.sunso.mini.crawler.annotation.html.*;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpGetRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import java.util.List;

public class HtmlCrawlerParserTest extends BaseTest {

    private HtmlCrawlerParser parser = new HtmlCrawlerParser();

    private UnionCrawlerParser unionCrawlerParser = new UnionCrawlerParser();

    @Test
    public void parseTest() {
        DemoCrawlerResult result = (DemoCrawlerResult)unionCrawlerParser.parse(DemoCrawlerResult.class, getRequest(), getResponse());
        print(result);
    }

    private CrawlerHttpRequest getRequest() {
        CrawlerHttpGetRequest request = new CrawlerHttpGetRequest();
        request.setUrl("http://www.dd.com");
        return request;
    }

    private CrawlerHttpResponse getResponse() {

        StringBuilder body = new StringBuilder();
        body.append("<div id='test' age='100' >this is a test");
        body.append("<li>1</li>");
        body.append("<li>2</li>");
        body.append("<li>3</li>");
        body.append("</div>");
        body.append("<div id='sub'>");
        body.append("<li>");
        body.append("<a href='http://www.baidu.com'></a>");
        body.append("<img src='http://www.ddd.com/mm/nn/99.jpg'></img>");
        body.append("<div source='fromMiNi'></div>");
        body.append("</li>");
        body.append("<li>");
        body.append("<a href='http://www.mmm.com'></a>");
        body.append("<img src='http://www.ppp.com/mm/nn/99.jpg'></img>");
        body.append("<div source='fromNPS'></div>");
        body.append("<div>");
        body.append("<ul data='11'></ul>");
        body.append("<ul data='2'></ul>");
        body.append("<ul data='9'></ul>");
        body.append("</div>");
        body.append("</li>");
        body.append("</div>");
        CrawlerHttpResponse response = new CrawlerHttpResponse(body.toString());
        return response;
    }

    @Data
    class DemoCrawlerResult implements CrawlerResult {
        private int id=40;

        @HtmlText()
        @HtmlCssPath("div#test")
        private String content;

        @HtmlAttr(value = "age")
        @HtmlCssPath("div#test")
        private int age;

        @HtmlText()
        @HtmlCssPath("div#test li")
        private Integer[] sortList;

        @HtmlCssPath("div#sub>li")
        private List<SubDemoCrawlerResult> subDemoCrawlerResult;


        private String name;

    }

    @Data
    class SubDemoCrawlerResult implements CrawlerResult {
        @HtmlImage
        @HtmlCssPath("img")
        private String imageUrl;

        @HtmlUrl
        @HtmlCssPath("a")
        private String url;

        @HtmlAttr("source")
        @HtmlCssPath("div")
        private String source;

        @HtmlAttr("data")
        @HtmlCssPath("div ul")
        private List<Integer> tagList;
    }
}
