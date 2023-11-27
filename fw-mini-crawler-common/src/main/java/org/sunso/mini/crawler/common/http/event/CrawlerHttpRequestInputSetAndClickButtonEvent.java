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
    private long clickWait;

    public CrawlerHttpRequestInputSetAndClickButtonEvent(String inputCssPath, String inputValue, long clickWait) {
        this.inputCssPath = inputCssPath;
        this.clickWait = clickWait;
        setEventType(HttpRequestEventTypeEnum.inputSetAndClickButton.getKey());
        setEventValue(inputValue);
    }
}
