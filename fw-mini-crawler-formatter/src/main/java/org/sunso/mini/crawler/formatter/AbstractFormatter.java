package org.sunso.mini.crawler.formatter;

/**
 * @author sunso520
 * @Title:AbstractFormatter
 * @Description: 数据格式化抽象类<br>
 * @Created on 2023/10/16 15:26
 */
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
