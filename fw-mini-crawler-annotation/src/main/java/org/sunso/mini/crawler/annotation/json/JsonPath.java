package org.sunso.mini.crawler.annotation.json;

import org.sunso.mini.crawler.formatter.Formatter;
import org.sunso.mini.crawler.formatter.NoneFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:JsonPath
 * @Description: json字段选择器
 *
 * @Created on 2023/10/12 10:24
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonPath {

	/**
	 * json选择器具体值
	 *
	 * <p>
	 * 例子
	 * <p/>
	 * <ul>
	 * <li>@JSONPath("$.Hits")</li>
	 * <li>@JsonPath("result.records")</li>
	 * <li>@JsonPath("name")</li>
	 * </ul>
	 * @return
	 */
	String value();

	/**
	 * 数据格式化
	 * @return
	 */
	Class<? extends Formatter>[] formatter() default NoneFormatter.class;

	/**
	 * 数据过滤
	 * @return
	 */
	String expressionFilter() default "";

}
