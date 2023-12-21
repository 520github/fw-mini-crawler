package org.sunso.mini.crawler.demo.browser.douyin.result;

import lombok.Data;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlImage;
import org.sunso.mini.crawler.annotation.html.HtmlUrl;
import org.sunso.mini.crawler.annotation.order.FieldOrder;
import org.sunso.mini.crawler.annotation.request.RequestAttributeSet;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:DouYinUserVideoCrawlerResult
 * @Description: 抖音视频列表每个视频的相关数据<br>
 * @Created on 2023/12/20 15:49
 */
@Data
public class DouYinUserVideoCrawlerResult implements CrawlerResult {

	@RequestAttributeSet
	@HtmlCssPath("p.iQKjW6dr")
	private String title;

	@RequestAttributeSet
	@HtmlImage
	@HtmlCssPath("img")
	private String imageUrl;

	@FieldOrder
	@HtmlUrl(triggerClick = false, urlAlias = "detailUrl", copyAttribute = true)
	@HtmlCssPath("a")
	private String detailUrl;

}
