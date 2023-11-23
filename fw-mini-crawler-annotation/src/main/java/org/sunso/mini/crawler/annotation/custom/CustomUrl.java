package org.sunso.mini.crawler.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomUrl {

    String url();

    String urlAlias() default "";

    boolean triggerClick() default false;

    long waitTime() default 0;

    boolean copyHeader() default false;

    boolean copyCookies() default false;

    boolean copyAttribute() default false;
}
