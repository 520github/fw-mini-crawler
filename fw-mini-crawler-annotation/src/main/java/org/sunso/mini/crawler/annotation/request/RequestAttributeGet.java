package org.sunso.mini.crawler.annotation.request;

import org.sunso.mini.crawler.formatter.Formatter;
import org.sunso.mini.crawler.formatter.NoneFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:RequestAttributeGet
 * @Description: RequestAttributeGet注解
 * 从RequestAttribute获取对应值
 * @Created on 2023/10/17 11:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestAttributeGet {
    /**
     * 需要从RequestAttribute里获取值对应的name
     * @return
     */
    String value() default "";

    /**
     * 数据格式化
     * @return
     */
    Class<? extends Formatter>[] formatter() default NoneFormatter.class;
}
