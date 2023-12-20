package org.sunso.mini.crawler.common.http.request;

/**
 * @author sunso520
 * @Title:CrawlerHttpEmptyRequest
 * @Description: 爬虫Http空请求定义<br>
 * @Created on 2023/10/12 11:21
 */
public class CrawlerHttpEmptyRequest extends AbstractCrawlerHttpRequest {

    public static CrawlerHttpEmptyRequest create() {
        return new CrawlerHttpEmptyRequest();
    }

}
