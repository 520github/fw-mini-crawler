package org.sunso.mini.crawler.parser.html;

import org.sunso.mini.crawler.common.result.CrawlerResult;

import java.lang.reflect.Field;
import java.util.List;

public interface HtmlFieldParser {

    List<String> selectorHtmlList(Field field);

    String selectorHtml(Field field);

    Object selectorObject(Field field);

    List<Object> selectorObjectList(Field field);

    List<? extends CrawlerResult> selectorCrawlerResultList(Field field);

}
