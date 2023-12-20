package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.annotation.request.RequestAttributeGet;
import org.sunso.mini.crawler.annotation.request.RequestParameter;

/**
 * @author sunso520
 * @Title:CrawlerRequestAttributeGetFieldParser
 * @Description: 字段为请求Attribute属性的解析处理<br>
 * @Created on 2023/10/17 10:22
 */
public class CrawlerRequestAttributeGetFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        return getRequestParameterValue(request);
    }

    private Object getRequestParameterValue(CrawlerFieldParserRequest request) {
        RequestAttributeGet parameter = request.fetchFieldAnnotation(RequestAttributeGet.class);
        String name = parameter.value();
        if (StrUtil.isBlank(name)) {
            name = request.fetchFieldName();
        }
        return request.fetchRequestAttributeValue(name);
    }
}
