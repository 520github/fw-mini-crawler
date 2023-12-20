package org.sunso.mini.crawler.parser;

import lombok.Data;
import org.junit.Test;
import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.enums.ContentTypeEnum;
import org.sunso.mini.crawler.common.enums.HttpRequestMethodEnum;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.downloader.HutoolCrawlerDownloader;

public class UnionCrawlerParserAjaxJsonTest extends AbstractHtmlCrawlerParser {

	@Test
	public void parseAjaxJsonTest() {
		CrawlerResult result = unionCrawlerParser.parse(AjaxJsonResult.class, getEmptyRequest(), getEmptyResponse());
		print(result);
	}

	@Data
	class AjaxJsonResult implements CrawlerResult {

		@JsonPath("data.total")
		@HtmlAjax(url = "https://dev-cqb.lddstp.com/zz-official/article/info/page/list",
				method = HttpRequestMethodEnum.POST, contentType = ContentTypeEnum.applicationJson,
				downloader = HutoolCrawlerDownloader.class)
		Integer count;

		@JsonPath("data.list[2]")
		@HtmlAjax(url = "https://dev-cqb.lddstp.com/zz-official/article/info/page/list",
				method = HttpRequestMethodEnum.POST, contentType = ContentTypeEnum.applicationJson,
				downloader = HutoolCrawlerDownloader.class)
		private JsonDataListResult oneData;

		@JsonPath("data.list[2,3,4]")
		@HtmlAjax(url = "https://dev-cqb.lddstp.com/zz-official/article/info/page/list",
				method = HttpRequestMethodEnum.POST, contentType = ContentTypeEnum.applicationJson,
				downloader = HutoolCrawlerDownloader.class)
		private JsonDataListResult[] arraydata;

	}

	@Data
	class JsonDataListResult implements CrawlerResult {

		@JsonPath("id")
		private Integer id;

		@JsonPath("title")
		private String title;

		@JsonPath("coverImg")
		private String imgUrl;

		@JsonPath("viewNum")
		private Integer count;

	}

}
