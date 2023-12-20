package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.annotation.request.ResponseStatus;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

/**
 * @author sunso520
 * @Title:CrawlerResponseStatusFieldParser
 * @Description: 字段为响应结果状态码的解析处理<br>
 * @Created on 2023/10/16 10:03
 */
public class CrawlerResponseStatusFieldParser extends CrawlerResponseFieldParser {

	@Override
	public Object parseField(CrawlerFieldParserRequest request) {
		return getResponseStatus(request);
	}

	private Integer getResponseStatus(CrawlerFieldParserRequest request) {
		ResponseStatus responseStatus = request.fetchFieldAnnotation(ResponseStatus.class);
		CrawlerHttpResponse response = getResponseData(responseStatus.parentLevel(), request);
		if (response == null) {
			return null;
		}
		return response.status();
	}

}
