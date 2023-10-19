package org.sunso.mini.crawler.annotation.html;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlMap {

    String[] key();

    String[] value();

    String keyExpressionFilter() default "";

    String valueExpressionFilter() default "";

}
