package org.sunso.mini.crawler.parser.type;

import cn.hutool.core.util.StrUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTypeConverter extends AbstractTypeConverter<Date> {

	@lombok.SneakyThrows
	@Override
	protected Date doConvert(Object value, String format) {
		if (value instanceof Date) {
			return (Date) value;
		}
		// return Boolean.parseBoolean(value.toString());
		SimpleDateFormat sdf = new SimpleDateFormat(getFormat(format));
		return sdf.parse(value.toString());
	}

	private String getFormat(String format) {
		if (StrUtil.isBlank(format)) {
			return "yyyy-MM-dd";
		}
		return format;
	}

}
