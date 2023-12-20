package org.sunso.mini.crawler.parser.field;


/**
 * @author sunso520
 * @Title:CrawlerFieldParser
 * @Description: 爬虫字段解析器接口<br>
 * @Created on 2023/10/12 11:20
 */
public interface CrawlerFieldParser {

    /**
     * 解析处理字段，最终返回字段解析值
     *
     * @param request 字段解析请求参数
     * @return 返回字段解析处理值
     */
    Object parseField(CrawlerFieldParserRequest request);
}
