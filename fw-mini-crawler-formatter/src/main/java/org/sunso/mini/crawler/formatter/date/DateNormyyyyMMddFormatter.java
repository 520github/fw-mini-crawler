package org.sunso.mini.crawler.formatter.date;

import cn.hutool.core.date.DateUtil;
import org.sunso.mini.crawler.formatter.AbstractFormatter;

public class DateNormyyyyMMddFormatter extends AbstractFormatter {

    @Override
    protected Object doFormat(Object value) {
        return DateUtil.parseDate(value.toString());
    }
}
