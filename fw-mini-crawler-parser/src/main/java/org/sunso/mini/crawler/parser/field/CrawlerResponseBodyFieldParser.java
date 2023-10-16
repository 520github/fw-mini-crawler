package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.annotation.request.ResponseBody;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

public class CrawlerResponseBodyFieldParser extends CrawlerResponseFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        return getResponseBody(request);
    }

    private String getResponseBody(CrawlerFieldParserRequest request) {
        ResponseBody responseBody = request.fetchFieldAnnotation(ResponseBody.class);
        CrawlerHttpResponse response =  getResponseData(responseBody.parentLevel(), request);
        if (response == null) {
            return null;
        }
        return response.body();
    }
}
