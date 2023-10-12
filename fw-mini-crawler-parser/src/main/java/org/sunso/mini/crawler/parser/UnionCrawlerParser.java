package org.sunso.mini.crawler.parser;

import cn.hutool.core.bean.BeanUtil;
import org.reflections.ReflectionUtils;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParser;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParserFactory;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParserRequest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UnionCrawlerParser extends AbstractCrawlerParser {
    @Override
    public CrawlerResult parse(Class<? extends CrawlerResult> clazz, CrawlerHttpRequest request, CrawlerHttpResponse response) {
        Set<Field> fieldSet =  ReflectionUtils.getAllFields(clazz);
        Map<String, Object> dataMap = new HashMap<>();
        CrawlerFieldParserRequest parserRequest = CrawlerFieldParserRequest.newInstance(request, response, this);
        for(Field field: fieldSet) {
            parserRequest.setField(field);
            CrawlerFieldParser crawlerFieldParser = CrawlerFieldParserFactory.getCrawlerFieldParser(field);
            if (crawlerFieldParser == null) {
                System.out.println("UnionCrawlerParser find crawlerFieldParser is null by field:" + field.getName());
                continue;
            }
            dataMap.put(field.getName(), crawlerFieldParser.parseField(parserRequest));
        }
        return BeanUtil.mapToBean(dataMap, clazz, true);
    }

}
