package org.sunso.mini.crawler.parser.type;

public abstract class AbstractTypeConverter<T> implements TypeConverter {
    @Override
    public T convertValue(Object value, String format) {
        if (value == null) {
            return null;
        }
        return doConvert(value, format);
    }

    protected abstract T doConvert(Object value, String format);
}
