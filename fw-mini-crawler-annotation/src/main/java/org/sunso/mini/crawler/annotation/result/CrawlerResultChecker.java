package org.sunso.mini.crawler.annotation.result;

import org.sunso.mini.crawler.checker.CrawlerChecker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:CrawlerResultChecker
 * @Description: 爬虫结果检查器注解
 * @Created on 2023/11/14 15:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CrawlerResultChecker {
    /**
     * 设置检查器
     * @return
     */
    Class<? extends CrawlerChecker> checker();
}
