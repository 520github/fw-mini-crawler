package org.sunso.mini.crawler.downloader;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

/**
 * @author sunso520
 * @Title:CrawlerDownloader
 * @Description: 爬虫下载器
 * 根据CrawlerHttpRequest执行下载对应的内容
 * @Created on 2023/10/12 11:02
 */
public interface CrawlerDownloader {

    /**
     * 根据CrawlerHttpRequest执行下载对应的内容
     * @param request 爬虫任务CrawlerHttpRequest
     * @return CrawlerHttpResponse 下载CrawlerHttpRequest对应的内容
     */
    CrawlerHttpResponse download(CrawlerHttpRequest request);
}
