package org.sunso.mini.crawler.storage.data;

import org.sunso.mini.crawler.common.result.CrawlerResult;

public interface CrawlerDataStorage<B extends CrawlerResult> {

    boolean storage(B crawlerResult);
}
