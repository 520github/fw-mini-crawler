package org.sunso.mini.crawler.downloader;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.sunso.mini.crawler.common.enums.HttpRequestEventTypeEnum;
import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEvent;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.downloader.enums.DownloaderExtendKeyEnum;

import java.util.Map;

public class SeleniumCrawlerDownloader implements CrawlerDownloader {
    @Override
    public CrawlerHttpResponse download(CrawlerHttpRequest request) {
        WebDriver webDriver = getWebDriver(request);
        webDriver.get(request.getUrl());
        doEvent(webDriver, request);
        return getCrawlerHttpResponse(webDriver);
    }

    private void doEvent(WebDriver webDriver, CrawlerHttpRequest request) {
        Map<String, CrawlerHttpRequestEvent> events = request.getEvents();
        if (events == null || events.isEmpty()) {
            return;
        }
        for(String eventKey: events.keySet()) {
            doOneEvent(eventKey, events.get(eventKey), webDriver);
        }
    }

    private void doOneEvent(String eventKey, CrawlerHttpRequestEvent eventValue, WebDriver webDriver) {
        if (HttpRequestEventTypeEnum.click.getKey().equalsIgnoreCase(eventValue.getEventType())) {
            doClickEvent(eventKey, eventValue, webDriver);
        }
    }

    private void doClickEvent(String eventKey, CrawlerHttpRequestEvent eventValue, WebDriver webDriver) {
        int doMaxNum = eventValue.getEventDoMaxNum();
        while (doMaxNum > 0) {
            try {
                doMaxNum--;
                WebElement webElement = getWebElement(webDriver, eventKey);
                webElement.click();
            }catch (NoSuchElementException e) {
                e.printStackTrace();
                break;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private WebElement getWebElement(WebDriver webDriver, String eventKey) {
        return webDriver.findElement(By.cssSelector(eventKey));
    }

    private CrawlerHttpResponse getCrawlerHttpResponse(WebDriver webDriver) {
        CrawlerHttpResponse response = new CrawlerHttpResponse(webDriver.getPageSource());
        response.setStatus(200);
        webDriver.quit();
        return response;
    }

    private WebDriver getWebDriver(CrawlerHttpRequest request) {
        String browserType = request.getAttributeString(DownloaderExtendKeyEnum.browserType.getKey());
        if ("chrome".equalsIgnoreCase(browserType)) {
            return new ChromeDriver();
        }
        else if ("safari".equalsIgnoreCase(browserType)) {
            return new SafariDriver();
        }
        return new ChromeDriver();
    }
}
