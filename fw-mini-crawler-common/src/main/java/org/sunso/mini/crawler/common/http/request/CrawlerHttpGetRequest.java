package org.sunso.mini.crawler.common.http.request;

/**
 * @author sunso520
 * @Title:CrawlerHttpGetRequest
 * @Description: 爬虫Http get请求定义<br>
 * @Created on 2023/10/12 11:21
 */
public class CrawlerHttpGetRequest extends AbstractCrawlerHttpRequest {

    public CrawlerHttpGetRequest() {

    }

    public CrawlerHttpGetRequest(String url) {
        super(url);
    }
}
