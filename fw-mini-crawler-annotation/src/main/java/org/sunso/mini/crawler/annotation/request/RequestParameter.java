package org.sunso.mini.crawler.annotation.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:RequestParameter
 * @Description: RequestParameter注解
 * 获取request参数里获取对应数据
 * @Created on 2023/10/16 10:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParameter {
    /**
     * 请求参数name
     * @return
     */
    String value() default "";
}
