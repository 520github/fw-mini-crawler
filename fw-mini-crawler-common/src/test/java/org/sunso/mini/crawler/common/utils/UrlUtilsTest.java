package org.sunso.mini.crawler.common.utils;

import org.junit.Test;
import org.sunso.mini.crawler.common.BaseTest;

import java.util.HashMap;
import java.util.Map;

public class UrlUtilsTest extends BaseTest {

	@Test
	public void testTest() {
		String[] params = new String[] { "33", "999", "999" };
		UrlUtils.test("ddd", params);
	}

	@Test
	public void urlMatchTest() {
		String matchUrl = "http://www.zhongzhenglawyer.com/NewsDetail/{articleNo}.html";
		String targetUrl = "http://www.zhongzhenglawyer.com/NewsDetail/4272935.html";
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

	@Test
	public void getNextPageUrlTest() {
		String pageKey = "pageNo";
		String url = "http://gsgk.mnr.gov.cn/wQpjApi/qpj/qpjSearch?xzq={code}&wz=&pageNo=1&pageSize=10";
		String result = UrlUtils.getNextPageUrl(url, pageKey, 2);
		print(result);
		url = "http://gsgk.mnr.gov.cn/wQpjApi/qpj/qpjSearch?xzq={code}&wz=&pageSize=10&pageNo=1";
		result = UrlUtils.getNextPageUrl(url, pageKey, 2);
		print(result);
	}

	@Test
	public void replaceParamsTest() {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("url", "http://www.zzz.com");
		String result = UrlUtils.replaceParams("{url}", dataMap);
		print(result);
	}

}
