package org.sunso.mini.crawler.downloader;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

public interface CrawlerDownloader {
    CrawlerHttpResponse download(CrawlerHttpRequest request);
}
