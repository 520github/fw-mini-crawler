package org.sunso.mini.crawler.annotation.html;

import java.lang.annotation.*;

/**
 * @author sunso520
 * @Title:HtmlPageUrl
 * @Description: 通过httClient方式获取分页数据的注解
 *
 * @Created on 2023/10/27 10:22
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlPageUrl {

    /**
     * 开始页数
     * @return
     */
    int startPageNo() default 1;

    /**
     * 结束页面
     * @return
     */
    int endPageNo() default Integer.MAX_VALUE;

    /**
     * 分页数对应的key
     * @return
     */
    String pageNoKey();
}
