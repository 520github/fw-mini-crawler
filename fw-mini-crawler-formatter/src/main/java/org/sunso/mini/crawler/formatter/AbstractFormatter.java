package org.sunso.mini.crawler.formatter;

public abstract class AbstractFormatter implements Formatter {
    @Override
    public Object format(Object value) {
        if (value == null) {
            return null;
        }
        return doFormat(value);
    }

    protected abstract Object doFormat(Object value);
}
