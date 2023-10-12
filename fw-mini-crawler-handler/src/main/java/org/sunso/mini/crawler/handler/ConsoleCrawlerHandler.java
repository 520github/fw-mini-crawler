package org.sunso.mini.crawler.handler;

import org.sunso.mini.crawler.common.result.CrawlerResult;

public class ConsoleCrawlerHandler implements CrawlerHandler<CrawlerResult> {
    @Override
    public void handle(CrawlerResult crawlerResult) {
        if (crawlerResult == null) {
            System.out.println("CrawlerResult is null");
            return;
        }
        System.out.println("print CrawlerResult:" + crawlerResult);
    }
}
