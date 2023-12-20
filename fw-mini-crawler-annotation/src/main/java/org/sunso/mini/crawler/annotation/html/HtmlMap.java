package org.sunso.mini.crawler.annotation.html;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:HtmlMap
 * @Description: HtmlMap注解
 * 返回一个Map数据
 * @Created on 2023/10/19 14:36
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlMap {

    /**
     * 设置key值读取的来源
     * @return
     */
    String[] key();

    /**
     * 设置value值读取的来源
     * @return
     */
    String[] value();

    /**
     * key值表达式过滤
     * @return
     */
    String keyExpressionFilter() default "";

    /**
     * value值表达式过滤
     * @return
     */
    String valueExpressionFilter() default "";

}
