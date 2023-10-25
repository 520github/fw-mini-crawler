package org.sunso.mini.crawler.common.http.event;

public abstract class AbstractCrawlerHttpRequestEvent implements CrawlerHttpRequestEvent{

    private String eventType;
    private int eventDoMaxNum = 1;

    private String eventEndFlag = "";

    public CrawlerHttpRequestEvent setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public String getEventType() {
        return eventType;
    }


    public CrawlerHttpRequestEvent setEventDoMaxNum(int eventDoMaxNum) {
        this.eventDoMaxNum = eventDoMaxNum;
        return this;
    }

    public int getEventDoMaxNum() {
        return eventDoMaxNum;
    }


    public CrawlerHttpRequestEvent setEventEndFlag(String eventEndFlag) {
        this.eventEndFlag = eventEndFlag;
        return this;
    }

    public String getEventEndFlag() {
        return eventEndFlag;
    }
}
