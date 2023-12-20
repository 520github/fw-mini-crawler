package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.annotation.request.RequestParameter;


/**
 * @author sunso520
 * @Title:CrawlerRequestParameterFieldParser
 * @Description: 字段为请求参数的解析处理<br>
 * @Created on 2023/10/16 10:09
 */
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
