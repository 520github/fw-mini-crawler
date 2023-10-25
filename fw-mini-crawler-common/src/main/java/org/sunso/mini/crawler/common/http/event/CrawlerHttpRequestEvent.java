package org.sunso.mini.crawler.common.http.event;

public interface CrawlerHttpRequestEvent {

    CrawlerHttpRequestEvent setEventType(String eventType);

    String getEventType();


    CrawlerHttpRequestEvent setEventDoMaxNum(int eventDoMaxNum);

    int getEventDoMaxNum();


    CrawlerHttpRequestEvent setEventEndFlag(String eventEndFlag);

    String getEventEndFlag();
}


