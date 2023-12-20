package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.annotation.request.ResponseBody;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

/**
 * @author sunso520
 * @Title:CrawlerResponseBodyFieldParser
 * @Description: 字段为响应结果数据的解析处理<br>
 * @Created on 2023/10/16 10:06
 */
public class CrawlerResponseBodyFieldParser extends CrawlerResponseFieldParser {

	@Override
	public Object parseField(CrawlerFieldParserRequest request) {
		return getResponseBody(request);
	}

	private String getResponseBody(CrawlerFieldParserRequest request) {
		ResponseBody responseBody = request.fetchFieldAnnotation(ResponseBody.class);
		CrawlerHttpResponse response = getResponseData(responseBody.parentLevel(), request);
		if (response == null) {
			return null;
		}
		return response.body();
	}

}
