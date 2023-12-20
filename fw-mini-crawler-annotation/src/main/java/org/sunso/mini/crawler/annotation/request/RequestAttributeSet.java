package org.sunso.mini.crawler.annotation.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:RequestAttributeSet
 * @Description: RequestAttributeSet注解
 * 向RequestAttribute设置对应值
 * @Created on 2023/10/17 10:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestAttributeSet {
    /**
     * 向RequestAttribute设置值对应的name
     * @return
     */
    String value() default "";
}
