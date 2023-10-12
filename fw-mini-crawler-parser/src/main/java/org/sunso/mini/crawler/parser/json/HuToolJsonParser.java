package org.sunso.mini.crawler.parser.json;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

public class HuToolJsonParser implements JsonParser {
    @Override
    public Object parse(String jsonSource, String jsonPath) {
        JSON json = initJson(jsonSource);
        return json.getByPath(jsonPath);
    }

    private JSON initJson(String jsonSource) {
        if (jsonSource.startsWith("{")) {
            return JSONUtil.parseObj(jsonSource);
        }
        else if (jsonSource.startsWith("[")) {
            return JSONUtil.parseArray(jsonSource);
        }
        return null;
    }
}
