package org.sunso.mini.crawler.common.http.request;

import org.sunso.mini.crawler.common.enums.HttpRequestMethodEnum;

/**
 * @author sunso520
 * @Title:CrawlerHttpRequestFactory
 * @Description: 爬虫Http请求定义工厂类<br>
 * @Created on 2023/10/12 11:21
 */
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
