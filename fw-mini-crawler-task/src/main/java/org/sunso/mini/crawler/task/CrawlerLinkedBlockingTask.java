package org.sunso.mini.crawler.task;

import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sunso520
 * @Title:CrawlerLinkedBlockingTask
 * @Description: 本地缓存方式的爬虫任务<br>
 * @Created on 2023/11/1 17:14
 */
@Slf4j
public class CrawlerLinkedBlockingTask extends AbstractCrawlerTask {

	private LinkedBlockingQueue<CrawlerHttpRequest> queue;

	public CrawlerLinkedBlockingTask() {
		queue = new LinkedBlockingQueue<>();
	}

	@Override
	public void offerTask(CrawlerHttpRequest request) {
		queue.offer(request);
	}

	@Override
	public CrawlerHttpRequest pollTask() {
		return queue.poll();
	}

	@Override
	public void doneTask(CrawlerHttpRequest request, CrawlerHttpResponse response, CrawlerResult crawlerResult) {
		log.info("doneTask url[%s], responseStatus[%d]", request.getUrl(), response.status());
	}

}
