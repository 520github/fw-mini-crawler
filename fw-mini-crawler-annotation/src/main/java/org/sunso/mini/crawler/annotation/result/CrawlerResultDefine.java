package org.sunso.mini.crawler.annotation.result;

import org.sunso.mini.crawler.handler.CrawlerHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CrawlerResultDefine {
    Class<? extends CrawlerHandler>[] handlers();
}
