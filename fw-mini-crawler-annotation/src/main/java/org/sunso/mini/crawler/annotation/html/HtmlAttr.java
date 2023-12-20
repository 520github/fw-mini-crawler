package org.sunso.mini.crawler.annotation.html;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:HtmlAttr
 * @Description: html属性注解
 * @Created on 2023/10/12 11:09
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlAttr {
    /**
     * 属性key
     * @return
     */
    String value();

}
