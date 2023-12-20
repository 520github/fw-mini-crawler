package org.sunso.mini.crawler.annotation.html;

import org.sunso.mini.crawler.formatter.Formatter;
import org.sunso.mini.crawler.formatter.NoneFormatter;

import java.lang.annotation.*;

/**
 * @author sunso520
 * @Title:HtmlCssPath
 * @Description: html字段选择器
 *
 * @Created on 2023/10/12 10:22
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlCssPath {

	/**
	 * html选择器具体值
	 *
	 * <p>
	 * 例子
	 * <p/>
	 * <ul>
	 * <li>@HtmlCssPath(".ModuleProduteDetailMain .inSwiper-slide
	 * .mobile-inSlide-size")</li>
	 * <li>@HtmlCssPath("div#js_content > section:eq(2)")</li>
	 * <li>@HtmlCssPath(value = "tr>td:eq(0) a")</li>
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

	/**
	 * 数据修补类型,字段值存在缺少标签的，可以自动修补
	 * @return
	 */
	HtmlRepairTypeEnum repairType() default HtmlRepairTypeEnum.empty;

}
