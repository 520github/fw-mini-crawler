package org.sunso.mini.crawler.demo.simple.baidu;

import lombok.Data;
import org.sunso.mini.crawler.annotation.html.HtmlAttr;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlImage;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * 百度首页爬取结果字段定义
 */
@CrawlerResultDefine(handlers = { BaiduCrawlerResultHandler.class })
@Data
public class BaiduCrawlerResult implements CrawlerResult {

	@HtmlCssPath("div#lg>img")
	@HtmlImage
	private String baiduLog;

	@HtmlCssPath("input#su")
	@HtmlAttr("value")
	private String baiduSubmitValue;

}
