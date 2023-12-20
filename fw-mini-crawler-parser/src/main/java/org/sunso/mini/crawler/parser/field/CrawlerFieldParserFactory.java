package org.sunso.mini.crawler.parser.field;

import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.annotation.custom.CustomUrl;
import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlPageSingleButton;
import org.sunso.mini.crawler.annotation.html.HtmlPageUrl;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.request.*;

import java.lang.reflect.Field;

/**
 * @author sunso520
 * @Title:CrawlerFieldParserFactory
 * @Description: 爬虫字段解析器工厂类<br>
 * @Created on 2023/10/12 11:20
 */
@Slf4j
public class CrawlerFieldParserFactory {

    /**
     * 根据字段注解配置，获取字段对应解析器
     *
     * @param field 字段
     * @return 返回字段解析器
     */
    public static CrawlerFieldParser getCrawlerFieldParser(Field field) {
        // 自定义url
        if (field.isAnnotationPresent(CustomUrl.class)) {
            return new CrawlerCustomUrlFieldParser();
        }
        // 字段值为Request对象
        else if (field.isAnnotationPresent(Request.class)) {
            return new CrawlerRequestFieldParser();
        }
        // 字段值为请求url
        else if (field.isAnnotationPresent(RequestUrl.class)) {
            return new CrawlerRequestUrlFieldParser();
        }
        // 字段值为请求参数
        else if (field.isAnnotationPresent(RequestParameter.class)) {
            return new CrawlerRequestParameterFieldParser();
        }
        // 字段值为请求数据
        else if (field.isAnnotationPresent(RequestData.class)) {
            return new CrawlerRequestDataFieldParser();
        }
        // 字段值为请求属性
        else if (field.isAnnotationPresent(RequestAttributeGet.class)) {
            return new CrawlerRequestAttributeGetFieldParser();
        }
        // 字段值为Request对返回结果
        else if (field.isAnnotationPresent(Response.class)) {
            return new CrawlerResponseFieldParser();
        }
        // 字段值为Request对应返回Body数据
        else if (field.isAnnotationPresent(ResponseBody.class)) {
            return new CrawlerResponseBodyFieldParser();
        }
        // 字段值为Request对应返回状态码
        else if (field.isAnnotationPresent(ResponseStatus.class)) {
            return new CrawlerResponseStatusFieldParser();
        }
        // 字段为分页类型
        else if (field.isAnnotationPresent(HtmlPageUrl.class)) {
            return new CrawlerPageUrlFieldParser();
        }
        // 字段为浏览器点击按钮方式的分页类型
        else if (field.isAnnotationPresent(HtmlPageSingleButton.class)) {
            return new CrawlerPageSingleButtonFieldParser();
        }
        // 字段为html类型解析
        else if (field.isAnnotationPresent(HtmlCssPath.class)) {
            return new CrawlerHtmlFieldParser();
        }
        // 字段为htmlAjax类型解析
        else if (field.isAnnotationPresent(HtmlAjax.class)) {
            return new CrawlerAjaxFieldParser();
        }
        // 字段为json类型解析
        else if (field.isAnnotationPresent(JsonPath.class)) {
            return new CrawlerJsonFieldParser();
        }
        log.warn("CrawlerFieldParserFactory not found any CrawlerFieldParser with filed[{}]", field.getName());
        return null;
    }
}
