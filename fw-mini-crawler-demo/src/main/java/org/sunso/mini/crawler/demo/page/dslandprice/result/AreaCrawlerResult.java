package org.sunso.mini.crawler.demo.page.dslandprice.result;

import lombok.Data;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:AreaCrawlerResult
 * @Description: 地区具体数据<br>
 * @Created on 2023/12/20 15:36
 */
@Data
public class AreaCrawlerResult implements CrawlerResult {

	@JsonPath("grade")
	private String addrGrade;

	@JsonPath("name")
	private String addrName;

	@JsonPath("parentname")
	private String addrParentname;

	@JsonPath("allname")
	private String addrMergerName;

}
