package org.sunso.mini.crawler.parser.ajax;

import java.lang.reflect.Field;

public interface AjaxFieldParser {
    Object selectorObject(Field field);
}
