package org.sunso.mini.crawler.annotation.order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:FieldOrder
 * @Description: 字段排序注解
 * @Created on 2023/10/18 15:19
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldOrder {
    /**
     * 设置字段排序值
     * @return
     */
    int sort() default 0;
}
