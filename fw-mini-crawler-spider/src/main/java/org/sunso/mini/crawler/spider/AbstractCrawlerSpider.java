package org.sunso.mini.crawler.spider;

import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.context.CrawlerContext;
import org.sunso.mini.crawler.context.CrawlerContextThreadLocal;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.downloader.CrawlerDownloaderFactory;
import org.sunso.mini.crawler.downloader.EmptyCrawlerDownloader;

public abstract class AbstractCrawlerSpider implements CrawlerSpider {
    protected CrawlerContext context;

    protected  AbstractCrawlerSpider(CrawlerContext context) {
        this.context = context;
    }

    protected CrawlerDownloader getDownloader(Class<? extends CrawlerResult> clazz) {
        CrawlerResultDefine crawlerResultDefine = clazz.getAnnotation(CrawlerResultDefine.class);
        if (crawlerResultDefine == null) {
            return context.getDownloader();
        }
        Class<? extends CrawlerDownloader> downloader = crawlerResultDefine.downloader();
        if (downloader == null || EmptyCrawlerDownloader.class.equals(downloader)) {
            return context.getDownloader();
        }
        return CrawlerDownloaderFactory.getCrawlerDownloader(downloader);
    }

    protected CrawlerHttpRequest getRequestFromCrawlerQueue() {
        return context.getQueue().poll();
    }

}
