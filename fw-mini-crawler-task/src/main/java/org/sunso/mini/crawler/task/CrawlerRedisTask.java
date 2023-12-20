package org.sunso.mini.crawler.task;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:CrawlerRedisTask
 * @Description: redis分布式爬虫任务<br>
 * @Created on 2023/11/1 17:18
 */
public class CrawlerRedisTask extends AbstractCrawlerTask {

	@Override
	public void offerTask(CrawlerHttpRequest request) {

	}

	@Override
	public CrawlerHttpRequest pollTask() {
		return null;
	}

	@Override
	public void doneTask(CrawlerHttpRequest request, CrawlerHttpResponse response, CrawlerResult crawlerResult) {

	}

}
