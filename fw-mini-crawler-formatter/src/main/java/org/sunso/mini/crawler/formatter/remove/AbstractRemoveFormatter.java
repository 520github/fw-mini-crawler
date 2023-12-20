package org.sunso.mini.crawler.formatter.remove;

import org.sunso.mini.crawler.formatter.AbstractFormatter;

/**
 * @author sunso520
 * @Title:AbstractRemoveFormatter
 * @Description: 移除掉指定值格式化抽象类<br>
 * @Created on 2023/11/24 17:33
 */
public abstract class AbstractRemoveFormatter extends AbstractFormatter {

    protected String removeKey;


    @Override
    protected Object doFormat(Object value) {
        return doRemoveFormat(value);
    }

    protected Object doRemoveFormat(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString().replaceAll(removeKey, "");
    }
}
