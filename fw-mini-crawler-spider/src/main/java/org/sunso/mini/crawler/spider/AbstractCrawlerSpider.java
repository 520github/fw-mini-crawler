package org.sunso.mini.crawler.spider;

import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.context.CrawlerContext;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.downloader.CrawlerDownloaderFactory;
import org.sunso.mini.crawler.downloader.EmptyCrawlerDownloader;
import org.sunso.mini.crawler.parser.CrawlerParser;
import org.sunso.mini.crawler.parser.CrawlerParserFactory;
import org.sunso.mini.crawler.parser.EmptyCrawlerParser;
import org.sunso.mini.crawler.task.CrawlerTask;

public abstract class AbstractCrawlerSpider implements CrawlerSpider {
    protected CrawlerContext context;

    protected  AbstractCrawlerSpider(CrawlerContext context) {
        this.context = context;
    }

    protected CrawlerDownloader getDownloader(Class<? extends CrawlerResult> clazz) {
        CrawlerResultDefine crawlerResultDefine = getCrawlerResultDefine(clazz);
        if (crawlerResultDefine != null &&  !EmptyCrawlerDownloader.class.equals(crawlerResultDefine.downloader())) {
            return CrawlerDownloaderFactory.getCrawlerDownloader(crawlerResultDefine.downloader());
        }
        if (context.getDownloader() != null) {
            return context.getDownloader();
        }
        return CrawlerDownloaderFactory.getDefaultCrawlerDownloader();
    }

    protected CrawlerParser getCrawlerParser(Class<? extends CrawlerResult> clazz) {
        CrawlerResultDefine crawlerResultDefine = getCrawlerResultDefine(clazz);
        if (crawlerResultDefine != null && !EmptyCrawlerParser.class.equals(crawlerResultDefine.parser())) {
            return CrawlerParserFactory.getCrawlerParser(crawlerResultDefine.parser());
        }
        if (context.getParser() != null) {
            return context.getParser();
        }
        return CrawlerParserFactory.getDefaultCrawlerParser();
    }

    protected CrawlerHttpRequest getRequestFromCrawlerQueue() {
        return context.getQueue().poll();
    }


    protected CrawlerHttpRequest getRequestFromCrawlerTask() {
        return context.getTask().pollTask();
    }

    protected CrawlerTask getCrawlerTask() {
        return context.fetchTask();
    }


    protected CrawlerResultDefine getCrawlerResultDefine(Class<? extends CrawlerResult> clazz) {
        return clazz.getAnnotation(CrawlerResultDefine.class);
    }

}
