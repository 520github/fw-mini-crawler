package org.sunso.mini.crawler.downloader;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.sunso.mini.crawler.common.enums.HttpRequestEventTypeEnum;
import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEvent;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.downloader.enums.DownloaderExtendKeyEnum;

import java.time.Duration;
import java.util.Map;

/**
 * @author sunso520
 * @Title:AbstractSeleniumCrawlerDownloader
 * @Description: <br>
 * @Created on 2023/10/30 09:03
 */
public abstract class AbstractSeleniumCrawlerDownloader implements CrawlerDownloader {

    protected CrawlerHttpResponse doDownload(CrawlerHttpRequest request) {
        WebDriver webDriver = null;
        try {
            webDriver = getWebDriver(request);
            webDriver.get(request.getUrl());

            waitTime(webDriver, request);
            doEvent(webDriver, request);
            return getCrawlerHttpResponse(webDriver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            quit(webDriver);
        }
        return null;
    }

    protected void doEvent(WebDriver webDriver, CrawlerHttpRequest request) {
        Map<String, CrawlerHttpRequestEvent> events = request.getEvents();
        if (events == null || events.isEmpty()) {
            return;
        }
        for(String eventKey: events.keySet()) {
            doOneEvent(eventKey, events.get(eventKey), webDriver);
        }
    }

    protected void waitTime(WebDriver webDriver, CrawlerHttpRequest request) {
        long waitTime = request.getWaitTime();
        if (waitTime > 0) {
            webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(waitTime));
        }
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doOneEvent(String eventKey, CrawlerHttpRequestEvent eventValue, WebDriver webDriver) {
        if (HttpRequestEventTypeEnum.click.getKey().equalsIgnoreCase(eventValue.getEventType())) {
            doClickEvent(eventKey, eventValue, webDriver);
        }
        else if (HttpRequestEventTypeEnum.inputSetAndMoveCursor.getKey().equalsIgnoreCase(eventValue.getEventType())) {
            doInputSetAndMoveCursor(eventKey, eventValue, webDriver);
        }
        else if (HttpRequestEventTypeEnum.scrollToBottom.getKey().equalsIgnoreCase(eventValue.getEventType())) {
            doScrollToBottom(eventKey, eventValue, webDriver);
        }
    }

    protected void doClickEvent(String eventKey, CrawlerHttpRequestEvent eventValue, WebDriver webDriver) {
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

    protected void doInputSetAndMoveCursor(String eventKey, CrawlerHttpRequestEvent eventValue, WebDriver webDriver) {
        try {
            WebElement webElement = getWebElement(webDriver, eventKey);
            webElement.clear();
            webElement.sendKeys(eventValue.getEventValue());
            emptyClick(webDriver);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doScrollToBottom(String eventKey, CrawlerHttpRequestEvent eventValue, WebDriver webDriver) {
        int lastHeight = getScrollHeight(webDriver);
        System.out.println("lastHeight:" + lastHeight);
        int doMaxNum = eventValue.getEventDoMaxNum();
        while (doMaxNum > 0) {
            try {
                doMaxNum--;
                getJavascriptExecutor(webDriver).executeScript("window.scrollTo(0,document.body.scrollHeight);");
                Thread.sleep(2000);
                int newHeight = getScrollHeight(webDriver);
                if (lastHeight == newHeight) {
                    break;
                }
                lastHeight = newHeight;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int getScrollHeight(WebDriver webDriver) {
        Object scrollHeight = getJavascriptExecutor(webDriver).executeScript("return document.body.scrollHeight");
        if (scrollHeight == null) {
            return 0;
        }
        return Integer.parseInt(scrollHeight.toString());
    }

    protected JavascriptExecutor getJavascriptExecutor(WebDriver webDriver) {
        return (JavascriptExecutor)webDriver;
    }

    protected void emptyClick(WebDriver webDriver) {
        Actions actions = new Actions(webDriver);
        actions.click().perform();
    }

    protected WebElement getWebElement(WebDriver webDriver, String eventKey) {
        return webDriver.findElement(By.cssSelector(eventKey));
    }

    protected CrawlerHttpResponse getCrawlerHttpResponse(WebDriver webDriver) {
        CrawlerHttpResponse response = new CrawlerHttpResponse(webDriver.getPageSource());
        response.setStatus(200);
        webDriver.quit();
        return response;
    }

    protected void quit(WebDriver webDriver) {
        if (webDriver == null) {
            return;
        }
        webDriver.quit();;
    }

    protected WebDriver getWebDriver(CrawlerHttpRequest request) {
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
