package org.sunso.mini.crawler.context;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.spider.CrawlerSpider;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.handler.CrawlerHandler;
import org.sunso.mini.crawler.parser.CrawlerParser;
import org.sunso.mini.crawler.queue.CrawlerQueue;
import org.sunso.mini.crawler.task.CrawlerTask;

import java.util.List;
import java.util.Map;

@Data
public class CrawlerContext {
    private String bizType;
    private List<CrawlerHttpRequest> requestList;
    private Class<? extends CrawlerSpider> spiderClassType;
    private int spiderNum;
    private CrawlerTask task;
    private CrawlerQueue queue;
    private CrawlerDownloader downloader;
    private CrawlerParser parser;
    //private CrawlerHandler handler;

    private Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap;


    public CrawlerTask fetchTask() {
        if (task != null && StrUtil.isNotBlank(bizType)
                && !bizType.equals(task.getBizType())) {
            task.setBizType(bizType);
        }
        return task;
    }

}
