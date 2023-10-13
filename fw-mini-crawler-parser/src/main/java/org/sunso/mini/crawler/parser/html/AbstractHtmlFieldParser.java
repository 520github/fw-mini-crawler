package org.sunso.mini.crawler.parser.html;

import org.sunso.mini.crawler.annotation.html.HtmlCssPath;

import java.lang.reflect.Field;

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
}
