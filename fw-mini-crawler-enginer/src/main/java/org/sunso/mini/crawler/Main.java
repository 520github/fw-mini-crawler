package org.sunso.mini.crawler;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.enginer.builder.CrawlerEnginerBuilder;

public class Main {

	public static void main(String[] args) {
		CrawlerEnginerBuilder.create().request(CrawlerHttpRequestBuilder.get("http://www.baidu.com"))
				.urlCrawlerResult("", null).defaultSingleCrawlerEnginer().startCrawler();
		System.out.println("Hello world!");
	}

}