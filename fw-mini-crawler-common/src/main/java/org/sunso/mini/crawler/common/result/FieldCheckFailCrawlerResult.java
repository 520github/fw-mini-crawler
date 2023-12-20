package org.sunso.mini.crawler.common.result;

import lombok.Data;

/**
 * @author sunso520
 * @Title:FieldCheckFailCrawlerResult
 * @Description: 字段检查失败的爬虫结果<br>
 * @Created on 2023/11/14 15:42
 */
@Data
public class FieldCheckFailCrawlerResult implements CrawlerResult {

	private String message = "field check fail";

	private String fieldName;

	public FieldCheckFailCrawlerResult(String fieldName) {
		this.fieldName = fieldName;
	}

	public static FieldCheckFailCrawlerResult newInstance(String fieldName) {
		return new FieldCheckFailCrawlerResult(fieldName);
	}

}
