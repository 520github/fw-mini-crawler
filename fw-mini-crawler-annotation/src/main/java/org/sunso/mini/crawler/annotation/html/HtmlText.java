package org.sunso.mini.crawler.annotation.html;

import java.lang.annotation.*;

/**
 * @author sunso520
 * @Title:HtmlText
 * @Description: Html文本注解
 * @Created on 2023/10/12 11:09
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlText {
    /**
     * 是否包含当前元素的文本
     * @return
     */
    boolean isOwn() default false;

}
