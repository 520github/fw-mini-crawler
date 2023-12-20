package org.sunso.mini.crawler.parser.json;

/**
 * @author sunso520
 * @Title:AbstractJsonFieldParser
 * @Description: json解析器抽象类
 *
 * @Created on 2023/10/12 11:17
 */
public abstract class AbstractJsonFieldParser implements JsonFieldParser {
    // json数据
    protected String jsonContent;

    protected AbstractJsonFieldParser(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    protected String getJsonContent() {
        return jsonContent;
    }
}
