package org.sunso.mini.crawler.parser;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.reflections.ReflectionUtils;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.order.FieldOrder;
import org.sunso.mini.crawler.annotation.request.RequestAttributeGet;
import org.sunso.mini.crawler.annotation.request.RequestAttributeSet;
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
import java.util.*;

public class UnionCrawlerParser extends AbstractCrawlerParser {
    @Override
    public CrawlerResult parse(Class<? extends CrawlerResult> clazz, CrawlerHttpRequest request, CrawlerHttpResponse response) {
        Set<Field> fieldSet =  ReflectionUtils.getAllFields(clazz);
        Map<String, Object> dataMap = new HashMap<>();
        CrawlerFieldParserRequest parserRequest = CrawlerFieldParserRequest.newInstance(request, response, this);
        //parserRequest.setBeanDataMap(dataMap);
        Map<Integer, List<Field>> orderFieldMap = new TreeMap<>();
        for(Field field: fieldSet) {
            if (field.isAnnotationPresent(FieldOrder.class)) {
                setFiled2OrderFieldList(field, orderFieldMap);
                continue;
            }
            Object fieldValue = handleField(field, parserRequest);

            dataMap.put(field.getName(), fieldValue);
        }
        for(Integer sort: orderFieldMap.keySet()) {
            for(Field field: orderFieldMap.get(sort)) {
                Object fieldValue = handleField(field, parserRequest);

                dataMap.put(field.getName(), fieldValue);
            }
        }
        CrawlerResult result = BeanUtil.mapToBean(dataMap, clazz, true);
        CrawlerHandlerFactory.doCrawlerHandler(result);
        return result;
    }

    private void setFiled2OrderFieldList(Field field, Map<Integer, List<Field>> orderFieldMap) {
        FieldOrder fieldOrder = field.getAnnotation(FieldOrder.class);
        List<Field> fieldList = orderFieldMap.get(fieldOrder.sort());
        if (fieldList == null) {
            fieldList = new ArrayList<>();
        }
        fieldList.add(field);
        orderFieldMap.put(fieldOrder.sort(), fieldList);
    }

    private Object handleField(Field field, CrawlerFieldParserRequest parserRequest) {
        parserRequest.setField(field);
        CrawlerFieldParser crawlerFieldParser = CrawlerFieldParserFactory.getCrawlerFieldParser(field);
        if (crawlerFieldParser == null) {
            System.out.println("UnionCrawlerParser find crawlerFieldParser is null by field:" + field.getName());
            //TODO 返回特殊对象不做处理
            return null;
        }
        Object fieldValue = crawlerFieldParser.parseField(parserRequest);
        //过滤

        //格式化处理
        fieldValue = formatFieldValue(field, fieldValue);
        //类型转化处理
        fieldValue = TypeConverters.getValue(field.getType(), fieldValue, null);
        //设置request上下文属性
        setRequestAttribute(field, fieldValue, parserRequest);
        return fieldValue;
    }

    private void setRequestAttribute(Field field, Object fieldValue, CrawlerFieldParserRequest parserRequest) {
        if (field.isAnnotationPresent(RequestAttributeSet.class)) {
            String name = field.getAnnotation(RequestAttributeSet.class).value();
            if (StrUtil.isBlank(name)) {
                name = field.getName();
            }
            parserRequest.commitRequestAttribute(name, fieldValue);
        }
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
        else if (field.isAnnotationPresent(RequestAttributeGet.class)) {
            return field.getAnnotation(RequestAttributeGet.class).formatter();
        }
        return null;
    }

}
