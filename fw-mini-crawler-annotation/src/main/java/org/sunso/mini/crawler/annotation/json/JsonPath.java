package org.sunso.mini.crawler.annotation.json;

import org.sunso.mini.crawler.formatter.Formatter;
import org.sunso.mini.crawler.formatter.NoneFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonPath {

    String value();

    Class<? extends Formatter>[] formatter() default NoneFormatter.class;

    String expressionFilter() default "";
}
