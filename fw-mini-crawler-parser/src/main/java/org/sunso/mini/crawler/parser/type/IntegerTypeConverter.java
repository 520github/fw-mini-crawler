package org.sunso.mini.crawler.parser.type;

public class IntegerTypeConverter extends AbstractTypeConverter<Integer> {
    @Override
    protected Integer doConvert(Object value, String format) {
        return Integer.parseInt(value.toString());
    }
}
