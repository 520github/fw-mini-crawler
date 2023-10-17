package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.request.*;

import java.lang.reflect.Field;

public class CrawlerFieldParserFactory {

    public static CrawlerFieldParser getCrawlerFieldParser(Field field) {
        if (field.isAnnotationPresent(Request.class)) {
            return new CrawlerRequestFieldParser();
        }
        else if (field.isAnnotationPresent(RequestParameter.class)) {
            return new CrawlerRequestParameterFieldParser();
        }
        else if (field.isAnnotationPresent(RequestData.class)) {
            return new CrawlerRequestDataFieldParser();
        }
        else if (field.isAnnotationPresent(RequestAttributeGet.class)) {
            return new CrawlerRequestAttributeGetFieldParser();
        }
        else if (field.isAnnotationPresent(Response.class)) {
            return new CrawlerResponseFieldParser();
        }
        else if (field.isAnnotationPresent(ResponseBody.class)) {
            return new CrawlerResponseBodyFieldParser();
        }
        else if (field.isAnnotationPresent(ResponseStatus.class)) {
            return new CrawlerResponseStatusFieldParser();
        }
        else if (field.isAnnotationPresent(HtmlCssPath.class)) {
            return new CrawlerHtmlFieldParser();
        }
        else if (field.isAnnotationPresent(HtmlAjax.class)) {
            return new CrawlerAjaxFieldParser();
        }
        else if (field.isAnnotationPresent(JsonPath.class)) {
            return new CrawlerJsonFieldParser();
         }
        return null;
    }
}
