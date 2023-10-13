package org.sunso.mini.crawler.parser.json;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.checkerframework.checker.units.qual.A;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.utils.JsonUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HuToolJsonFieldParser extends AbstractJsonFieldParser {
    JSON jsonObject = null;
    public HuToolJsonFieldParser(String jsonContent) {
        super(jsonContent);
        initJson();
    }

    @Override
    public Object selectorObject(Field field) {
        String jsonPath = getJsonPath(field);
        if (!jsonPath.contains(",")) {
            return jsonObject.getByPath(jsonPath);
        }
        List<String> jsonPathList = getMultiJsonPath(jsonPath);
        List<Object> list = null;
        JSONArray jsonArray = null;
        for(String path: jsonPathList) {
            Object data = jsonObject.getByPath(path);
            if (data instanceof JSONArray || data instanceof JSONObject) {
                if (jsonArray == null) {
                    jsonArray = new JSONArray();
                }
                jsonArray.put(data);
            }
            else {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(data);
            }
        }
        //TODO 进行格式化处理
        return list != null? list: jsonArray;
    }

    private String getJsonPath(Field field) {
        JsonPath jsonPath = field.getAnnotation(JsonPath.class);
        return jsonPath.value();
    }

    private List<String> getMultiJsonPath(String jsonPath) {
        return JsonUtils.getMultiJsonPath(jsonPath);
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
