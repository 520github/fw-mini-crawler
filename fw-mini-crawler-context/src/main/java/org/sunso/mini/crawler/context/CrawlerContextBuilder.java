package org.sunso.mini.crawler.context;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.spider.CrawlerSpider;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.downloader.HutoolCrawlerDownloader;
import org.sunso.mini.crawler.handler.CrawlerHandler;
import org.sunso.mini.crawler.parser.CrawlerParser;
import org.sunso.mini.crawler.parser.UnionCrawlerParser;
import org.sunso.mini.crawler.queue.CrawlerLinkedBlockingQueue;
import org.sunso.mini.crawler.queue.CrawlerQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrawlerContextBuilder {
    private List<CrawlerHttpRequest> requestList = new ArrayList<>();
    private CrawlerQueue queue;
    private CrawlerDownloader downloader;
    private CrawlerParser parser;
    private CrawlerHandler handler;
    private Class<? extends CrawlerSpider> spiderClassType;

    private Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap;
    private int spiderNum;

    private CrawlerContextBuilder() {

    }

    public static CrawlerContextBuilder create() {
        return new CrawlerContextBuilder();
    }

    public CrawlerContextBuilder request(CrawlerHttpRequest request) {
        requestList.add(request);
        return this;
    }

    public CrawlerContextBuilder requestList(List<CrawlerHttpRequest> requestList) {
        this.requestList.addAll(requestList);
        return this;
    }

    public CrawlerContextBuilder queue(CrawlerQueue queue) {
        this.queue = queue;
        return this;
    }

    public CrawlerContextBuilder downloader(CrawlerDownloader downloader) {
        this.downloader = downloader;
        return this;
    }

    public CrawlerContextBuilder parser(CrawlerParser parser) {
        this.parser = parser;
        return this;
    }

    public CrawlerContextBuilder handler(CrawlerHandler handler) {
        this.handler = handler;
        return this;
    }

    public CrawlerContextBuilder spiderClassType(Class<? extends CrawlerSpider> spiderClassType) {
        this.spiderClassType = spiderClassType;
        return this;
    }

    public CrawlerContextBuilder urlCrawlerResultMap(Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap) {
        this.urlCrawlerResultMap = urlCrawlerResultMap;
        return this;
    }

    public CrawlerContextBuilder spiderNum(int spiderNum) {
        this.spiderNum = spiderNum;
        return this;
    }

    public CrawlerContext build() {
        if (requestList.isEmpty()) {
            throw new IllegalArgumentException("requestList parameter is must");
        }
        if (queue == null) {
            queue = new CrawlerLinkedBlockingQueue();
        }
        if (downloader == null) {
            downloader = new HutoolCrawlerDownloader();
        }
        if (parser == null) {
            parser = new UnionCrawlerParser();
        }
        if (handler == null) {
            handler = null;
        }
        CrawlerContext context = new CrawlerContext();
        context.setQueue(queue);
        context.setDownloader(downloader);
        context.setParser(parser);
        context.setHandler(handler);
        context.setRequestList(requestList);
        context.setSpiderClassType(spiderClassType);
        context.setUrlCrawlerResultMap(urlCrawlerResultMap);
        context.setSpiderNum(spiderNum);
        return context;
    }
}
