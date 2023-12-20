package org.sunso.mini.crawler.handler;

import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:ConsoleCrawlerHandler
 * @Description: 爬虫结果控制台输出处理器<br>
 * @Created on 2023/10/12 11:17
 */
public class ConsoleCrawlerHandler implements CrawlerHandler<CrawlerResult> {

	@Override
	public void handle(CrawlerResult crawlerResult) {
		if (crawlerResult == null) {
			System.out.println("CrawlerResult is null");
			return;
		}
		System.out.println("print CrawlerResult:" + crawlerResult);
	}

}
