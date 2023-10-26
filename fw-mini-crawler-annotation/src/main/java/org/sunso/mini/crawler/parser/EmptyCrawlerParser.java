package org.sunso.mini.crawler.parser;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:EmptyCrawlerParser
 * @Description: <br>
 * @Created on 2023/10/26 09:23
 */
public class EmptyCrawlerParser implements CrawlerParser {

    @Override
    public CrawlerResult parse(Class<? extends CrawlerResult> clazz, CrawlerHttpRequest request, CrawlerHttpResponse response) {
        return null;
    }
}
