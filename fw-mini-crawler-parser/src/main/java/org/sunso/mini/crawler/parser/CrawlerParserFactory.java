package org.sunso.mini.crawler.parser;

import lombok.SneakyThrows;

/**
 * @author sunso520
 * @Title:CrawlerParserFactory
 * @Description: <br>
 * @Created on 2023/10/26 10:00
 */
public class CrawlerParserFactory {

    @SneakyThrows
    public static CrawlerParser getCrawlerParser(Class<? extends CrawlerParser> clazz) {
        if (clazz == null) {
            return getDefaultCrawlerParser();
        }
        return clazz.newInstance();
    }

    public static CrawlerParser getDefaultCrawlerParser() {
        return new UnionCrawlerParser();
    }
}
