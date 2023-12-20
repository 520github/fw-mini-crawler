package org.sunso.mini.crawler.common.http.event;

/**
 * @author sunso520
 * @Title:CrawlerHttpRequestEvent
 * @Description: 爬虫Http请求触发事件 该触发事件主要针对浏览器爬取数据 包含：
 * <ul>
 * <li>加载页面后点击某个按钮</li>
 * <li>加载页面后滚动鼠标下滑</li>
 * <li>加载页面后，设置某个输入框值，并点击按钮</li>
 * <li>.....等等</li>
 * </ul>
 * @Created on 2023/10/12 10:23
 */
public interface CrawlerHttpRequestEvent {

	/**
	 * 设置事件类型
	 * @param eventType 事件类型
	 * @return 返回请求事件
	 */
	CrawlerHttpRequestEvent setEventType(String eventType);

	/**
	 * 获取事件类型
	 * @return 返回事件类型
	 */
	String getEventType();

	/**
	 * 设置事件值
	 * @param eventValue 事件值
	 * @return 返回请求事件
	 */
	CrawlerHttpRequestEvent setEventValue(String eventValue);

	/**
	 * 获取事件值
	 * @return 返回事件值
	 */
	String getEventValue();

	/**
	 * 设置事件处理的最大次数
	 * @param eventDoMaxNum 事件处理的最大次数
	 * @return 返回请求事件
	 */
	CrawlerHttpRequestEvent setEventDoMaxNum(int eventDoMaxNum);

	/**
	 * 获取事件处理的最大次数
	 * @return 返回事件处理的最大次数
	 */
	int getEventDoMaxNum();

	/**
	 * 设置事件结束标记
	 * @param eventEndFlag 事件结束标记
	 * @return 返回请求事件
	 */
	CrawlerHttpRequestEvent setEventEndFlag(String eventEndFlag);

	/**
	 * 获取事件结束标记
	 * @return 返回事件结束标记
	 */
	String getEventEndFlag();

}
