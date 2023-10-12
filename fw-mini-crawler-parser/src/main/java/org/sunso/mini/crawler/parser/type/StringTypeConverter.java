package org.sunso.mini.crawler.parser.type;

public class StringTypeConverter extends AbstractTypeConverter<String> {
    @Override
    protected String doConvert(Object value, String format) {
        return value.toString();
    }
}
