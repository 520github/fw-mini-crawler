package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.annotation.html.HtmlCssPath;

import java.lang.reflect.Field;

public class CrawlerFieldParserFactory {

    public static CrawlerFieldParser getCrawlerFieldParser(Field field) {
        if (field.isAnnotationPresent(HtmlCssPath.class)) {
            return new CrawlerHtmlFieldParser();
        }
//        else if (field.isAnnotationPresent(HtmlAjax.class)) {
//            return new CrawlerAjaxFieldParser();
//        }
        return null;
    }
}
