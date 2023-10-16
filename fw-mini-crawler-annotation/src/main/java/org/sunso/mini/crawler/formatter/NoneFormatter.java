package org.sunso.mini.crawler.formatter;

public class NoneFormatter implements Formatter {
    @Override
    public Object format(Object value) {
        return value;
    }
}
