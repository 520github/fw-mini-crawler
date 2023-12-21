package org.sunso.mini.crawler.demo.json.dslandprice;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.enginer.builder.CrawlerEnginerBuilder;

/**
 * @author sunso520
 * @Title:DsLandPriceCrawler
 * @Description: <br>
 * @Created on 2023/12/20 15:23
 */
public class DsLandPriceCrawler {

	public static void main(String[] args) {
		String url = "http://gsgk.mnr.gov.cn/wQpjApi/dict/getDepByPidCas";

		CrawlerEnginerBuilder.create().request(CrawlerHttpRequestBuilder.get(url))
				.urlCrawlerResult(url, ProvinceListCrawlerResult.class) // 省份列表
				.buildOfSingleCrawlerEnginer().startCrawler();
	}

}
