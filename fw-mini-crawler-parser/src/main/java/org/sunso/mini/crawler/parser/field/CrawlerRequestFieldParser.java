package org.sunso.mini.crawler.parser.field;

/**
 * @author sunso520
 * @Title:CrawlerRequestFieldParser
 * @Description: 字段为请求类型的解析处理<br>
 * @Created on 2023/10/16 10:10
 */
public class CrawlerRequestFieldParser extends AbstractCrawlerFieldParser {

	@Override
	public Object parseField(CrawlerFieldParserRequest request) {
		return request.getRequest();
	}

}
