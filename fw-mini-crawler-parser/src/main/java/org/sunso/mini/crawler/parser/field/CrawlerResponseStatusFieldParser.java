package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.annotation.request.ResponseBody;
import org.sunso.mini.crawler.annotation.request.ResponseStatus;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

public class CrawlerResponseStatusFieldParser extends CrawlerResponseFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        return getResponseStatus(request);
    }

    private Integer getResponseStatus(CrawlerFieldParserRequest request) {
        ResponseStatus responseStatus = request.fetchFieldAnnotation(ResponseStatus.class);
        CrawlerHttpResponse response =  getResponseData(responseStatus.parentLevel(), request);
        if (response == null) {
            return null;
        }
        return response.status();
    }
}
