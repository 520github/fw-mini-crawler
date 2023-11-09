package org.sunso.mini.crawler.common.http.event;

public class CrawlerHttpRequestEventFactory {
    public static CrawlerHttpRequestEvent getClickEvent(String eventEndFlag, int eventDoMaxNum) {
        return new CrawlerHttpRequestClickEvent().setEventDoMaxNum(eventDoMaxNum).setEventEndFlag(eventEndFlag);
    }

    public static CrawlerHttpRequestEvent getInputSetAndMoveCursorEvent(String value) {
        return new CrawlerHttpRequestInputSetAndMoveCursorEvent(value);
    }

    public static CrawlerHttpRequestEvent getCrawlerHttpRequestScrollToBottomEvent(int eventDoMaxNum) {
        return new CrawlerHttpRequestScrollToBottomEvent().setEventDoMaxNum(eventDoMaxNum);
    }
}
