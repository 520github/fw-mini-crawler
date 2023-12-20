package org.sunso.mini.crawler.downloader;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

/**
 * @author sunso520
 * @Title:AbstractSeleniumCrawlerDownloader
 * @Description: 基于Selenium处理http请求的爬虫下载器<br>
 * @Created on 2023/10/25 15:03
 */
public class SeleniumCrawlerDownloader extends AbstractSeleniumCrawlerDownloader {
    @Override
    public CrawlerHttpResponse download(CrawlerHttpRequest request) {
        return doDownload(request);
    }


}
