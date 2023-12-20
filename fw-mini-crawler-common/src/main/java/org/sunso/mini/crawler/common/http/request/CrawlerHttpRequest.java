package org.sunso.mini.crawler.common.http.request;

import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEvent;
import org.sunso.mini.crawler.common.http.option.Option;

import java.util.Map;

/**
 * @author sunso520
 * @Title:CrawlerHttpRequest
 * @Description: 爬虫Http请求定义接口 该接口包含：
 * <ul>
 * <li>请求url</li>
 * <li>请求头</li>
 * <li>请求cookies</li>
 * <li>请求数据</li>
 * <li>请求事件</li>
 * <li>请求id、url别名、排序、等待时间等</li>
 * </ul>
 * @Created on 2023/10/12 10:27
 */
public interface CrawlerHttpRequest {

	/**
	 * 获取请求url
	 * @return 返回请求url
	 */
	String getUrl();

	/**
	 * 设置请求url
	 * @param url 请求url
	 */
	void setUrl(String url);

	/**
	 * 获取url对应别名
	 * @return 返回url别名
	 */
	String getUrlAlias();

	/**
	 * 设置url别名
	 * @param urlAlias url别名
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setUrlAlias(String urlAlias);

	/**
	 * 获取请求id
	 * @return 返回请求id
	 */
	String getRequestId();

	/**
	 * 获取请求id，如果请求id为空，自动设置一个请求id
	 * @return 返回请求id
	 */
	String getAndSetRequestIdIfEmpty();

	/**
	 * 设置请求id
	 * @param requestId 请求id
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setRequestId(String requestId);

	/**
	 * 获取排序
	 * @return 返回排序
	 */
	int getSort();

	/**
	 * 设置排序
	 * @param sort 排序
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setSort(int sort);

	/**
	 * 获取等待时间 如通过浏览器获取数据时，浏览器页面需要等待的时间
	 * @return 返回等待时间
	 */
	long getWaitTime();

	/**
	 * 设置等待时间
	 * @param waitTime 等待时间
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setWaitTime(long waitTime);

	/**
	 * 获取ContentType
	 * @return 返回ContentType
	 */
	String getContentType();

	/**
	 * 设置ContentType
	 * @param contentType ContentType
	 */
	void setContentType(String contentType);

	/**
	 * 添加单个请求参数
	 * @param name 参数name
	 * @param value 参数值
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest addParameter(String name, String value);

	/**
	 * 设置多个请求参数
	 * @param parameters 多个请求参数map
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setParameters(Map<String, String> parameters);

	/**
	 * 获取单个请求参数值
	 * @param name 参数name
	 * @return 返回对应参数值
	 */
	String getParameter(String name);

	/**
	 * 获取所有参数值
	 * @return 返回参数Map
	 */
	Map<String, String> getParameters();

	/**
	 * 添加一个请求数据
	 * @param name 请求数据name
	 * @param value 请求数据值
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest addData(String name, Object value);

	/**
	 * 设置多个请求数据
	 * @param data 请求数据map
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setData(Map<String, Object> data);

	/**
	 * 获取请求数据map
	 * @return 返回请求数据map
	 */
	Map<String, Object> getData();

	/**
	 * 设置一个请求属性, 请求属性主要用于值的传递
	 * @param name 请求属性name
	 * @param value 请求属性值
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setAttribute(String name, Object value);

	/**
	 * 设置多个请求属性
	 * @param attributes 请求属性map
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setAttributes(Map<String, Object> attributes);

	/**
	 * 获取一个请求属性值
	 * @param name 请求属性name
	 * @return 返回对应请求属性值
	 */
	Object getAttribute(String name);

	/**
	 * 获取一个请求属性字符串值
	 * @param name 请求属性name
	 * @return 返回对应请求属性值
	 */
	String getAttributeString(String name);

	/**
	 * 获取所有的请求属性值
	 * @return 返回请求属性map
	 */
	Map<String, Object> getAttributes();

	/**
	 * 设置一个请求事件
	 * @param eventKey 事件key
	 * @param eventValue 事件值
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setEvent(String eventKey, CrawlerHttpRequestEvent eventValue);

	/**
	 * 设置多个请求事件
	 * @param events 请求事件map
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setEvents(Map<String, CrawlerHttpRequestEvent> events);

	/**
	 * 根据事件key获取对应的事件值
	 * @param eventKey 事件key
	 * @return 返回对应的事件值
	 */
	CrawlerHttpRequestEvent getEvent(String eventKey);

	/**
	 * 获取所有事件
	 * @return 返回事件map
	 */
	Map<String, CrawlerHttpRequestEvent> getEvents();

	/**
	 * 设置单个请求头
	 * @param name 请求头name
	 * @param value 请求头值
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest addHeader(String name, String value);

	/**
	 * 设置多个请求头
	 * @param headerMap 请求头map
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setHeaders(Map<String, String> headerMap);

	/**
	 * 获取所有请求头
	 * @return 返回请求头map
	 */
	Map<String, String> getHeaders();

	/**
	 * 获取所有cookies
	 * @return 返回cookies Map
	 */
	Map<String, String> getCookies();

	/**
	 * 获取所有cookies字符串
	 * @return 返回所有cookies字符串
	 */
	String getCookiesString();

	/**
	 * 添加单个cookie
	 * @param name cookie名称
	 * @param value cookie值
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest addCookie(String name, String value);

	/**
	 * 设置多个cookies值
	 * @param cookiesMap cookies map
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setCookies(Map<String, String> cookiesMap);

	/**
	 * 设置浏览器相关属性Option
	 * @param option 浏览器属性
	 * @return 返回请求接口
	 */
	CrawlerHttpRequest setOption(Option option);

	/**
	 * 获取浏览器相关属性
	 * @return
	 */
	Option getOption();

}
