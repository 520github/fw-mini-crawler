package org.sunso.mini.crawler.common.http.event;

import org.sunso.mini.crawler.common.enums.HttpRequestEventTypeEnum;

public class CrawlerHttpRequestClickEvent extends AbstractCrawlerHttpRequestEvent {

    public CrawlerHttpRequestClickEvent() {
        setEventType(HttpRequestEventTypeEnum.click.getKey());
    }
}
