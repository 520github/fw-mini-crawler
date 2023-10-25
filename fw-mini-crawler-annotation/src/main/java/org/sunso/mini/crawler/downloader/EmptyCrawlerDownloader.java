package org.sunso.mini.crawler.downloader;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

public class EmptyCrawlerDownloader implements CrawlerDownloader{
    @Override
    public CrawlerHttpResponse download(CrawlerHttpRequest request) {
        return null;
    }
}
