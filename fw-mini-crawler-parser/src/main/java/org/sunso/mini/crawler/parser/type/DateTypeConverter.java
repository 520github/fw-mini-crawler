package org.sunso.mini.crawler.parser.type;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTypeConverter extends AbstractTypeConverter<Date> {
    @lombok.SneakyThrows
    @Override
    protected Date doConvert(Object value, String format) {
        if (value instanceof Date) {
            return (Date) value;
        }
        //return Boolean.parseBoolean(value.toString());
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(value.toString());
    }
}
