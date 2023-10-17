package org.sunso.mini.crawler.annotation.html;

import org.sunso.mini.crawler.common.enums.ContentTypeEnum;
import org.sunso.mini.crawler.common.enums.HttpRequestMethodEnum;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlAjax {
    String url();

    HttpRequestMethodEnum method() default HttpRequestMethodEnum.GET;

    Class<? extends CrawlerDownloader> downloader();

    ContentTypeEnum contentType() default ContentTypeEnum.applicationXWwwForm;

    String requestAttributeName() default "";
}
