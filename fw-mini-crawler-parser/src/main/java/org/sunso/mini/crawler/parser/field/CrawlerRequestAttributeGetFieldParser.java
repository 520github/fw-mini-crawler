package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.annotation.request.RequestAttributeGet;
import org.sunso.mini.crawler.annotation.request.RequestParameter;

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
