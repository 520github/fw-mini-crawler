package org.sunso.mini.crawler.formatter;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class FormatterFactory {

    @SneakyThrows
    public static Formatter getFormatter(Class<? extends Formatter> clazz) {
        return clazz.newInstance();
    }

    public static List<Formatter> getFormatterList(Class<? extends Formatter>[] classes) {
        List<Formatter> list = new ArrayList<>();
        for(Class<? extends Formatter> clazz: classes) {
            if (classes.equals(NoneFormatter.class) ) {
                continue;
            }
            list.add(getFormatter(clazz));
        }
        return list;
    }

    public static Object doFormat(Class<? extends Formatter>[] classes, Object value) {
        Object result = value;
        if (classes == null) {
            return value;
        }
        List<Formatter> list = getFormatterList(classes);
        if (list == null || list.isEmpty()) {
            return result;
        }
        for(Formatter formatter: list) {
            result = formatter.format(result);
        }
        return result;
    }
}
