package org.sunso.mini.crawler.storage.file;

import org.sunso.mini.crawler.storage.CrawlerFileStorageRequest;

public class NoneCrawlerFileStorage implements CrawlerFileStorage {

	@Override
	public String storage(CrawlerFileStorageRequest request) {
		return null;
	}

}
