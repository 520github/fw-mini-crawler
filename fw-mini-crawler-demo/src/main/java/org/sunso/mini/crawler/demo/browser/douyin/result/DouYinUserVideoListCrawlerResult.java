package org.sunso.mini.crawler.demo.browser.douyin.result;

import lombok.Data;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.downloader.SeleniumCrawlerDownloader;
import org.sunso.mini.crawler.handler.ConsoleCrawlerHandler;

import java.util.List;

/**
 * @author sunso520
 * @Title:DouYinUserVideoListCrawlerResult
 * @Description: 抖音用户对应的视频列表<br>
 * @Created on 2023/12/20 15:48
 */
@Data
@CrawlerResultDefine(handlers = { ConsoleCrawlerHandler.class }, downloader = SeleniumCrawlerDownloader.class)
public class DouYinUserVideoListCrawlerResult implements CrawlerResult {

	@HtmlCssPath("div.UFuuTZ1P > ul > li")
	private List<DouYinUserVideoCrawlerResult> videoList;

}
