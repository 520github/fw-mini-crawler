package org.sunso.mini.crawler.demo.json.dslandprice;

import lombok.Data;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.request.RequestAttributeSet;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:ProvinceCrawlerResult
 * @Description: <br>
 * @Created on 2023/12/20 15:25
 */
@Data
@CrawlerResultDefine(handlers = { ProvinceCrawlerResultHandler.class })
public class ProvinceCrawlerResult implements CrawlerResult {

	@JsonPath("parentid")
	private String parentCode;

	@JsonPath("parentname")
	private String parentName;

	@RequestAttributeSet
	@JsonPath("value")
	private String provinceCode; // 省份code

	@RequestAttributeSet
	@JsonPath("name")
	private String provinceName; // 省份名称

	@JsonPath("type")
	private String type; // 行政区划

}
