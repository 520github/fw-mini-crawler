package org.sunso.mini.crawler.demo.simple.baidu;

import org.sunso.mini.crawler.handler.CrawlerHandler;

/**
 * 针对BaiduCrawlerResult爬取结果的处理
 */
public class BaiduCrawlerResultHandler implements CrawlerHandler<BaiduCrawlerResult> {

	@Override
	public void handle(BaiduCrawlerResult crawlerResult) {
		System.out.println("crawlerResult: " + crawlerResult);
	}

}
