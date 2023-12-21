package org.sunso.mini.crawler.demo.browser.douyin;

import org.sunso.mini.crawler.common.http.option.Option;
import org.sunso.mini.crawler.common.http.option.OptionFactory;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.demo.browser.douyin.result.DouYinUserVideoListCrawlerResult;
import org.sunso.mini.crawler.enginer.builder.CrawlerEnginerBuilder;

/**
 * @author sunso520
 * @Title:DouYinCrawler
 * @Description: 爬取抖音视频数据,需要本地浏览器支持，默认使用chrome浏览器<br>
 * @Created on 2023/12/20 15:47
 */
public class DouYinCrawler {

	public static void main(String[] args) {
		String url = "https://www.douyin.com/user/MS4wLjABAAAAeVoCPC2GsmiAqMAJVqGAhE0bIxx7h_Are-76huHeoKjWvqCnzqhvtLZ7SZFKjzDC?relation=0&vid=7291963578511297807";
		CrawlerEnginerBuilder.create()
				.request(CrawlerHttpRequestBuilder.get(url).setWaitTime(50 * 1000)
						.setOption(OptionFactory.getDefault().setSwitchArgHeadless(false)))
				.urlCrawlerResult(url, DouYinUserVideoListCrawlerResult.class).buildOfSingleCrawlerEnginer()
				.startCrawler();
	}

}
