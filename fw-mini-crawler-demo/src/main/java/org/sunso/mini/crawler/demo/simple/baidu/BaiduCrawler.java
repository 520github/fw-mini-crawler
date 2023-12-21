package org.sunso.mini.crawler.demo.simple.baidu;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.enginer.builder.CrawlerEnginerBuilder;

public class BaiduCrawler {

	public static void main(String[] args) {
		String url = "https://www.baidu.com/";
		CrawlerEnginerBuilder.create().request(CrawlerHttpRequestBuilder.get(url))
				.urlCrawlerResult(url, BaiduCrawlerResult.class).buildOfSingleCrawlerEnginer().startCrawler();
		System.out.println("baidu  crawler finish!");
	}

}