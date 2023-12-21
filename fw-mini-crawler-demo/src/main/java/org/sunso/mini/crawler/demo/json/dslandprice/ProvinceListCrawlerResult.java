package org.sunso.mini.crawler.demo.json.dslandprice;

import lombok.Data;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import java.util.List;

/**
 * @author sunso520
 * @Title:ProvinceListCrawlerResult
 * @Description: <br>
 * @Created on 2023/12/20 15:25
 */
@Data
public class ProvinceListCrawlerResult implements CrawlerResult {

	@JsonPath("result")
	private List<ProvinceCrawlerResult> list;

}
