package org.sunso.mini.crawler.downloader;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

public class SeleniumCrawlerDownloader extends AbstractSeleniumCrawlerDownloader {
    @Override
    public CrawlerHttpResponse download(CrawlerHttpRequest request) {
        return doDownload(request);
    }


}
