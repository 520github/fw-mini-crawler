package org.sunso.mini.crawler.demo.page.dslandprice.handler;

import org.sunso.mini.crawler.demo.page.dslandprice.result.ProvinceCrawlerResult;
import org.sunso.mini.crawler.handler.CrawlerHandler;

/**
 * @author sunso520
 * @Title:ProvinceCrawlerResultHandler
 * @Description: <br>
 * @Created on 2023/12/20 15:28
 */
public class ProvinceCrawlerResultHandler implements CrawlerHandler<ProvinceCrawlerResult> {

	@Override
	public void handle(ProvinceCrawlerResult crawlerResult) {
		System.out.println("ProvinceCrawlerResult: " + crawlerResult);
	}

}
