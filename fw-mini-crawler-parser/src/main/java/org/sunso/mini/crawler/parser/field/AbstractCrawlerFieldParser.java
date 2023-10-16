package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.expression.ExpressionUtil;
import cn.hutool.json.JSONUtil;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCrawlerFieldParser implements CrawlerFieldParser {

    protected boolean isArray(Field field) {
        return field.getType().isArray();
    }

    protected boolean isList(Field field) {
        return containSuperType(field.getType(), List.class);
    }

    protected boolean isCrawlerResult(Field field) {
        return containSuperType(field.getType(), CrawlerResult.class);
    }

    protected boolean isCrawlerResult(Class clazz) {
        return containSuperType(clazz, CrawlerResult.class);
    }


    protected boolean containSuperType(Class<?> type, Class<?> superType) {
        return ReflectUtils.containSuperType(type, superType);
    }

    protected Class getGenericClass(Field field) {
        Type genericType = field.getGenericType();
        return ReflectUtils.getGenericClass(genericType, 0);
    }

    protected boolean checkFilter(Field field, CrawlerResult crawlerResult) {
        if (crawlerResult == null) {
            return false;
        }
        String expression = getExpression(field);
        if (StrUtil.isBlank(expression)) {
            return true;
        }
        return evalExpression(expression, JSONUtil.toBean(JSONUtil.toJsonStr(crawlerResult), Map.class));
    }

    protected List<Object> checkFilter(Field field, List<Object> list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        List<Object> resultList = new ArrayList<>();
        for(Object value: list) {
            if (checkFilter(field, value)) {
                resultList.add(value);
            }
        }
        return resultList;
    }

    protected boolean checkFilter(Field field, Object value) {
        String expression = getExpression(field);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put(field.getName(), value);
        return evalExpression(expression, dataMap);
    }

    protected String getExpression(Field field) {
        if (field.isAnnotationPresent(HtmlCssPath.class)) {
            return field.getAnnotation(HtmlCssPath.class).expressionFilter();
        }
        else if (field.isAnnotationPresent(JsonPath.class)) {
            return field.getAnnotation(JsonPath.class).expressionFilter();
        }
        return null;
    }

    protected boolean evalExpression(String expression, Map<String, Object> dataMap) {
        Object result = ExpressionUtil.eval(expression, dataMap);
        if (result instanceof Boolean) {
            return (Boolean) result;
        }
        return true;
    }

}
