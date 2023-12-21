package org.sunso.mini.crawler.demo.page.dslandprice.result;

import lombok.Data;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import java.util.List;

/**
 * @author sunso520
 * @Title:ProvinceListCrawlerResult
 * @Description: 爬取省份列表数据<br>
 * @Created on 2023/12/20 15:25
 */
@Data
public class ProvinceListCrawlerResult implements CrawlerResult {

	@JsonPath("result")
	private List<ProvinceCrawlerResult> list;

}
