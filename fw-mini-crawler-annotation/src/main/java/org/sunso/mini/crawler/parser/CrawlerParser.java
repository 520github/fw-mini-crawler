package org.sunso.mini.crawler.parser;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:CrawlerParser
 * @Description: 爬虫解析器 根据下载器返回的结果CrawlerHttpResponse，以及CrawlerResult字段定义，解析字段，设置字段值
 * @Created on 2023/10/12 11:02
 */
public interface CrawlerParser {

	/**
	 * 根据下载器返回的结果CrawlerHttpResponse，以及CrawlerResult类对应的字段定义，解析字段，并设置字段值
	 *
	 * <p>
	 * CrawlerResult类例子
	 * <p/>
	 *
	 * @Data
	 * @CrawlerResultDefine(handlers = {ConsoleCrawlerHandler.class}) public class
	 * NotifyCrawlerResult implements CrawlerResult { @HtmlCssPath(".container
	 * .details_page > h2 > p")
	 * @HtmlText private String title;
	 *
	 * @HtmlHtml @HtmlCssPath("div#div_zhengwen") private String detail; }
	 * @param clazz 实现{@link org.sunso.mini.crawler.common.result.CrawlerResult}接口的类
	 * @param request 爬虫任务CrawlerHttpRequest
	 * @param response 下载CrawlerHttpRequest对应的内容
	 * @return CrawlerResult 爬虫最终解析得到的业务结果
	 */
	CrawlerResult parse(Class<? extends CrawlerResult> clazz, CrawlerHttpRequest request, CrawlerHttpResponse response);

}
