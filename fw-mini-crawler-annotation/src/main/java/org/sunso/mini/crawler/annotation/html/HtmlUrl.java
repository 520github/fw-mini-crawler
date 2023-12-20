package org.sunso.mini.crawler.annotation.html;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:HtmlUrl
 * @Description: Html链接注解
 * @Created on 2023/10/12 11:09
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlUrl {

    /**
     * 获取链接值的属性key
     * @return
     */
    String[] value() default "href";

    /**
     * 链接对应别名
     * @return
     */
    String urlAlias() default "";

    /**
     * 是否触发点击链接
     * @return
     */
    boolean triggerClick() default false;

    /**
     * 等待时间
     * @return
     */
    long waitTime() default 0;

    /**
     * 是否copy父Request的请求头
     * @return
     */
    boolean copyHeader() default false;

    /**
     * 是否copy父Request的cookies
     * @return
     */
    boolean copyCookies() default false;

    /**
     * 是否copy父Request的Attribute
     * @return
     */
    boolean copyAttribute() default false;
}
