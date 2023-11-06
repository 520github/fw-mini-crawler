package org.sunso.mini.crawler.enginer.builder;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.spider.CrawlerSpider;
import org.sunso.mini.crawler.context.CrawlerContext;
import org.sunso.mini.crawler.context.CrawlerContextBuilder;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.enginer.CrawlerEnginer;
import org.sunso.mini.crawler.enginer.SingleCrawlerEnginer;
import org.sunso.mini.crawler.handler.CrawlerHandler;
import org.sunso.mini.crawler.parser.CrawlerParser;
import org.sunso.mini.crawler.queue.CrawlerQueue;
import org.sunso.mini.crawler.spider.OneTimeCrawlerSpider;
import org.sunso.mini.crawler.task.CrawlerTask;
import org.sunso.mini.crawler.task.CrawlerTaskFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrawlerEnginerBuilder {
    private String bizType;
    private List<CrawlerHttpRequest> requestList = new ArrayList<>();
    private CrawlerTask task;
    private CrawlerQueue queue;
    private CrawlerDownloader downloader;
    private CrawlerParser parser;
    //private CrawlerHandler handler;
    private Class<? extends CrawlerSpider> spiderClassType;

    private Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap;
    private int spiderNum;


    private CrawlerEnginerBuilder() {
    }

    public static CrawlerEnginerBuilder create() {
        return new CrawlerEnginerBuilder();
    }

    public CrawlerEnginerBuilder bizType(String bizType) {
        this.bizType = bizType;
        return this;
    }

    public CrawlerEnginerBuilder request(CrawlerHttpRequest request) {
        requestList.add(request);
        return this;
    }

    public CrawlerEnginerBuilder requestList(List<CrawlerHttpRequest> requestList) {
        requestList.addAll(requestList);
        return this;
    }

    public CrawlerEnginerBuilder task(CrawlerTask task) {
        this.task = task;
        return this;
    }
    public CrawlerEnginerBuilder queue(CrawlerQueue queue) {
        this.queue = queue;
        return this;
    }

    public CrawlerEnginerBuilder downloader(CrawlerDownloader downloader) {
        this.downloader = downloader;
        return this;
    }

    public CrawlerEnginerBuilder parser(CrawlerParser parser) {
        this.parser = parser;
        return this;
    }

//    public CrawlerEnginerBuilder handler(CrawlerHandler handler) {
//        this.handler = handler;
//        return this;
//    }

    public CrawlerEnginerBuilder spiderClassType(Class<? extends CrawlerSpider> spiderClassType) {
        this.spiderClassType = spiderClassType;
        return this;
    }

    public CrawlerEnginerBuilder urlCrawlerResultMap(Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap) {
        this.urlCrawlerResultMap = urlCrawlerResultMap;
        return this;
    }

    public CrawlerEnginerBuilder urlCrawlerResult(String url, Class<? extends CrawlerResult> crawlerResult) {
         if (this.urlCrawlerResultMap == null) {
             this.urlCrawlerResultMap = new HashMap<>();
         }
         this.urlCrawlerResultMap.put(url, crawlerResult);
        return this;
    }

    public CrawlerEnginerBuilder spiderNum(int spiderNum) {
        this.spiderNum = spiderNum;
        return this;
    }

    public CrawlerEnginer defaultSingleCrawlerEnginer() {
        SingleCrawlerEnginer singleCrawlerEnginer = new SingleCrawlerEnginer();
        spiderNum = 1;
        singleCrawlerEnginer.setCrawlerContext(getCrawlerContext());
        return singleCrawlerEnginer;
    }

    private CrawlerContext getCrawlerContext() {
        if (spiderClassType == null) {
            spiderClassType = OneTimeCrawlerSpider.class;
        }
        if (task == null) {
            task = CrawlerTaskFactory.getDefaultCrawlerTask(bizType);
        }
        return CrawlerContextBuilder.create()
                .bizType(bizType)
                .requestList(requestList)
                .task(task)
                .queue(queue)
                .downloader(downloader)
                .parser(parser)
                //.handler(handler)
                .spiderClassType(spiderClassType)
                .urlCrawlerResultMap(urlCrawlerResultMap)
                .spiderNum(spiderNum)
                .build();
    }

}
