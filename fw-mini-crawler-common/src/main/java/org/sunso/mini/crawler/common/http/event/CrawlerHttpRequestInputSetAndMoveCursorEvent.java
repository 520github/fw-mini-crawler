package org.sunso.mini.crawler.common.http.event;

import org.sunso.mini.crawler.common.enums.HttpRequestEventTypeEnum;

/**
 * @author sunso520
 * @Title:CrawlerHttpRequestInputSetAndMoveCursorEvent
 * @Description: <br>
 * @Created on 2023/10/26 15:26
 */
public class CrawlerHttpRequestInputSetAndMoveCursorEvent extends AbstractCrawlerHttpRequestEvent {

	public CrawlerHttpRequestInputSetAndMoveCursorEvent(String value) {
		setEventType(HttpRequestEventTypeEnum.inputSetAndMoveCursor.getKey());
		setEventValue(value);
	}

}
