package org.sunso.mini.crawler.annotation.html;

import java.lang.annotation.*;

/**
 * @author sunso520
 * @Title:HtmlHtml
 * @Description: HtmlHtml注解
 * @Created on 2023/10/12 11:09
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlHtml {

	/**
	 * 是否包含当前元素
	 * @return
	 */
	boolean isOwn() default false;

}
