package org.sunso.mini.crawler.parser.json;

import java.lang.reflect.Field;

public interface JsonFieldParser {
    Object selectorObject(Field field);
}
