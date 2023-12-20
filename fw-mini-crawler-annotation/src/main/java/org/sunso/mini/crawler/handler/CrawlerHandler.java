package org.sunso.mini.crawler.handler;

import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:CrawlerHandler
 * @Description: 爬虫处理器 处理爬虫最终得到的业务结果CrawlerResult,可以定义多个CrawlerHandler对CrawlerResult进行处理
 * @Created on 2023/10/12 11:05
 */
public interface CrawlerHandler<B extends CrawlerResult> {

	/**
	 *
	 * 处理爬虫最终得到的业务结果CrawlerResult,可以定义多个CrawlerHandler对CrawlerResult进行处理
	 * @param crawlerResult 爬虫最终得到的业务结果
	 */
	void handle(B crawlerResult);

}
