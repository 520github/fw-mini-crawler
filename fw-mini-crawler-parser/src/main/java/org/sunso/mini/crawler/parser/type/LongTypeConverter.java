package org.sunso.mini.crawler.parser.type;

public class LongTypeConverter extends AbstractTypeConverter<Long> {
    @Override
    protected Long doConvert(Object value, String format) {
        return Long.parseLong(value.toString());
    }
}
