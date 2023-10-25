package org.sunso.mini.crawler.common.http.event;

public class CrawlerHttpRequestEventFactory {
    public static CrawlerHttpRequestEvent getClickEvent(String eventEndFlag, int eventDoMaxNum) {
        return new CrawlerHttpRequestClickEvent().setEventDoMaxNum(eventDoMaxNum).setEventEndFlag(eventEndFlag);
    }
}
