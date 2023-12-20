package org.sunso.mini.crawler.parser.json;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

/**
 * @author sunso520
 * @Title:JsonFieldParserFactory
 * @Description: json解析器工厂类
 *
 * @Created on 2023/10/13 14:16
 */
public class JsonFieldParserFactory {

    @SneakyThrows
    public static JsonFieldParser getJsonFieldParser(String jsonData, Class<? extends JsonFieldParser> clazz) {
        Constructor constructor = clazz.getConstructor(String.class);
        return (JsonFieldParser)constructor.newInstance(jsonData);
    }
}
