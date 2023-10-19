package org.sunso.mini.crawler.storage.data;

import org.sunso.mini.crawler.common.result.CrawlerResult;

public class EmptyCrawlerDataStorage implements CrawlerDataStorage {
    @Override
    public boolean storage(CrawlerResult crawlerResult) {
        return true;
    }
}
