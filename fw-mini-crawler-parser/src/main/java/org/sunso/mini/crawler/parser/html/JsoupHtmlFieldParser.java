package org.sunso.mini.crawler.parser.html;

import cn.hutool.core.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sunso.mini.crawler.annotation.html.*;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.parser.type.TypeConverters;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsoupHtmlFieldParser extends AbstractHtmlFieldParser {

    private static String attrKeyFlag = "attr.";
    private Document document;

    public JsoupHtmlFieldParser(String url, String htmlContent) {
        super(url, htmlContent);
        if (StrUtil.isNotBlank(url)) {
            this.document = Jsoup.parse(htmlContent, url);
        }
        else {
            this.document = Jsoup.parse(htmlContent);
        }
    }


    @Override
    public List<String> selectorHtmlList(Field field) {
        List<String> htmlList= new ArrayList<>();
        Elements elements = elementList(field);
        for(Element element: elements) {
            htmlList.add(valueHtml(element, true));
        }
        return htmlList;
    }

    @Override
    public String selectorHtml(Field field) {
        return valueHtml(field);
    }

    @Override
    public Object selectorObject(Field field) {
        if (field.isAnnotationPresent(HtmlMap.class)) {
            return valueMap(field);
        }
        String result = elementOneValue(elementOne(field), field);
        return convertValue(result, field);
    }

    @Override
    public List<Object> selectorObjectList(Field field) {
        List<Object> resultList = new ArrayList<>();
        Elements elements = elementList(field);
        for(Element element: elements) {
            resultList.add(convertValue(elementOneValue(element, field), field));
        }
        return resultList;
    }

    private Object convertValue(String result, Field field) {
        //return TypeConverters.getValue(field.getType(), result, null);
        return result;
    }

    @Override
    public List<? extends CrawlerResult> selectorCrawlerResultList(Field field) {
        return null;
    }

    private Elements elementList(Field field) {
        return elementList(getCssPath(field));
    }

    public Elements elementList(String selector) {
        return document.select(selector);
    }

    private Element elementOne(Field field) {
        return elementOne(getCssPath(field));
    }

    private String elementOneValue(Element element, Field field) {
        String result = null;
        if (field.isAnnotationPresent(HtmlText.class)) {
            result = valueText(element, field);
        }
        else if (field.isAnnotationPresent(HtmlHtml.class)) {
            result = valueHtml(element, field);
        }
        else if (field.isAnnotationPresent(HtmlAttr.class)) {
            result = valueAttr(element, field);
        }
        else if (field.isAnnotationPresent(HtmlImage.class)) {
            result = valueImage(element, field);
        }
        else if (field.isAnnotationPresent(HtmlUrl.class)) {
            result = valueUrl(element, field);
        }
        else {
            result = valueText(element, false);
        }
        return result;
    }

    public Element elementOne(String selector) {
        Elements elementList = elementList(selector);
        if (elementList != null && elementList.size() > 0) {
            return elementList.first();
        }
        return null;
    }

    public Map<String,String> valueMap(Field field) {
        Map<String,String> resultMap = new HashMap<>();
        Elements elements = elementList(field);
        for(Element element: elements) {
            valueMap(element, field, resultMap);
        }
        return resultMap;
    }

    private Map<String, String> valueMap(Element element, Field field, Map<String,String> resultMap) {
        HtmlMap htmlMap = field.getAnnotation(HtmlMap.class);
        String keyValue = getValueByKeyTypes(element, htmlMap.key());
        if (StrUtil.isBlank(keyValue)) {
            return resultMap;
        }
        keyValue = keyValue.trim();
        if (!evalExpression(htmlMap.keyExpressionFilter(), "key", keyValue)) {
            return resultMap;
        }
        if (!resultMap.containsKey(keyValue)) {
            String valueValue = getValueByKeyTypes(element, htmlMap.value());
            if (StrUtil.isBlank(valueValue)) {
                valueValue = "not found any value";
            }
            if (!evalExpression(htmlMap.valueExpressionFilter(), "value", valueValue)) {
                return resultMap;
            }
            resultMap.put(keyValue, valueValue);
        }
        return resultMap;
    }

    private String getValueByKeyTypes(Element element, String keys[]) {
        if (keys == null || keys.length < 1) {
            return null;
        }
        String result = null;
        for(String key: keys) {
            result = getValueByKeyType(element,  key);
            if (StrUtil.isNotBlank(result)) {
                return result;
            }
        }
        return result;
    }
    private String getValueByKeyType(Element element, String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        if (key.startsWith(attrKeyFlag)) {
            return valueAttr(element, key.substring(attrKeyFlag.length()));
        }
        else if ("text".equals(key)) {
            return valueText(element, false);
        }
        return null;
    }

    private String valueHtml(Field field) {
        return valueHtml(elementOne(field), false);
    }

    private String valueHtml(Element element, Field field) {
        HtmlHtml htmlHtml = field.getAnnotation(HtmlHtml.class);
        return valueHtml(element, htmlHtml.isOwn());
    }

    private String valueHtml(Element element, boolean isOuter) {
        if (element == null) {
            return null;
        }
        if (isOuter) {
            return element.outerHtml();
        }
        return element.html();
    }

    private String valueText(Element element, Field field) {
        HtmlText htmlText = field.getAnnotation(HtmlText.class);
        return valueText(element, htmlText.isOwn());
    }

    private String valueText(Element element, boolean isOwn) {
        //Element element = elementOne(selector);
        if (element == null) {
            return null;
        }
        //获取标签自身的文本
        if (isOwn) {
            return element.ownText();
        }
        return element.text();
    }

    private String valueAttr(Element element, Field field) {
        HtmlAttr htmlAttr = field.getAnnotation(HtmlAttr.class);
        return valueAttr(element, htmlAttr.value());
    }

    private String valueAttr(Element element, String attrKey) {
        if (element == null) {
            return null;
        }
        if ("href".equals(attrKey)) {
            return valueAbsUrl(element, attrKey);
        }
        return element.attr(attrKey);
    }

    private String valueImage(Element element, Field field) {
        HtmlImage htmlImage = field.getAnnotation(HtmlImage.class);
        return valueAbsUrl(element, "src", htmlImage.value());
    }

    private String valueUrl(Element element, Field field) {
        HtmlUrl htmlUrl = field.getAnnotation(HtmlUrl.class);
        return valueAbsUrl(element, "href", htmlUrl.value());
    }

    private String valueAbsUrl(Element element, String defaultAttrKey, String... attrKeys) {
        for(String attrKey: attrKeys) {
            String result = valueAbsUrl(element, attrKey);
            if (StrUtil.isNotBlank(result)) {
                return result;
            }
        }
        return valueAbsUrl(element, defaultAttrKey);
    }

    private String valueAbsUrl(Element element, String attrKey) {
        if (element == null) {
            return null;
        }
        String result = element.absUrl(attrKey);
        if (StrUtil.isBlank(result)) {
            result = element.attr(attrKey);
        }
        return result;
    }


}
