package org.sunso.mini.crawler.parser.type;

public class BooleanTypeConverter extends AbstractTypeConverter<Boolean> {
    @Override
    protected Boolean doConvert(Object value, String format) {
        return Boolean.parseBoolean(value.toString());
    }
}
