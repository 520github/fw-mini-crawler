package org.sunso.mini.crawler.parser;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.reflections.ReflectionUtils;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlUrl;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.order.FieldOrder;
import org.sunso.mini.crawler.annotation.request.RequestAttributeGet;
import org.sunso.mini.crawler.annotation.request.RequestAttributeSet;
import org.sunso.mini.crawler.annotation.result.CrawlerResultChecker;
import org.sunso.mini.crawler.checker.CrawlerCheckerFactory;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.result.FieldCheckFailCrawlerResult;
import org.sunso.mini.crawler.context.CrawlerContextThreadLocal;
import org.sunso.mini.crawler.formatter.Formatter;
import org.sunso.mini.crawler.formatter.FormatterFactory;
import org.sunso.mini.crawler.handler.CrawlerHandlerFactory;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParser;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParserFactory;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParserRequest;
import org.sunso.mini.crawler.parser.type.TypeConverters;
import org.sunso.mini.crawler.storage.data.CrawlerDataStorageFactory;

import java.lang.reflect.Field;
import java.util.*;

public class UnionCrawlerParser extends AbstractCrawlerParser {
    @Override
    public CrawlerResult parse(Class<? extends CrawlerResult> clazz, CrawlerHttpRequest request, CrawlerHttpResponse response) {
        Set<Field> fieldSet =  ReflectionUtils.getAllFields(clazz);
        Map<String, Object> dataMap = new HashMap<>();
        CrawlerFieldParserRequest parserRequest = CrawlerFieldParserRequest.newInstance(request, response, this, clazz);
        //parserRequest.setBeanDataMap(dataMap);
        Map<Integer, List<Field>> orderFieldMap = new TreeMap<>();
        for(Field field: fieldSet) {
            if (field.isAnnotationPresent(FieldOrder.class)) {
                setFiled2OrderFieldList(field, orderFieldMap);
                continue;
            }
            handleField2Map(field, parserRequest, dataMap);
            if (!checker(field, dataMap, clazz)) {
                return FieldCheckFailCrawlerResult.newInstance(field.getName());
            }
        }
        for(Integer sort: orderFieldMap.keySet()) {
            for(Field field: orderFieldMap.get(sort)) {
                handleField2Map(field, parserRequest, dataMap);
                if (!checker(field, dataMap, clazz)) {
                    return FieldCheckFailCrawlerResult.newInstance(field.getName());
                }
            }
        }
        CrawlerResult result = BeanUtil.mapToBean(dataMap, clazz, true);
        CrawlerHandlerFactory.doCrawlerHandler(result);
        CrawlerDataStorageFactory.doDataStorage(result);
        return result;
    }

    private boolean checker(Field field, Map<String, Object> dataMap, Class<? extends CrawlerResult> clazz) {
        CrawlerResultChecker checker = field.getAnnotation(CrawlerResultChecker.class);
        if (checker == null) {
            return true;
        }
        return CrawlerCheckerFactory.getCrawlerChecker(checker.checker()).check(BeanUtil.mapToBean(dataMap, clazz, true));
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

    private void handleField2Map(Field field, CrawlerFieldParserRequest parserRequest, Map<String, Object> dataMap) {
        Object fieldValue = handleField(field, parserRequest);
        if (fieldValue == null) {
            return;
        }
        if (field.isAnnotationPresent(HtmlUrl.class)) {
            handleHtmlUrlTriggerClick(field, fieldValue, parserRequest);
        }
        dataMap.put(field.getName(), fieldValue);
    }

    private void handleHtmlUrlTriggerClick(Field field, Object fieldValue, CrawlerFieldParserRequest parserRequest) {
        HtmlUrl htmlUrl = field.getAnnotation(HtmlUrl.class);
        if (!htmlUrl.triggerClick()) {
            return;
        }
        if (fieldValue instanceof List) {
            for(String subUrl: (List<String>)fieldValue) {
                handleHtmlUrlTriggerClick(htmlUrl, subUrl, parserRequest);
            }
        }
        else {
            handleHtmlUrlTriggerClick(htmlUrl, fieldValue.toString(), parserRequest);
        }
    }

    private void handleHtmlUrlTriggerClick(HtmlUrl htmlUrl, String subUrl, CrawlerFieldParserRequest parserRequest) {
        CrawlerHttpRequest subRequest = parserRequest.subRequest(htmlUrl, subUrl);
        CrawlerContextThreadLocal.offerRequest(subRequest);
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
