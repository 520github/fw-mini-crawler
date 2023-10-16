package org.sunso.mini.crawler.parser;

import cn.hutool.core.bean.BeanUtil;
import org.reflections.ReflectionUtils;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.formatter.Formatter;
import org.sunso.mini.crawler.formatter.FormatterFactory;
import org.sunso.mini.crawler.handler.CrawlerHandlerFactory;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParser;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParserFactory;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParserRequest;
import org.sunso.mini.crawler.parser.type.TypeConverters;

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
            Object fieldValue = crawlerFieldParser.parseField(parserRequest);
            //过滤

            //格式化处理
            fieldValue = formatFieldValue(field, fieldValue);
            //类型转化处理
            fieldValue = TypeConverters.getValue(field.getType(), fieldValue, null);
            dataMap.put(field.getName(), fieldValue);
        }
        CrawlerResult result = BeanUtil.mapToBean(dataMap, clazz, true);
        CrawlerHandlerFactory.doCrawlerHandler(result);
        return result;
    }

    private Object formatFieldValue(Field field, Object fieldValue) {
        return FormatterFactory.doFormat(getFormatClasses(field), fieldValue);
    }

    private Class<? extends Formatter>[] getFormatClasses(Field field) {
        if (field.isAnnotationPresent(HtmlCssPath.class)) {
            return field.getAnnotation(HtmlCssPath.class).formatter();
        }
        else if (field.isAnnotationPresent(JsonPath.class)) {
            return field.getAnnotation(JsonPath.class).formatter();
        }
        return null;
    }

}
