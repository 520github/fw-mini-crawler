package org.sunso.mini.crawler.handler;

import org.sunso.mini.crawler.common.result.CrawlerResult;

public interface CrawlerHandler<B extends CrawlerResult> {
    void handle(B crawlerResult);
}
