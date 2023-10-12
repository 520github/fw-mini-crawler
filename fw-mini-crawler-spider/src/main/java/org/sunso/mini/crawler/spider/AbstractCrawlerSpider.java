package org.sunso.mini.crawler.spider;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.spider.CrawlerSpider;
import org.sunso.mini.crawler.context.CrawlerContext;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;

public abstract class AbstractCrawlerSpider implements CrawlerSpider {
    protected CrawlerContext context;

    protected  AbstractCrawlerSpider(CrawlerContext context) {
        this.context = context;
    }

    protected CrawlerDownloader getDownloader() {
        return context.getDownloader();
    }

    protected CrawlerHttpRequest getRequestFromCrawlerQueue() {
        return context.getQueue().poll();
    }

}
