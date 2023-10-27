package org.sunso.mini.crawler.annotation.html;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlPageUrl {

    int startPageNo() default 1;

    int endPageNo() default Integer.MAX_VALUE;

    String pageNoKey();
}
