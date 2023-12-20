package org.sunso.mini.crawler.parser.html;

import cn.hutool.core.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sunso.mini.crawler.annotation.html.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunso520
 * @Title:JsoupHtmlFieldParser
 * @Description: 用于解析html的Jsoup解析器
 *
 * @Created on 2023/10/12 11:07
 */
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

    /**
     * 返回字段对应的元素列表
     * @param field 字段
     * @return
     */
    private Elements elementList(Field field) {
        return elementList(getCssPath(field));
    }

    /**
     * 根据选择器返回元素列表
     *
     * @param selector 选择器
     * @return
     */
    public Elements elementList(String selector) {
        return document.select(selector);
    }

    /**
     * 返回字段对应的单个元素
     *
     * @param field 字段
     * @return
     */
    private Element elementOne(Field field) {
        return elementOne(getCssPath(field));
    }

    /**
     * 返回选择器对应的单个元素
     *
     * @param selector 选择器
     * @return
     */
    public Element elementOne(String selector) {
        Elements elementList = elementList(selector);
        if (elementList != null && elementList.size() > 0) {
            return elementList.first();
        }
        return null;
    }

    /**
     * 返回字段对应元素的值
     *
     * @param element 字段对应html元素
     * @param field 字段
     * @return
     */
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
        // 默认获取元素的文本数据
        else {
            result = valueText(element, false);
        }
        return result;
    }

    /**
     * 获取字段对应元素的map数据
     *
     * @param field 字段
     * @return
     */
    public Map<String,String> valueMap(Field field) {
        Map<String,String> resultMap = new HashMap<>();
        Elements elements = elementList(field);
        for(Element element: elements) {
            valueMap(element, field, resultMap);
        }
        return resultMap;
    }

    /**
     * 获取字段对应元素的map数据
     *
     * @param element 元素
     * @param field 字段
     * @param resultMap 结果Map
     * @return
     */
    private Map<String, String> valueMap(Element element, Field field, Map<String,String> resultMap) {
        HtmlMap htmlMap = field.getAnnotation(HtmlMap.class);
        // 获取key值
        String keyValue = getValueByKeyTypes(element, htmlMap.key());
        if (StrUtil.isBlank(keyValue)) {
            return resultMap;
        }
        keyValue = keyValue.trim();
        // key已存在
        if (resultMap.containsKey(keyValue)) {
            return resultMap;
        }
        // key值不满足过滤条件
        if (!evalExpression(htmlMap.keyExpressionFilter(), "key", keyValue)) {
            return resultMap;
        }
        // 获取value值
        String valueValue = getValueByKeyTypes(element, htmlMap.value());
        if (StrUtil.isBlank(valueValue)) {
            valueValue = "not found any value";
        }
        // value值不满足过滤条件
        if (!evalExpression(htmlMap.valueExpressionFilter(), "value", valueValue)) {
            return resultMap;
        }
        resultMap.put(keyValue, valueValue);
        return resultMap;
    }

    /**
     * 根据多个key的类型获取对应元素的值
     *
     * @param element 元素
     * @param keys 指定的多个key
     * @return
     */
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

    /**
     * 根据key的类型获取对应元素的值
     *
     * @param element 元素
     * @param key 指定key
     * @return
     */
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

    /**
     * 获取字段对应元素的html值
     * @param field 字段
     * @return
     */
    private String valueHtml(Field field) {
        return valueHtml(elementOne(field), false);
    }

    /**
     * 获取字段对应元素的html值
     * @param element 元素
     * @param field 字段
     * @return
     */
    private String valueHtml(Element element, Field field) {
        HtmlHtml htmlHtml = field.getAnnotation(HtmlHtml.class);
        return valueHtml(element, htmlHtml.isOwn());
    }

    /**
     * 获取元素html值
     * @param element 元素
     * @param isOuter 是否包含当前元素
     * @return
     */
    private String valueHtml(Element element, boolean isOuter) {
        if (element == null) {
            return null;
        }
        if (isOuter) {
            return element.outerHtml();
        }
        return element.html();
    }

    /**
     * 获取字段对应元素的文本值
     * @param element 元素
     * @param field 字段
     * @return
     */
    private String valueText(Element element, Field field) {
        HtmlText htmlText = field.getAnnotation(HtmlText.class);
        return valueText(element, htmlText.isOwn());
    }

    /**
     * 获取元素文本值
     * @param element 元素
     * @param isOwn 是否包含当前元素
     * @return
     */
    private String valueText(Element element, boolean isOwn) {
        if (element == null) {
            return null;
        }
        //获取标签自身的文本
        if (isOwn) {
            return element.ownText();
        }
        return element.text();
    }

    /**
     * 获取字段对应元素的属性值
     * @param element 元素
     * @param field 字段
     * @return
     */
    private String valueAttr(Element element, Field field) {
        HtmlAttr htmlAttr = field.getAnnotation(HtmlAttr.class);
        return valueAttr(element, htmlAttr.value());
    }

    /**
     * 获取元素属性值
     *
     * @param element 元素
     * @param attrKey 属性key
     * @return
     */
    private String valueAttr(Element element, String attrKey) {
        if (element == null) {
            return null;
        }
        if ("href".equals(attrKey)) {
            return valueAbsUrl(element, attrKey);
        }
        return element.attr(attrKey);
    }

    /**
     * 获取字段对应元素的图片值
     *
     * @param element 元素
     * @param field 字段
     * @return
     */
    private String valueImage(Element element, Field field) {
        HtmlImage htmlImage = field.getAnnotation(HtmlImage.class);
        return valueAbsUrl(element, "src", htmlImage.value());
    }

    /**
     * 获取字段对应元素的url值
     *
     * @param element 元素
     * @param field 字段
     * @return
     */
    private String valueUrl(Element element, Field field) {
        HtmlUrl htmlUrl = field.getAnnotation(HtmlUrl.class);
        return valueAbsUrl(element, "href", htmlUrl.value());
    }

    /**
     * 获取元素对应的url值
     *
     * @param element 元素
     * @param defaultAttrKey 默认属性key
     * @param attrKeys 指定属性key
     * @return
     */
    private String valueAbsUrl(Element element, String defaultAttrKey, String... attrKeys) {
        for(String attrKey: attrKeys) {
            String result = valueAbsUrl(element, attrKey);
            if (StrUtil.isNotBlank(result)) {
                return result;
            }
        }
        return valueAbsUrl(element, defaultAttrKey);
    }

    /**
     * 获取元素对应的url值
     *
     * @param element 元素
     * @param attrKey 对应的属性key
     * @return
     */
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
