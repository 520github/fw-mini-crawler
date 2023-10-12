package org.sunso.mini.crawler.parser.json;

public interface JsonParser {
    Object parse(String jsonSource, String jsonPath);
}
