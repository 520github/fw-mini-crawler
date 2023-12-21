package org.sunso.mini.crawler.demo.browser.zzlawyer.result;

import lombok.Data;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlImage;
import org.sunso.mini.crawler.annotation.html.HtmlText;
import org.sunso.mini.crawler.annotation.html.HtmlUrl;
import org.sunso.mini.crawler.annotation.request.RequestAttributeSet;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.demo.browser.zzlawyer.handler.ZzLawyerTeamCrawlerResultHandler;

/**
 * @author sunso520
 * @Title:ZzLawyerTeamCrawlerResult
 * @Description: <br>
 * @Created on 2023/12/20 16:02
 */
@Data
@CrawlerResultDefine(handlers = { ZzLawyerTeamCrawlerResultHandler.class })
public class ZzLawyerTeamCrawlerResult implements CrawlerResult {

	@HtmlUrl(triggerClick = false, urlAlias = "teamDetail", copyAttribute = true, waitTime = 3 * 1000)
	@RequestAttributeSet()
	@HtmlCssPath("a.teams-list-li")
	private String detailUrl;

	@HtmlText
	@HtmlCssPath(".portrait-dd-title-text")
	private String name;

	@HtmlCssPath(".portrait-dd-title-sub")
	private String position;

	@HtmlCssPath(".portrait-dt-address-text")
	private String address;

	@HtmlImage
	@HtmlCssPath(".overlay-image-block img")
	private String imageUrl;

}
