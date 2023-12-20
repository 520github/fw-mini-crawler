package org.sunso.mini.crawler.formatter.date;

import cn.hutool.core.date.DateUtil;
import org.sunso.mini.crawler.formatter.AbstractFormatter;

/**
 * @author sunso520
 * @Title:DateNormyyyyMMddFormatter
 * @Description: 日期格式化类<br>
 * @Created on 2023/10/16 15:26
 */
public class DateNormyyyyMMddFormatter extends AbstractFormatter {

    @Override
    protected Object doFormat(Object value) {
        return DateUtil.parseDate(value.toString());
    }
}
