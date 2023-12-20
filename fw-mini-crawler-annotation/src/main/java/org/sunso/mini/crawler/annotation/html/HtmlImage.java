package org.sunso.mini.crawler.annotation.html;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:HtmlHtml
 * @Description: Html图片注解
 * @Created on 2023/10/12 11:09
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlImage {

	/**
	 * 获取图片值的属性字段
	 * @return
	 */
	String[] value() default "src";

}
