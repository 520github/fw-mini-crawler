package org.sunso.mini.crawler.context;

import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;

/**
 * @author sunso520
 * @Title:CrawlerContextThreadLocal
 * @Description: 爬虫本地线程上下文
 * @Created on 2023/10/17 10:13
 */
@Slf4j
public class CrawlerContextThreadLocal {

	private static ThreadLocal<CrawlerContext> crawlerContextThreadLocal = new ThreadLocal<>();

	public static void set(CrawlerContext context) {
		crawlerContextThreadLocal.set(context);
	}

	public static CrawlerContext get() {
		return crawlerContextThreadLocal.get();
	}

	public static void offerRequest(CrawlerHttpRequest request) {
		CrawlerContext context = get();
		if (context == null) {
			log.warn("CrawlerContextThreadLocal offerRequest[{}] to Thread[{}] find CrawlerContext is null", request,
					Thread.currentThread().getName());
			return;
		}
		get().fetchTask().offerTask(request);
	}

}
