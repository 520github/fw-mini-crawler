package org.sunso.mini.crawler.enginer.builder;

import lombok.Data;
import org.junit.Test;
import org.sunso.mini.crawler.annotation.html.HtmlAttr;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlText;
import org.sunso.mini.crawler.annotation.html.HtmlUrl;
import org.sunso.mini.crawler.annotation.request.*;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.enginer.BaseTest;
import org.sunso.mini.crawler.handler.ConsoleCrawlerHandler;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CrawlerEnginerBuilderTest extends BaseTest {

	@Test
	public void CrawlerEnginerBuilderTest() {
		String url = "http://www.zhongzhenglawyer.com/Content/2232432.html";
		CrawlerEnginerBuilder.create()
				.request(CrawlerHttpRequestBuilder.get(url).addParameter("name", "你是我的家").addParameter("age", "23"))
				.urlCrawlerResult(url, ArticleList.class).defaultSingleCrawlerEnginer().startCrawler();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		try {
			countDownLatch.await();
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Data
	@CrawlerResultDefine(handlers = ConsoleCrawlerHandler.class)
	class ArticleList implements CrawlerResult {

		@Response
		private CrawlerHttpResponse response;

		@ResponseStatus
		private String responseStatus;

		// @ResponseBody
		private String responseBody;

		@HtmlText
		@HtmlCssPath("div.ModuleNewsListGiant div.BodyCenter li")
		private List<ArticleListOne> articleList;

	}

	@Data
	class ArticleListOne implements CrawlerResult {

		@Response
		private CrawlerHttpResponse response;

		@Request
		private CrawlerHttpRequest request;

		// @ResponseBody
		private String responseBody;

		@ResponseStatus
		private String responseStatus;

		@RequestParameter
		private String name;

		@RequestParameter
		private Integer age;

		@HtmlUrl(value = "href")
		@HtmlCssPath("a")
		private String detailUrl;

		@HtmlAttr("data-src")
		@HtmlCssPath("img")
		private String img;

		@HtmlAttr("title")
		@HtmlCssPath("img")
		private String title;

		@HtmlText
		@HtmlCssPath("time")
		private String time;

	}

}
