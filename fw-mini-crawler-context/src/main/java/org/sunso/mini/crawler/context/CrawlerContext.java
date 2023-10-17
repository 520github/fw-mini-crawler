package org.sunso.mini.crawler.context;

import lombok.Data;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.spider.CrawlerSpider;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.handler.CrawlerHandler;
import org.sunso.mini.crawler.parser.CrawlerParser;
import org.sunso.mini.crawler.queue.CrawlerQueue;

import java.util.List;
import java.util.Map;

@Data
public class CrawlerContext {
    private List<CrawlerHttpRequest> requestList;
    private Class<? extends CrawlerSpider> spiderClassType;
    private int spiderNum;
    private CrawlerQueue queue;
    private CrawlerDownloader downloader;
    private CrawlerParser parser;
    private CrawlerHandler handler;

    private Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap;

}
