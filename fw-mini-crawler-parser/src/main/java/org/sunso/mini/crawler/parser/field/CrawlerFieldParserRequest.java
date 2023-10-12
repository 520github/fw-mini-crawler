package org.sunso.mini.crawler.parser.field;

import lombok.Data;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.parser.CrawlerParser;

import java.lang.reflect.Field;

@Data
public class CrawlerFieldParserRequest {
    private CrawlerHttpRequest request;
    private CrawlerHttpResponse response;
    private Field field;
    private CrawlerParser crawlerParser;


    public static CrawlerFieldParserRequest newInstance(CrawlerHttpRequest request, CrawlerHttpResponse response, CrawlerParser crawlerParser) {
        CrawlerFieldParserRequest instance = new CrawlerFieldParserRequest();
        instance.setRequest(request);
        instance.setResponse(response);
        instance.setCrawlerParser(crawlerParser);
        return instance;
    }


    public String fetchRequestUrl() {
        return request.getUrl();
    }

    public String fetchResponseBody() {
        return response.body();
    }
}
