package org.sunso.mini.crawler.common.utils;

import org.junit.Test;
import org.sunso.mini.crawler.common.BaseTest;

import java.util.Map;

public class UrlUtilsTest extends BaseTest {

    @Test
    public void urlMatchTest() {
        String matchUrl = "http://weixin.sogou.com/weixin?type=2&query={keyword}";
        String targetUrl = "http://weixin.sogou.com/weixin?type=2&query=9999";
        Map<String, String> result = UrlUtils.urlMatch(matchUrl, targetUrl);
        print(result);
    }

    @Test
    public void urlMatchMultiTest() {
        String matchUrl = "http://weixin.sogou.com/weixin?type=2&query={keyword}&name={name}&isNew=1";
        String targetUrl = "http://weixin.sogou.com/weixin?type=2&query=9999&name=liming777&isNew=1";
        Map<String, String> result = UrlUtils.urlMatch(matchUrl, targetUrl);
        print(result);
    }
}
