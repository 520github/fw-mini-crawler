package org.sunso.mini.crawler.downloader;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.sunso.mini.crawler.common.enums.HttpRequestEventTypeEnum;
import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEvent;
import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestInputSetAndClickButtonEvent;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.downloader.enums.DownloaderExtendKeyEnum;

import java.time.Duration;
import java.util.Map;

/**
 * @author sunso520
 * @Title:AbstractSeleniumCrawlerDownloader
 * @Description: 基于Selenium处理http请求的爬虫下载器抽象类<br>
 * @Created on 2023/10/30 09:03
 */
@Slf4j
public abstract class AbstractSeleniumCrawlerDownloader extends AbstractCrawlerDownloader {

	/**
	 * 下载爬虫http请求对应内容
	 * @param request 爬虫http请求对象
	 * @return
	 */
	protected CrawlerHttpResponse doDownload(CrawlerHttpRequest request) {
		WebDriver webDriver = null;
		try {
			webDriver = getWebDriver(request);
			webDriver.get(request.getUrl());

			waitTime(webDriver, request);
			doEvent(webDriver, request);
			return getCrawlerHttpResponse(webDriver);
		}
		catch (Exception e) {
			log.error(String.format("AbstractSeleniumCrawlerDownloader download url[%s] exception[%s]",
					request.getUrl(), e.getMessage()), e);
		}
		finally {
			quit(webDriver);
		}
		return null;
	}

	/**
	 * 触发多个事件
	 * @param webDriver WebDriver
	 * @param request 爬虫http请求对象
	 */
	protected void doEvent(WebDriver webDriver, CrawlerHttpRequest request) {
		Map<String, CrawlerHttpRequestEvent> events = request.getEvents();
		if (events == null || events.isEmpty()) {
			return;
		}
		for (String eventKey : events.keySet()) {
			doOneEvent(eventKey, events.get(eventKey), webDriver);
		}
	}

	protected void waitTime(WebDriver webDriver, CrawlerHttpRequest request) {
		long waitTime = request.getWaitTime();
		if (waitTime > 0) {
			webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(waitTime));
		}
		sleep(waitTime);
	}

	protected void sleep(long waitTime) {
		if (waitTime <= 0) {
			return;
		}
		try {
			Thread.sleep(waitTime);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 触发一个事件
	 * @param eventKey 事件key
	 * @param eventValue 事件对应值
	 * @param webDriver WebDriver
	 */
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
		else if (HttpRequestEventTypeEnum.inputSetAndClickButton.getKey().equalsIgnoreCase(eventValue.getEventType())) {
			doInputSetAndClickButton(eventKey, (CrawlerHttpRequestInputSetAndClickButtonEvent) eventValue, webDriver);
		}
	}

	/**
	 * 向本文框输入内容后，点击某个按钮
	 * @param eventKey 事件key
	 * @param eventValue 事件对应值
	 * @param webDriver WebDriver
	 */
	protected void doInputSetAndClickButton(String eventKey, CrawlerHttpRequestInputSetAndClickButtonEvent eventValue,
			WebDriver webDriver) {
		WebElement inputElement = getWebElement(webDriver, eventValue.getInputCssPath());
		inputElement.clear();
		inputElement.sendKeys(eventValue.getEventValue());
		WebElement webElement = getWebElement(webDriver, eventKey);
		webElement.click();
		sleep(eventValue.getClickWait());
	}

	/**
	 * 点击按钮
	 * @param eventKey 事件key
	 * @param eventValue 事件对应值
	 * @param webDriver WebDriver
	 */
	protected void doClickEvent(String eventKey, CrawlerHttpRequestEvent eventValue, WebDriver webDriver) {
		int doMaxNum = eventValue.getEventDoMaxNum();
		while (doMaxNum > 0) {
			try {
				doMaxNum--;
				WebElement webElement = getWebElement(webDriver, eventKey);
				webElement.click();
			}
			catch (NoSuchElementException e) {
				log.info("doClickEvent find NoSuchElementException by eventKey[{}]", eventKey);
				break;
			}
			catch (Exception e) {
				log.error("doClickEvent exception", e);
			}
		}
	}

	/**
	 * 向输入框设置内容，并移开光标
	 * @param eventKey 事件key
	 * @param eventValue 事件对应值
	 * @param webDriver WebDriver
	 */
	protected void doInputSetAndMoveCursor(String eventKey, CrawlerHttpRequestEvent eventValue, WebDriver webDriver) {
		try {
			WebElement webElement = getWebElement(webDriver, eventKey);
			webElement.clear();
			webElement.sendKeys(eventValue.getEventValue());
			emptyClick(webDriver);
		}
		catch (Exception e) {
			log.error("doInputSetAndMoveCursor exception", e);
		}
	}

	/**
	 * 滚动到浏览器窗口底部
	 * @param eventKey 事件key
	 * @param eventValue 事件对应值
	 * @param webDriver WebDriver
	 */
	protected void doScrollToBottom(String eventKey, CrawlerHttpRequestEvent eventValue, WebDriver webDriver) {
		int lastHeight = getScrollHeight(webDriver);
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
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取浏览器屏幕滚动的高度
	 * @param webDriver
	 * @return
	 */
	private int getScrollHeight(WebDriver webDriver) {
		Object scrollHeight = getJavascriptExecutor(webDriver).executeScript("return document.body.scrollHeight");
		if (scrollHeight == null) {
			return 0;
		}
		return Integer.parseInt(scrollHeight.toString());
	}

	/**
	 * 获取Javascript执行器
	 * @param webDriver
	 * @return
	 */
	protected JavascriptExecutor getJavascriptExecutor(WebDriver webDriver) {
		return (JavascriptExecutor) webDriver;
	}

	/**
	 * 空点击
	 * @param webDriver WebDriver
	 */
	protected void emptyClick(WebDriver webDriver) {
		Actions actions = new Actions(webDriver);
		actions.click().perform();
	}

	/**
	 * 根据事件key，获取事件对应的浏览器元素
	 * @param webDriver WebDriver
	 * @param eventKey 事件key
	 * @return
	 */
	protected WebElement getWebElement(WebDriver webDriver, String eventKey) {
		return webDriver.findElement(By.cssSelector(eventKey));
	}

	protected CrawlerHttpResponse getCrawlerHttpResponse(WebDriver webDriver) {
		CrawlerHttpResponse response = new CrawlerHttpResponse(webDriver.getPageSource());
		response.setStatus(200);
		webDriver.quit();
		return response;
	}

	/**
	 * 退出浏览器
	 * @param webDriver
	 */
	protected void quit(WebDriver webDriver) {
		if (webDriver == null) {
			return;
		}
		webDriver.quit();
		;
	}

	/**
	 * 获取WebDriver对象
	 * @param request 爬虫http请求对象
	 * @return
	 */
	protected WebDriver getWebDriver(CrawlerHttpRequest request) {
		String browserType = request.getAttributeString(DownloaderExtendKeyEnum.browserType.getKey());
		ChromiumOptions options = SeleniumOption.getOption(request);
		if (SeleniumOption.isChrome(browserType)) {
			return new ChromeDriver((ChromeOptions) options);
		}
		else if (SeleniumOption.isSafari(browserType)) {
			return new SafariDriver();
		}
		return new ChromeDriver((ChromeOptions) options);
	}

}
