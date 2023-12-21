package org.sunso.mini.crawler.demo.browser.zzlawyer;

import org.sunso.mini.crawler.common.http.option.OptionFactory;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.demo.browser.zzlawyer.result.ZzLawyerTeamListCrawlerResult;
import org.sunso.mini.crawler.enginer.builder.CrawlerEnginerBuilder;

/**
 * @author sunso520
 * @Title:ZzLawyerCrawler
 * @Description: <br>
 * @Created on 2023/12/20 16:01
 */
public class ZzLawyerCrawler {

	public static void main(String[] args) {
		String teamUrl = "http://www.zhongzhenglawyer.com/teams";
		CrawlerEnginerBuilder.create()
				.request(CrawlerHttpRequestBuilder.get(teamUrl).setWaitTime(3000)
						.setOption(OptionFactory.getDefault().setSwitchArgHeadless(false)))
				.urlCrawlerResult(teamUrl, ZzLawyerTeamListCrawlerResult.class).defaultSingleCrawlerEnginer()
				.startCrawler();
	}

}
