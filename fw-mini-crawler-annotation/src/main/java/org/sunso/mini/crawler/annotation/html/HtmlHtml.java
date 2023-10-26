package org.sunso.mini.crawler.annotation.html;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlHtml {
    boolean isOwn() default false;

}
