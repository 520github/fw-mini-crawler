package org.sunso.mini.crawler.parser.field;

public class CrawlerRequestFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        return request.getRequest();
    }
}
