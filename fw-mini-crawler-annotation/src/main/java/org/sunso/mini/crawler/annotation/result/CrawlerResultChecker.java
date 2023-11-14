package org.sunso.mini.crawler.annotation.result;

import org.sunso.mini.crawler.checker.CrawlerChecker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CrawlerResultChecker {
    Class<? extends CrawlerChecker> checker();
}
