package org.sunso.mini.crawler.formatter.replace;

import org.sunso.mini.crawler.formatter.AbstractFormatter;

/**
 * @author sunso520
 * @Title:AbstractReplaceFormatter
 * @Description: 替换掉指定值格式化抽象类<br>
 * @Created on 2023/11/24 17:33
 */
public abstract class AbstractReplaceFormatter extends AbstractFormatter {

	protected String replaceKey;

	protected String replaceValue;

	@Override
	protected Object doFormat(Object value) {
		return doReplaceFormat(value);
	}

	protected Object doReplaceFormat(Object value) {
		if (value == null) {
			return null;
		}
		return value.toString().replaceAll(replaceKey, replaceValue);
	}

}
