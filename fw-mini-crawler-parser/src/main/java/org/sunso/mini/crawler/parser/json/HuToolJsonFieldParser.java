package org.sunso.mini.crawler.parser.json;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.sunso.mini.crawler.common.annotation.json.JsonPath;

import java.lang.reflect.Field;

public class HuToolJsonFieldParser extends AbstractJsonFieldParser {
    JSON jsonObject = null;
    public HuToolJsonFieldParser(String jsonContent) {
        super(jsonContent);
    }

    @Override
    public Object selectorObject(Field field) {
        Object result = jsonObject.getByPath(getJsonPath(field));
        //TODO 进行格式化处理
        return result;
    }

    private String getJsonPath(Field field) {
        JsonPath jsonPath = field.getAnnotation(JsonPath.class);
        return jsonPath.value();
    }

    private void initJson() {
        String jsonContent = getJsonContent();
        if (jsonContent.startsWith("{")) {
            jsonObject = JSONUtil.parseObj(jsonContent);
        }
        else if (jsonContent.startsWith("[")) {
            jsonObject = JSONUtil.parseArray(jsonContent);
        }
    }
}
