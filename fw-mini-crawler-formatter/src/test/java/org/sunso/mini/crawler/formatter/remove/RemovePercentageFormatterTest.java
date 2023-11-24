package org.sunso.mini.crawler.formatter.remove;

import org.junit.Test;
import org.sunso.mini.crawler.formatter.BaseTest;

/**
 * @author sunso520
 * @Title:RemovePercentageFormatterTest
 * @Description: <br>
 * @Created on 2023/11/24 17:52
 */
public class RemovePercentageFormatterTest extends BaseTest {
    RemovePercentageFormatter formatter = new RemovePercentageFormatter();

    @Test
    public void formatterTest() {
        Object result = formatter.format("%98%");
        print(result);
    }
}
