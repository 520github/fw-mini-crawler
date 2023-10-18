package org.sunso.mini.crawler.storage.file;

import org.sunso.mini.crawler.storage.CrawlerFileStorageRequest;

public interface CrawlerFileStorage {
    String storage(CrawlerFileStorageRequest request);
}
