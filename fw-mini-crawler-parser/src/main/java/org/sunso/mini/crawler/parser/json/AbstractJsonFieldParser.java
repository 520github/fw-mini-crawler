package org.sunso.mini.crawler.parser.json;

public abstract class AbstractJsonFieldParser implements JsonFieldParser {
    protected String jsonContent;

    protected AbstractJsonFieldParser(String jsonContent) {
        this.jsonContent = jsonContent;
    }


    protected String getJsonContent() {
        return jsonContent;
    }
}
