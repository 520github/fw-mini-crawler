package org.sunso.mini.crawler.parser;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.request.RequestAttributeGet;
import org.sunso.mini.crawler.annotation.request.RequestAttributeSet;
import org.sunso.mini.crawler.formatter.Formatter;
import org.sunso.mini.crawler.formatter.FormatterFactory;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParser;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParserFactory;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParserRequest;
import org.sunso.mini.crawler.parser.type.TypeConverters;

import java.lang.reflect.Field;

/**
 * @author sunso520
 * @Title:AbstractCrawlerParser
 * @Description: 爬虫解析器抽象类
 * @Created on 2023/10/12 11:03
 */
@Slf4j
public abstract class AbstractCrawlerParser implements CrawlerParser {

	/**
	 * 解析处理字段，最终获取字段值
	 * @param field
	 * @param parserRequest
	 * @return
	 */
	protected Object handleField(Field field, CrawlerFieldParserRequest parserRequest) {
		parserRequest.setField(field);
		CrawlerFieldParser crawlerFieldParser = CrawlerFieldParserFactory.getCrawlerFieldParser(field);
		if (crawlerFieldParser == null) {
			// TODO 返回特殊对象不做处理
			log.info("UnionCrawlerParser find crawlerFieldParser is null by field[{}]", field.getName());
			return null;
		}
		Object fieldValue = crawlerFieldParser.parseField(parserRequest);

		// 格式化处理
		fieldValue = formatFieldValue(field, fieldValue);
		// 类型转化处理
		fieldValue = TypeConverters.getValue(field.getType(), fieldValue, null);
		// 设置request上下文属性
		setRequestAttribute(field, fieldValue, parserRequest);
		return fieldValue;
	}

	/**
	 * 设置当前字段值到当前请求的Attribute中
	 * @param field 字段
	 * @param fieldValue 字段值
	 * @param parserRequest 字段解析请求参数
	 */
	protected void setRequestAttribute(Field field, Object fieldValue, CrawlerFieldParserRequest parserRequest) {
		if (field.isAnnotationPresent(RequestAttributeSet.class)) {
			String name = field.getAnnotation(RequestAttributeSet.class).value();
			if (StrUtil.isBlank(name)) {
				name = field.getName();
			}
			parserRequest.commitRequestAttribute(name, fieldValue);
		}
	}

	/**
	 * 格式化字段对应值
	 * @param field 字段
	 * @param fieldValue 字段值
	 * @return 返回字段格式化后值
	 */
	protected Object formatFieldValue(Field field, Object fieldValue) {
		return FormatterFactory.doFormat(getFormatClasses(field), fieldValue);
	}

	/**
	 * 获取字段格式化类
	 * @param field 字段
	 * @return 返回字段格式化类
	 */
	protected Class<? extends Formatter>[] getFormatClasses(Field field) {
		if (field.isAnnotationPresent(HtmlCssPath.class)) {
			return field.getAnnotation(HtmlCssPath.class).formatter();
		}
		else if (field.isAnnotationPresent(JsonPath.class)) {
			return field.getAnnotation(JsonPath.class).formatter();
		}
		else if (field.isAnnotationPresent(RequestAttributeGet.class)) {
			return field.getAnnotation(RequestAttributeGet.class).formatter();
		}
		return null;
	}

}
