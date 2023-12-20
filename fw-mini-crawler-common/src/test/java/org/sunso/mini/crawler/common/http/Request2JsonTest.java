package org.sunso.mini.crawler.common.http;

import cn.hutool.json.JSONUtil;
import org.junit.Test;
import org.sunso.mini.crawler.common.BaseTest;
import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEventFactory;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;

/**
 * @author sunso520
 * @Title:Request2JsonTest
 * @Description: <br>
 * @Created on 2023/11/1 16:06
 */
public class Request2JsonTest extends BaseTest {

	@Test
	public void request2JsonTest() {
		CrawlerHttpRequest request = getCrawlerHttpRequest();
		String result = JSONUtil.toJsonStr(request);
		print(result);

		CrawlerHttpRequest copResult = JSONUtil.toBean(result, request.getClass());
		print(copResult);
	}

	private CrawlerHttpRequest getCrawlerHttpRequest() {
		return CrawlerHttpRequestBuilder.get("http://www.zz.com").setWaitTime(100).addCookie("id", "1")
				.addCookie("name", "limd").addParameter("p01", "01").addParameter("p02", "02").addData("d01", "d01")
				.addData("d02", "d02").addHeader("ua", "my ua").addHeader("source", "lod").setAttribute("a01", "a01")
				.setAttribute("a02", "b02")
				.setEvent("div.load", CrawlerHttpRequestEventFactory.getInputSetAndMoveCursorEvent("2"));
	}

}
