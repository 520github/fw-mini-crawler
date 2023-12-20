package org.sunso.mini.crawler.enginer;

/**
 * @author sunso520
 * @Title:SingleCrawlerEnginer
 * @Description:简单单一的爬虫引擎
 * @Created on 2023/10/12 11:11
 */
public class SingleCrawlerEnginer extends AbstractCrawlerEnginer {

	@Override
	protected boolean doRun() {
		return false;
	}

	@Override
	public CrawlerEnginer startCrawler() {
		return super.startCrawler();
	}

}
