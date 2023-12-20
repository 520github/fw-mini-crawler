package org.sunso.mini.crawler.formatter.string;

import org.sunso.mini.crawler.formatter.AbstractFormatter;

/**
 * @author sunso520
 * @Title:StringNull2EmptyFormatter
 * @Description: 把null转成空字符串的格式化处理<br>
 * @Created on 2023/11/27 15:50
 */
public class StringNull2EmptyFormatter extends AbstractFormatter {

	@Override
	public Object format(Object value) {
		return doFormat(value);
	}

	@Override
	protected Object doFormat(Object value) {
		if (value == null) {
			return "";
		}
		return value;
	}

}
