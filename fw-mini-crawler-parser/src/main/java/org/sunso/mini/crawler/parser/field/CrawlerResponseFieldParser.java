package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.annotation.request.Response;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

public class CrawlerResponseFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        return getResponseData(request);
    }

    protected CrawlerHttpResponse getResponseData(CrawlerFieldParserRequest request) {
        Response response = request.fetchFieldAnnotation(Response.class);
        if (response == null) {
            return null;
        }
        int parentLevel = response.parentLevel();
        return getResponseData(parentLevel, request);
    }

    protected CrawlerHttpResponse getResponseData(int parentLevel, CrawlerFieldParserRequest request) {
        if (parentLevel == 0) {
            return request.getResponse();
        }
        //TODO  多级的情况，获取前N级的结果
        return null;
    }
}
