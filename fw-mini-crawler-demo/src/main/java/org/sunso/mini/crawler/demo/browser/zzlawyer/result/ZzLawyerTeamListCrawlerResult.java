package org.sunso.mini.crawler.demo.browser.zzlawyer.result;

import lombok.Data;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlPageSingleButton;
import org.sunso.mini.crawler.annotation.html.HtmlText;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.downloader.SeleniumCrawlerDownloader;
import org.sunso.mini.crawler.handler.ConsoleCrawlerHandler;

import java.util.List;

/**
 * @author sunso520
 * @Title:ZzLawyerTeamListCrawlerResult
 * @Description: <br>
 * @Created on 2023/12/20 16:02
 */
@Data
@CrawlerResultDefine(handlers = { ConsoleCrawlerHandler.class }, downloader = SeleniumCrawlerDownloader.class)
public class ZzLawyerTeamListCrawlerResult implements CrawlerResult {

	@HtmlText
	@HtmlCssPath("div.portrait-dd-title-text")
	private List<String> nameList;

	@HtmlCssPath(".teams-list>div")
	@HtmlPageSingleButton(url = "http://www.zhongzhenglawyer.com/teams", cssSelector = "a.teams-footer-button",
			singlePageAppendData = true, waitTime = 3000)
	private List<ZzLawyerTeamCrawlerResult> teamList;

}
