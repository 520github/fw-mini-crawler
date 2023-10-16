package org.sunso.mini.crawler.parser.hutool;

import cn.hutool.extra.expression.ExpressionUtil;
import org.junit.Test;
import org.sunso.mini.crawler.parser.BaseTest;

import java.util.HashMap;
import java.util.Map;

public class ExpressionUtilTest extends BaseTest {

    @Test
    public void expressionTest() {
        //ExpressionUtil.getEngine()
        Object result = ExpressionUtil.eval("(name=='liming' && age>30 && config.min==20) or config.max<50", getMap());
        print(result.getClass().getName());
        print(result);
    }

    private Map<String,Object> getMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", "liming");
        dataMap.put("age", 40);
        Map<String, Object> config = new HashMap<>();
        config.put("min", 30);
        config.put("max", 60);

        dataMap.put("config", config);
        return dataMap;
    }
}
