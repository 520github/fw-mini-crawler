package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.annotation.request.RequestParameter;

public class CrawlerRequestParameterFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        return getRequestParameterValue(request);
    }

    private String getRequestParameterValue(CrawlerFieldParserRequest request) {
        RequestParameter parameter = request.fetchFieldAnnotation(RequestParameter.class);
        String name = parameter.value();
        if (StrUtil.isBlank(name)) {
            name = request.fetchFieldName();
        }
        return request.fetchRequestParameterValue(name);
    }
}
