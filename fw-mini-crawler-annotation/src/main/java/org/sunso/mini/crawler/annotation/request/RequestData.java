package org.sunso.mini.crawler.annotation.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:RequestData
 * @Description: RequestData注解 获取request data里获取对应数据
 * @Created on 2023/10/16 10:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestData {

	/**
	 * requestData里对应的key
	 * @return
	 */
	String value() default "";

}
