package org.sunso.mini.crawler.annotation.html;

import org.sunso.mini.crawler.common.enums.HttpRequestEventEndFlagEnum;
import org.sunso.mini.crawler.common.enums.HttpRequestEventTypeEnum;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlPageSingleButton {

    String url();

    boolean singlePageAppendData() default false;

    long waitTime() default 0;

    int startDataIndex() default 0;

    int endDataIndex() default Integer.MAX_VALUE;

    String cssSelector();

    HttpRequestEventTypeEnum eventType() default HttpRequestEventTypeEnum.click;

    HttpRequestEventEndFlagEnum eventEndFlag() default HttpRequestEventEndFlagEnum.noSuchElement;

    int eventDoMaxNum() default 100;

    String extendData() default "";

    boolean copyOption() default false;
}
