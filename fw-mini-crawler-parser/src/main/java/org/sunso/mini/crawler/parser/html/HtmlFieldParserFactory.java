package org.sunso.mini.crawler.parser.html;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

public class HtmlFieldParserFactory {

    @SneakyThrows
    public static HtmlFieldParser getHtmlFieldParser(String url, String htmlContent, Class<? extends HtmlFieldParser> clazz) {
        Constructor constructor = clazz.getConstructor(String.class, String.class);
        return (HtmlFieldParser)constructor.newInstance(url, htmlContent);
    }
}
