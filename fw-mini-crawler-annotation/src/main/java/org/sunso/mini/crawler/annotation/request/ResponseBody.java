package org.sunso.mini.crawler.annotation.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:ResponseBody
 * @Description: ResponseBody注解 获取request请求对应的响应数据
 * @Created on 2023/10/16 10:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseBody {

	/**
	 * 父Request的级别
	 * @return
	 */
	int parentLevel() default 0;

}
