package org.sunso.mini.crawler.enginer;

import org.sunso.mini.crawler.context.CrawlerContext;


public interface CrawlerEnginer {

    void setCrawlerContext(CrawlerContext context);

    CrawlerContext getCrawlerContext();


    CrawlerEnginer startCrawler();
}
