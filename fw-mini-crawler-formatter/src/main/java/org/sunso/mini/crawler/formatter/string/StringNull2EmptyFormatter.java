package org.sunso.mini.crawler.formatter.string;

import org.sunso.mini.crawler.formatter.AbstractFormatter;

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
