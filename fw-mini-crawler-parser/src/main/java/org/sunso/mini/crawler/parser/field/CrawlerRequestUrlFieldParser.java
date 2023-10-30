package org.sunso.mini.crawler.parser.field;

public class CrawlerRequestUrlFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        return request.getRequest().getUrl();
    }
}
