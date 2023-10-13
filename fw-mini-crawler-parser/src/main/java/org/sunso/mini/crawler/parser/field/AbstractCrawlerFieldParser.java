package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

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

}
