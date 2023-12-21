package org.sunso.mini.crawler.demo.browser.zzlawyer.handler;

import org.sunso.mini.crawler.demo.browser.zzlawyer.result.ZzLawyerTeamCrawlerResult;
import org.sunso.mini.crawler.handler.CrawlerHandler;

/**
 * @author sunso520
 * @Title:ZzLawyerTeamCrawlerResultHandler
 * @Description: <br>
 * @Created on 2023/12/20 16:07
 */
public class ZzLawyerTeamCrawlerResultHandler implements CrawlerHandler<ZzLawyerTeamCrawlerResult> {

	@Override
	public void handle(ZzLawyerTeamCrawlerResult crawlerResult) {
		System.out.println("ZzLawyerTeamCrawlerResult:" + crawlerResult);
	}

}
