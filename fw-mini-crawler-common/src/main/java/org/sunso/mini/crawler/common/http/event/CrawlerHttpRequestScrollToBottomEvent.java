package org.sunso.mini.crawler.common.http.event;

import org.sunso.mini.crawler.common.enums.HttpRequestEventTypeEnum;

/**
 * @author sunso520
 * @Title:CrawlerHttpRequestScrollToBottomEvent
 * @Description: <br>
 * @Created on 2023/11/9 16:37
 */
public class CrawlerHttpRequestScrollToBottomEvent extends AbstractCrawlerHttpRequestEvent {

	public CrawlerHttpRequestScrollToBottomEvent() {
		setEventType(HttpRequestEventTypeEnum.scrollToBottom.getKey());
	}

}
