package org.sunso.mini.crawler.parser.html;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.expression.ExpressionUtil;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHtmlFieldParser implements HtmlFieldParser {
    protected String url;
    protected String htmlContent;

    protected AbstractHtmlFieldParser(String url, String htmlContent) {
        this.url = url;
        this.htmlContent = htmlContent;
    }

    protected String getCssPath(Field field) {
        HtmlCssPath cssPath = field.getAnnotation(HtmlCssPath.class);
        if (cssPath == null) {
            throw new IllegalArgumentException(String.format("not found @HtmlCssPath in field[%s]", field.getName()));
        }
        return cssPath.value();
    }

    protected boolean evalExpression(String expression, String key, String value) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(key, value);
        return evalExpression(expression, dataMap);
    }
    protected boolean evalExpression(String expression, Map<String, Object> dataMap) {
        if (StrUtil.isBlank(expression)) {
            return true;
        }
        Object result = ExpressionUtil.eval(expression, dataMap);
        if (result instanceof Boolean) {
            return (Boolean) result;
        }
        return true;
    }
}
