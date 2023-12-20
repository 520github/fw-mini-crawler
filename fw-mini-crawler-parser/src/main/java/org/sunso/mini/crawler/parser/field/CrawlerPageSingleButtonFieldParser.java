package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.annotation.html.HtmlPageSingleButton;
import org.sunso.mini.crawler.common.enums.HttpRequestEventTypeEnum;
import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEvent;
import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEventFactory;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.utils.UrlUtils;
import org.sunso.mini.crawler.downloader.CrawlerDownloaderFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunso520
 * @Title:CrawlerPageButtonFieldParser
 * @Description: 字段为浏览器点击按钮获取分页数据的解析器<br>
 * @Created on 2023/10/27 17:30
 */
@Slf4j
public class CrawlerPageSingleButtonFieldParser extends AbstractCrawlerFieldParser {

	@Override
	public Object parseField(CrawlerFieldParserRequest request) {
		Field field = request.getField();
		HtmlPageSingleButton htmlPageButton = getHtmlPageButton(field);
		List<Object> resultList = null;
		// 单页面点击按钮，不断追加数据
		if (htmlPageButton.singlePageAppendData()) {
			resultList = handleSinglePageAppendData(htmlPageButton, request);
		}
		// 每次分页数据都需要重新打开一个浏览器进行操作
		else {
			resultList = handleAlonePageData(htmlPageButton, request);
		}
		if (resultList == null || resultList.isEmpty()) {
			return resultList;
		}
		if (htmlPageButton.startDataIndex() <= 0 && htmlPageButton.endDataIndex() >= resultList.size() - 1) {
			return resultList;
		}
		// 指定数据的开始位置和结束位置
		return resultList.subList(htmlPageButton.startDataIndex(), htmlPageButton.endDataIndex());
	}

	/**
	 * 处理在一个页面上点击分页按钮，不断追加分页数据的情况
	 * @param htmlPageButton HtmlPageSingleButton注解
	 * @param request 字段解析请求参数
	 * @return
	 */
	private List<Object> handleSinglePageAppendData(HtmlPageSingleButton htmlPageButton,
			CrawlerFieldParserRequest request) {
		CrawlerHttpResponse response = seleniumDownload(htmlPageButton, request, 1);
		return parseResponse(request, response);
	}

	/**
	 * 处理在一个页面上点击分页按钮，只显示当前页数据的情况
	 * @param htmlPageButton HtmlPageSingleButton注解
	 * @param request 字段解析请求参数
	 * @return
	 */
	private List<Object> handleAlonePageData(HtmlPageSingleButton htmlPageButton, CrawlerFieldParserRequest request) {
		List<Object> resultList = new ArrayList<>();
		for (int i = htmlPageButton.startPage(); i <= htmlPageButton.eventDoMaxNum(); i++) {
			CrawlerHttpResponse response = seleniumDownload(htmlPageButton, request, i);
			List<Object> dataList = parseResponse(request, response);
			if (dataList == null || dataList.isEmpty()) {
				break;
			}
			resultList.addAll(dataList);
		}
		return resultList;
	}

	private CrawlerFieldParser getCrawlerFieldParser() {
		return new CrawlerHtmlFieldParser();
	}

	/**
	 * 解析处理每一页的数据结果
	 * @param request 字段解析请求参数
	 * @param response 分页每一页的数据结果
	 * @return
	 */
	private List<Object> parseResponse(CrawlerFieldParserRequest request, CrawlerHttpResponse response) {
		return (List<Object>) getCrawlerFieldParser().parseField(newCrawlerFieldParserRequest(request, response));
	}

	/**
	 * 通过selenium获取指定页的分页数据
	 * @param htmlPageButton HtmlPageSingleButton注解
	 * @param request 字段解析请求参数
	 * @param currentPage 指定分页数
	 * @return
	 */
	private CrawlerHttpResponse seleniumDownload(HtmlPageSingleButton htmlPageButton, CrawlerFieldParserRequest request,
			int currentPage) {
		String url = htmlPageButton.url();
		url = UrlUtils.replaceParams(url, request.fetchAllReplaceParams());
		CrawlerHttpRequest pageRequest = CrawlerHttpRequestBuilder.get(url);
		pageRequest.setEvent(htmlPageButton.cssSelector(), getCrawlerHttpRequestEvent(htmlPageButton, currentPage));
		pageRequest.setWaitTime(htmlPageButton.waitTime());
		if (htmlPageButton.copyOption()) {
			pageRequest.setOption(request.fetchOption());
		}
		return CrawlerDownloaderFactory.getSeleniumCrawlerDownloader().download(pageRequest);
	}

	/**
	 * 获取分页对应的请求触发事件
	 * @param htmlPageButton HtmlPageSingleButton 注解
	 * @param currentPage 当前页数
	 * @return
	 */
	private CrawlerHttpRequestEvent getCrawlerHttpRequestEvent(HtmlPageSingleButton htmlPageButton, int currentPage) {
		if (HttpRequestEventTypeEnum.click == htmlPageButton.eventType()) {
			return CrawlerHttpRequestEventFactory.getClickEvent(htmlPageButton.eventEndFlag().getKey(),
					htmlPageButton.eventDoMaxNum());
		}
		else if (HttpRequestEventTypeEnum.inputSetAndMoveCursor == htmlPageButton.eventType()) {
			return CrawlerHttpRequestEventFactory.getInputSetAndMoveCursorEvent(String.valueOf(currentPage));
		}
		else if (HttpRequestEventTypeEnum.inputSetAndClickButton == htmlPageButton.eventType()) {
			return CrawlerHttpRequestEventFactory.getCrawlerHttpRequestInputSetAndClickButtonEvent(
					getInputCssPath(htmlPageButton), String.valueOf(currentPage), getClickWait(htmlPageButton));
		}
		return null;
	}

	private long getClickWait(HtmlPageSingleButton htmlPageButton) {
		String clickWait = getExtendDataValue(htmlPageButton, "clickWait");
		try {
			return Long.parseLong(clickWait);
		}
		catch (Exception e) {
			return 0;
		}
	}

	private String getInputCssPath(HtmlPageSingleButton htmlPageButton) {
		return getExtendDataValue(htmlPageButton, "inputCssPath");
	}

	/**
	 * 根据指定key获取对应扩展参数值
	 * @param htmlPageButton HtmlPageSingleButton注解
	 * @param key 扩展参数key
	 * @return 返回对应扩展参数值
	 */
	private String getExtendDataValue(HtmlPageSingleButton htmlPageButton, String key) {
		if (htmlPageButton == null) {
			return null;
		}
		String extendData = htmlPageButton.extendData();
		if (StrUtil.isBlank(extendData)) {
			return null;
		}
		try {
			JSONObject jsonObject = JSONUtil.parseObj(extendData);
			return jsonObject.getStr(key);
		}
		catch (Exception e) {
			log.error(
					String.format("CrawlerPageSingleButtonFieldParser get key[%s] value from extendData exception[%s]",
							key, e.getMessage()),
					e);
		}
		return null;
	}

	private HtmlPageSingleButton getHtmlPageButton(Field field) {
		return field.getAnnotation(HtmlPageSingleButton.class);
	}

}
