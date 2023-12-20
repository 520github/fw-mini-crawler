package org.sunso.mini.crawler.parser.field;

/**
 * @author sunso520
 * @Title:CrawlerRequestUrlFieldParser
 * @Description: 字段为请求url的解析处理<br>
 * @Created on 2023/10/30 15:06
 */
public class CrawlerRequestUrlFieldParser extends AbstractCrawlerFieldParser {

	@Override
	public Object parseField(CrawlerFieldParserRequest request) {
		return request.getRequest().getUrl();
	}

}
