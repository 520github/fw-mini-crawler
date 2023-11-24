package org.sunso.mini.crawler.formatter.remove;

import org.junit.Test;
import org.sunso.mini.crawler.formatter.BaseTest;

/**
 * @author sunso520
 * @Title:RemoveYearUnitFormatterTest
 * @Description: <br>
 * @Created on 2023/11/24 17:49
 */
public class RemoveYearUnitFormatterTest extends BaseTest {

    RemoveYearUnitFormatter formatter = new RemoveYearUnitFormatter();

    @Test
    public void formatterTest() {
        Object result = formatter.format("年98年");
        print(result);
    }
}
