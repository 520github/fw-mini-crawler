package org.sunso.mini.crawler.formatter.type;

import org.sunso.mini.crawler.formatter.AbstractFormatter;

import java.math.BigDecimal;

/**
 * @author sunso520
 * @Title:BigDecimalFormatter
 * @Description: 把字符串转化成BigDecimal的格式化处理<br>
 * @Created on 2023/10/16 15:11
 */
public class BigDecimalFormatter extends AbstractFormatter {
    @Override
    protected Object doFormat(Object value) {
        if (value instanceof BigDecimal) {
            return value;
        }
        return new BigDecimal(value.toString());
    }
}
