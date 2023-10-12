package org.sunso.mini.crawler.parser.html;

import cn.hutool.core.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sunso.mini.crawler.common.annotation.html.HtmlAttr;
import org.sunso.mini.crawler.common.annotation.html.HtmlImage;
import org.sunso.mini.crawler.common.annotation.html.HtmlText;
import org.sunso.mini.crawler.common.annotation.html.HtmlUrl;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.parser.type.TypeConverters;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JsoupHtmlFieldParser extends AbstractHtmlFieldParser {

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
        return TypeConverters.getValue(field.getType(), result, null);
    }

    @Override
    public List<? extends CrawlerResult> selectorCrawlerResultList(Field field) {
        return null;
    }

    private Elements elementList(Field field) {
        return elementList(getCssPath(field));
    }

    private Elements elementList(String selector) {
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
        else if (field.isAnnotationPresent(HtmlAttr.class)) {
            result = valueAttr(element, field);
        }
        else if (field.isAnnotationPresent(HtmlImage.class)) {
            result = valueImage(element, field);
        }
        else if (field.isAnnotationPresent(HtmlUrl.class)) {
            result = valueUrl(element, field);
        }
        return result;
    }

    private Element elementOne(String selector) {
        Elements elementList = elementList(selector);
        if (elementList != null && elementList.size() > 0) {
            return elementList.first();
        }
        return null;
    }

    private String valueHtml(Field field) {
        return valueHtml(elementOne(field), false);
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
