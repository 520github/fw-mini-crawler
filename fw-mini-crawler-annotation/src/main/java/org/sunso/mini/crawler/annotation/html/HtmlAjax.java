package org.sunso.mini.crawler.annotation.html;

import org.sunso.mini.crawler.common.enums.ContentTypeEnum;
import org.sunso.mini.crawler.common.enums.HttpRequestMethodEnum;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.downloader.EmptyCrawlerDownloader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:HtmlAjax
 * @Description: html Ajax请求注解
 * @Created on 2023/10/13 15:09
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlAjax {
    /**
     * 请求url
     * @return
     */
    String url();

    /**
     * 请求方法
     * @return
     */
    HttpRequestMethodEnum method() default HttpRequestMethodEnum.GET;

    /**
     * 请求对应的爬虫下载器
     * @return
     */
    Class<? extends CrawlerDownloader> downloader() default EmptyCrawlerDownloader.class;

    /**
     * contentType
     * @return
     */
    ContentTypeEnum contentType() default ContentTypeEnum.applicationXWwwForm;

    /**
     * ajax结果作为requestAttribute属性值对应的name
     * @return
     */
    String requestAttributeName() default "";

    /**
     * 是否copy父Request的请求头
     * @return
     */
    boolean copyHeader() default false;

    /**
     * 是否copy父Request的cookies
     * @return
     */
    boolean copyCookies() default false;

    /**
     * 是否copy父Request的Attribute
     * @return
     */
    boolean copyAttribute() default false;
}
