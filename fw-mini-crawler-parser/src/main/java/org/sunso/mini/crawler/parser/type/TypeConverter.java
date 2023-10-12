package org.sunso.mini.crawler.parser.type;

public interface TypeConverter<T> {
    T convertValue(Object value, String format);
}
