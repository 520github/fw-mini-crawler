package org.sunso.mini.crawler.annotation.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:FieldDefine
 * @Description: 字段相关定义注解
 * @Created on 2023/11/28 15:17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDefine {

	/**
	 * 设置默认值
	 * @return
	 */
	String defaultValue();

}
