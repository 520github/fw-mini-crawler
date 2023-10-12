package org.sunso.mini.crawler.common.annotation.html;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlText {
    boolean isOwn() default true;

    String format() default "";
}
