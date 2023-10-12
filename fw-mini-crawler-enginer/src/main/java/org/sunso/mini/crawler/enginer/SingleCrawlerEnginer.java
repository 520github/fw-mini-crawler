package org.sunso.mini.crawler.enginer;

import org.sunso.mini.crawler.context.CrawlerContext;

public class SingleCrawlerEnginer extends AbstractCrawlerEnginer {


    @Override
    protected boolean doRun() {
        return false;
    }


    @Override
    public CrawlerEnginer startCrawler() {
        return super.startCrawler();
    }
}
