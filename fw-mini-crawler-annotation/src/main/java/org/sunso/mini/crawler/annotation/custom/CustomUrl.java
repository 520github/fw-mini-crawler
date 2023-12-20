package org.sunso.mini.crawler.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:CustomUrl
 * @Description: 自定义url注解
 * @Created on 2023/11/23 16:07
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomUrl {

	/**
	 * url
	 * @return
	 */
	String url();

	/**
	 * url别名
	 * @return
	 */
	String urlAlias() default "";

	/**
	 * 是否触发点击url操作
	 * @return
	 */
	boolean triggerClick() default false;

	/**
	 * 等待时间
	 * @return
	 */
	long waitTime() default 0;

	/**
	 * 是否copy父Request的请求头
	 * @return
	 */
	boolean copyHeader() default false;

	/**
	 * 是否copy父Request的cookies
	 * @return
	 */
	boolean copyCookies() default false;

	/**
	 * 是否copy父Request的Attribute
	 * @return
	 */
	boolean copyAttribute() default false;

}
