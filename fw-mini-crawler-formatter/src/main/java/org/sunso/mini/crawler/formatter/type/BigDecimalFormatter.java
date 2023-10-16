package org.sunso.mini.crawler.formatter.type;

import org.sunso.mini.crawler.formatter.AbstractFormatter;

import java.math.BigDecimal;

public class BigDecimalFormatter extends AbstractFormatter {
    @Override
    protected Object doFormat(Object value) {
        if (value instanceof BigDecimal) {
            return value;
        }
        return new BigDecimal(value.toString());
    }
}
