package org.sunso.mini.crawler.parser.html;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

/**
 * @author sunso520
 * @Title:HtmlFieldParserFactory
 * @Description: html解析器工厂类
 * @Created on 2023/10/12 11:13
 */
public class HtmlFieldParserFactory {

	@SneakyThrows
	public static HtmlFieldParser getHtmlFieldParser(String url, String htmlContent,
			Class<? extends HtmlFieldParser> clazz) {
		Constructor constructor = clazz.getConstructor(String.class, String.class);
		return (HtmlFieldParser) constructor.newInstance(url, htmlContent);
	}

}
