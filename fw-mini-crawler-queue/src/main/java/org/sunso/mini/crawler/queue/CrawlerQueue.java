package org.sunso.mini.crawler.queue;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;

public interface CrawlerQueue {

	void offer(CrawlerHttpRequest request);

	CrawlerHttpRequest poll();

}
