package org.sunso.mini.crawler.parser.html;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.expression.ExpressionUtil;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunso520
 * @Title:AbstractHtmlFieldParser
 * @Description: html解析器抽象类
 * @Created on 2023/10/12 11:03
 */
public abstract class AbstractHtmlFieldParser implements HtmlFieldParser {

	// 请求url
	protected String url;

	// 请求url对应返回的html内容
	protected String htmlContent;

	protected AbstractHtmlFieldParser(String url, String htmlContent) {
		this.url = url;
		this.htmlContent = htmlContent;
	}

	/**
	 * 获取字段对应的cssPath
	 * @param field 字段
	 * @return
	 */
	protected String getCssPath(Field field) {
		HtmlCssPath cssPath = field.getAnnotation(HtmlCssPath.class);
		if (cssPath == null) {
			throw new IllegalArgumentException(String.format("not found @HtmlCssPath in field[%s]", field.getName()));
		}
		return cssPath.value();
	}

	/**
	 * 返回执行表达式结果
	 * @param expression 表达式
	 * @param key 表达式key
	 * @param value 表达式key对应值
	 * @return
	 */
	protected boolean evalExpression(String expression, String key, String value) {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put(key, value);
		return evalExpression(expression, dataMap);
	}

	/**
	 * 返回执行表达式结果
	 * @param expression 表达式
	 * @param dataMap 数据map
	 * @return
	 */
	protected boolean evalExpression(String expression, Map<String, Object> dataMap) {
		if (StrUtil.isBlank(expression)) {
			return true;
		}
		Object result = ExpressionUtil.eval(expression, dataMap);
		if (result instanceof Boolean) {
			return (Boolean) result;
		}
		return true;
	}

}
