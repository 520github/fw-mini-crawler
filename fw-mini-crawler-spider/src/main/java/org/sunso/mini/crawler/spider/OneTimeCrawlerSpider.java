package org.sunso.mini.crawler.spider;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.context.CrawlerContext;
import org.sunso.mini.crawler.context.CrawlerContextThreadLocal;

/**
 * @author sunso520
 * @Title:OneTimeCrawlerSpider
 * @Description: 单线程一次性爬虫执行器
 * @Created on 2023/10/12 11:05
 */
@Slf4j
public class OneTimeCrawlerSpider extends AbstractCrawlerSpider {

	public OneTimeCrawlerSpider(CrawlerContext context) {
		super(context);
	}

	@SneakyThrows
	@Override
	public void run() {
		CrawlerContextThreadLocal.set(this.context);
		while (true) {
			CrawlerHttpRequest request = getRequestFromCrawlerTask();
			if (request == null) {
				log.info("执行器[OneTimeCrawlerSpider]没有发现可执行的CrawlerHttpRequest，已退出服务");
				break;
			}
			doRequest(request);
		}
	}

}
