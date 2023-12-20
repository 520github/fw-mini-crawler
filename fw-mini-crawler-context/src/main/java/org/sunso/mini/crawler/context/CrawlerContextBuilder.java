package org.sunso.mini.crawler.context;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.spider.CrawlerSpider;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.parser.CrawlerParser;
import org.sunso.mini.crawler.task.CrawlerTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sunso520
 * @Title:CrawlerContext
 * @Description: 爬虫上下文构造器
 * @Created on 2023/10/12 10:13
 */
public class CrawlerContextBuilder {

	// 业务类型
	private String bizType;

	// 爬虫请求列表
	private List<CrawlerHttpRequest> requestList = new ArrayList<>();

	// 爬虫任务
	private CrawlerTask task;

	// 爬虫下载器
	private CrawlerDownloader downloader;

	// 爬虫解析器
	private CrawlerParser parser;

	// 爬虫CrawlerSpider执行器
	private Class<? extends CrawlerSpider> spiderClassType;

	// 爬虫请求与CrawlerResult映射关系
	private Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap;

	// 爬虫线程数
	private int spiderNum;

	private CrawlerContextBuilder() {

	}

	public static CrawlerContextBuilder create() {
		return new CrawlerContextBuilder();
	}

	public CrawlerContextBuilder bizType(String bizType) {
		this.bizType = bizType;
		return this;
	}

	public CrawlerContextBuilder request(CrawlerHttpRequest request) {
		requestList.add(request);
		return this;
	}

	public CrawlerContextBuilder requestList(List<CrawlerHttpRequest> requestList) {
		this.requestList.addAll(requestList);
		return this;
	}

	public CrawlerContextBuilder task(CrawlerTask task) {
		this.task = task;
		return this;
	}

	public CrawlerContextBuilder downloader(CrawlerDownloader downloader) {
		this.downloader = downloader;
		return this;
	}

	public CrawlerContextBuilder parser(CrawlerParser parser) {
		this.parser = parser;
		return this;
	}

	public CrawlerContextBuilder spiderClassType(Class<? extends CrawlerSpider> spiderClassType) {
		this.spiderClassType = spiderClassType;
		return this;
	}

	public CrawlerContextBuilder urlCrawlerResultMap(Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap) {
		this.urlCrawlerResultMap = urlCrawlerResultMap;
		return this;
	}

	public CrawlerContextBuilder spiderNum(int spiderNum) {
		this.spiderNum = spiderNum;
		return this;
	}

	public CrawlerContext build() {
		if (requestList.isEmpty()) {
			throw new IllegalArgumentException("requestList parameter is must");
		}
		CrawlerContext context = new CrawlerContext();
		context.setBizType(bizType);
		context.setTask(task);
		context.setDownloader(downloader);
		context.setParser(parser);
		context.setRequestList(requestList);
		context.setSpiderClassType(spiderClassType);
		context.setUrlCrawlerResultMap(urlCrawlerResultMap);
		context.setSpiderNum(spiderNum);
		return context;
	}

}
