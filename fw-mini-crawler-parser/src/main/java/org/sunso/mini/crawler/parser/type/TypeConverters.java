package org.sunso.mini.crawler.parser.type;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TypeConverters {
    private static final Map<Class, TypeConverter> TYPE_CONVERTER_MAP = new HashMap<>();

    static {
        TYPE_CONVERTER_MAP.put(Integer.class, new IntegerTypeConverter());
        TYPE_CONVERTER_MAP.put(int.class, new IntegerTypeConverter());
        TYPE_CONVERTER_MAP.put(Long.class, new LongTypeConverter());
        TYPE_CONVERTER_MAP.put(long.class, new LongTypeConverter());
        TYPE_CONVERTER_MAP.put(Float.class, new FloatTypeConverter());
        TYPE_CONVERTER_MAP.put(float.class, new FloatTypeConverter());
        TYPE_CONVERTER_MAP.put(Double.class, new DoubleTypeConverter());
        TYPE_CONVERTER_MAP.put(double.class, new DoubleTypeConverter());
        TYPE_CONVERTER_MAP.put(Boolean.class, new BooleanTypeConverter());
        TYPE_CONVERTER_MAP.put(boolean.class, new BooleanTypeConverter());
        TYPE_CONVERTER_MAP.put(Date.class, new DateTypeConverter());
        TYPE_CONVERTER_MAP.put(BigDecimal.class, new BigDecimalTypeConverter());
    }

    public static Object getValue(Class<?> type, Object value, String format)  {
        TypeConverter converter = TYPE_CONVERTER_MAP.get(type);
        if (converter != null && value != null) {
            return converter.convertValue(value, format);
        }
        return value;
    }

    public static void register(Class<?> type, TypeConverter typeConverter) {
        TYPE_CONVERTER_MAP.put(type, typeConverter);
    }
}
