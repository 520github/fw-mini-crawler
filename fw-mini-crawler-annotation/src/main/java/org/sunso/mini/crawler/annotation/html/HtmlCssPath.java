package org.sunso.mini.crawler.annotation.html;

import org.sunso.mini.crawler.formatter.Formatter;
import org.sunso.mini.crawler.formatter.NoneFormatter;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlCssPath {
    String value();

    Class<? extends Formatter>[] formatter() default NoneFormatter.class;

    String expressionFilter() default "";
}
