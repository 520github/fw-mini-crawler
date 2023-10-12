package org.sunso.mini.crawler.parser.hutool;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.Test;
import org.sunso.mini.crawler.parser.BaseTest;

public class JSONUtilTest extends BaseTest {

    public void jsonObjectTest() {

    }

    @Test
    public void jsonArrayTest() {
        Object result = JSONUtil.getByPath(getJSONArray(), "$.name");
        print(result.getClass().getName());
        print(result);
    }

    private JSONObject getJSONObject() {
        return null;
    }

    private JSONArray getJSONArray() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("{'id':1,'name':'name1'},");
        sb.append("{'id':2,'name':'name2'},");
        sb.append("{'id':3,'name':'name3'}");
        sb.append("]");
        return JSONUtil.parseArray(sb.toString());
    }
}
