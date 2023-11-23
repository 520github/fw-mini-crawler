package org.sunso.mini.crawler.common.http.event;

import lombok.Data;
import org.sunso.mini.crawler.common.enums.HttpRequestEventTypeEnum;

/**
 * @author sunso520
 * @Title:CrawlerHttpRequestInputSetAndClickButtonEvent
 * @Description: <br>
 * @Created on 2023/11/23 15:23
 */
@Data
public class CrawlerHttpRequestInputSetAndClickButtonEvent extends AbstractCrawlerHttpRequestEvent {
    private String inputCssPath;

    public CrawlerHttpRequestInputSetAndClickButtonEvent(String inputCssPath, String inputValue) {
        this.inputCssPath = inputCssPath;
        setEventType(HttpRequestEventTypeEnum.inputSetAndClickButton.getKey());
        setEventValue(inputValue);
    }
}
