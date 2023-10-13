package org.sunso.mini.crawler.common.http.request;

import org.sunso.mini.crawler.common.enums.HttpRequestMethodEnum;

public class CrawlerHttpRequestFactory {

    public static CrawlerHttpRequest getCrawlerHttpRequest(String url, HttpRequestMethodEnum methodEnum) {
        if (HttpRequestMethodEnum.GET == methodEnum) {
            return new CrawlerHttpGetRequest(url);
        }
        else if (HttpRequestMethodEnum.POST == methodEnum) {
            return new CrawlerHttpPostRequest(url);
        }
        return null;
    }
}
