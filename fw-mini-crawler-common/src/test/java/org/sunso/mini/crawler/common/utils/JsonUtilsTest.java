package org.sunso.mini.crawler.common.utils;

import org.junit.Test;
import org.sunso.mini.crawler.common.BaseTest;

import java.util.List;

public class JsonUtilsTest extends BaseTest {

    @Test
    public void getMultiJsonPathTest() {
        String jsonPath = "data.li[0].list[1,2,3].type.id[6]";
        List<String> result = JsonUtils.getMultiJsonPath(jsonPath);
        print(result);
    }
}
