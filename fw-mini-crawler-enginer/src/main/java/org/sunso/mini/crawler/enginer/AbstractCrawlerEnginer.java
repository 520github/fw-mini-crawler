package org.sunso.mini.crawler.enginer;

import lombok.SneakyThrows;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.spider.CrawlerSpider;
import org.sunso.mini.crawler.context.CrawlerContext;

import java.lang.reflect.Constructor;

/**
 * @author sunso520
 * @Title:AbstractCrawlerEnginer
 * @Description:爬虫引擎抽象类
 * @Created on 2023/10/12 11:11
 */
public abstract class AbstractCrawlerEnginer extends Thread implements CrawlerEnginer {

    protected CrawlerContext context;

    public void run() {
        setHttpRequest2Task();
        initCrawlerSpider();
        doRun();
    }

    protected abstract boolean doRun();

    @Override
    public CrawlerEnginer startCrawler() {
        super.start();
        return this;
    }

    @Override
    public void setCrawlerContext(CrawlerContext context) {
        this.context = context;
    }

    @Override
    public CrawlerContext getCrawlerContext() {
        return context;
    }

    @SneakyThrows
    protected CrawlerSpider newCrawlerSpider() {
        Constructor constructor = context.getSpiderClassType().getConstructor(CrawlerContext.class);
        return (CrawlerSpider)constructor.newInstance(this.getCrawlerContext());
    }

    protected void setHttpRequest2Task() {
        for(CrawlerHttpRequest request: context.getRequestList()) {
            context.fetchTask().offerTask(request);
        }
    }

    protected void initCrawlerSpider() {
        Thread thread = new Thread(newCrawlerSpider(), "spider-boot");
        thread.start();
    }
}
