package org.sunso.mini.crawler.annotation.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonRelationDb {
    String url() default "";

    String user() default "";

    String password() default "";

    String tableName() default "";

    String[] checkExistColumns() default "";

    String[] filterColumns() default "";

    boolean isToUnderlineCase() default true;

    boolean ignoreNullValue() default true;

    boolean insertOrUpdate() default false;
}
