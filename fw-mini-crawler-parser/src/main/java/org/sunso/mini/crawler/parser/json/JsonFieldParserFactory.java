package org.sunso.mini.crawler.parser.json;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

public class JsonFieldParserFactory {

    @SneakyThrows
    public static JsonFieldParser getJsonFieldParser(String jsonData, Class<? extends JsonFieldParser> clazz) {
        Constructor constructor = clazz.getConstructor(String.class);
        return (JsonFieldParser)constructor.newInstance(jsonData);
    }
}
