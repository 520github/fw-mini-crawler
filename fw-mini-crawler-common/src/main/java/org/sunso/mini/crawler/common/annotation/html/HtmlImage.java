package org.sunso.mini.crawler.common.annotation.html;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlImage {

    String[] value() default "src";

    String downloadLocation() default "";
}
