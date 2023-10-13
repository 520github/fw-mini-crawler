package org.sunso.mini.crawler.parser;

import cn.hutool.core.bean.BeanUtil;
import org.reflections.ReflectionUtils;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpEmptyRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.utils.ReflectUtils;
import org.sunso.mini.crawler.parser.ajax.AjaxFieldParser;
import org.sunso.mini.crawler.parser.html.HtmlFieldParser;
import org.sunso.mini.crawler.parser.html.JsoupHtmlFieldParser;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class HtmlCrawlerParser extends AbstractCrawlerParser {

    @Override
    public CrawlerResult parse(Class<? extends CrawlerResult> clazz, CrawlerHttpRequest request, CrawlerHttpResponse response) {
        Map<String, Object> dataMap = new HashMap<>();
        HtmlFieldParser htmlFieldParser = getHtmlFieldParser(request.getUrl(), response.body());
        dataMap.putAll(parseHtml(clazz, htmlFieldParser));

        AjaxFieldParser ajaxFieldParser = getAjaxFieldParser();

        return BeanUtil.mapToBean(dataMap, clazz, true);
    }


    private Object parseAjax() {
        return null;
    }

    private Map<String, Object> parseHtml(Class<? extends CrawlerResult> clazz, HtmlFieldParser htmlFieldParser) {
        Set<Field> htmlFields = ReflectionUtils.getAllFields(clazz, ReflectionUtils.withAnnotation(HtmlCssPath.class));
        Map<String, Object> dataMap = new HashMap<>();
        for(Field field: htmlFields) {
            dataMap.put(field.getName(), getHtmlFieldValue(field, htmlFieldParser));
        }
        //return BeanUtil.mapToBean(dataMap, clazz, true);
        return dataMap;
    }

    private Object getHtmlFieldValue(Field field, HtmlFieldParser htmlFieldParser) {
        Class<?> type = field.getType();
        boolean isArray = type.isArray();
        boolean isList = containSuperType(type, List.class);
        System.out.println(field.getName() + " is list = " + isList);
        if (isList) {
            //获取泛型的类型
            Type genericType = field.getGenericType();
            Class genericClass = ReflectUtils.getGenericClass(genericType, 0);
            if (containSuperType(genericClass, CrawlerResult.class)) {
                return getHtmlFieldCrawlerResultListValue(field, htmlFieldParser, genericClass);
            }
            else {
                return htmlFieldParser.selectorObjectList(field);
            }
        }
        if (isArray) {
            Class genericClass = type.getComponentType();
            if (containSuperType(genericClass, CrawlerResult.class)) {
                return getHtmlFieldCrawlerResultListValue(field, htmlFieldParser, genericClass).toArray();
            }
            else {
                return htmlFieldParser.selectorObjectList(field).toArray();
            }
        }
        if (containSuperType(type, CrawlerResult.class)) {
            return getHtmlFieldCrawlerResultValue(field, htmlFieldParser, (Class<? extends CrawlerResult>)type);
        }

        return htmlFieldParser.selectorObject(field);
    }

    private List<CrawlerResult> getHtmlFieldCrawlerResultListValue(Field field, HtmlFieldParser htmlFieldParser, Class<? extends CrawlerResult> clazz) {
        List<String> subHtmlList = htmlFieldParser.selectorHtmlList(field);
        List<CrawlerResult> resultList = new ArrayList<>();
        //CrawlerResult[] arrayResult = new CrawlerResult[subHtmlList.size()];
        for(int i=0; i<subHtmlList.size(); i++) {
            //HtmlFieldParser subHtmlParser = getHtmlParser("", subHtmlList.get(i));
            //arrayResult[i] = parse(clazz, subHtmlParser);
            resultList.add(parse(clazz, CrawlerHttpEmptyRequest.create(), CrawlerHttpResponse.create(subHtmlList.get(i))));
        }
        return resultList;
    }

    private CrawlerResult getHtmlFieldCrawlerResultValue(Field field, HtmlFieldParser htmlFieldParser, Class<? extends CrawlerResult> clazz) {
        String subHtml = htmlFieldParser.selectorHtml(field);
        //HtmlFieldParser subHtmlParser = getHtmlParser("", subHtml);
        return parse(clazz, CrawlerHttpEmptyRequest.create(), CrawlerHttpResponse.create(subHtml));
    }

    private boolean containSuperType(Class<?> type, Class<?> superType) {
        return ReflectUtils.containSuperType(type, superType);
    }

    private HtmlFieldParser getHtmlFieldParser(String url, String content) {
        return new JsoupHtmlFieldParser(url, content);
    }

    private AjaxFieldParser getAjaxFieldParser() {
        return null;
    }
}
