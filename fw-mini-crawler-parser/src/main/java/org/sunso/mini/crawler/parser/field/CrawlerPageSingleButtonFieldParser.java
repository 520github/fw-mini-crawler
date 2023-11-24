package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.netty.util.internal.StringUtil;
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
 * @Description: <br>
 * @Created on 2023/10/27 17:30
 */
public class CrawlerPageSingleButtonFieldParser extends AbstractCrawlerFieldParser{
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        Field field = request.getField();
        HtmlPageSingleButton htmlPageButton = getHtmlPageButton(field);
        List<Object> resultList = null;
        //单页面点击按钮，不断追加数据
        if (htmlPageButton.singlePageAppendData()) {
            resultList = handleSinglePageAppendData(htmlPageButton, request);
        }
        else {
            resultList =  handleAlonePageData(htmlPageButton, request);
        }
        if (resultList == null || resultList.isEmpty()) {
            return resultList;
        }
        if (htmlPageButton.startDataIndex() <=0 && htmlPageButton.endDataIndex() >= resultList.size()-1) {
            return resultList;
        }
        return resultList.subList(htmlPageButton.startDataIndex(), htmlPageButton.endDataIndex());
    }

    private  CrawlerFieldParser getCrawlerFieldParser() {
        return new CrawlerHtmlFieldParser();
    }

    private List<Object> handleSinglePageAppendData(HtmlPageSingleButton htmlPageButton, CrawlerFieldParserRequest request) {
        CrawlerHttpResponse response =  seleniumDownload(htmlPageButton, request, 1);
        return parseResponse(request, response);
    }

    private List<Object> parseResponse(CrawlerFieldParserRequest request, CrawlerHttpResponse response) {
        return (List<Object>) getCrawlerFieldParser().parseField(newCrawlerFieldParserRequest(request, response));
    }

    private List<Object> handleAlonePageData(HtmlPageSingleButton htmlPageButton, CrawlerFieldParserRequest request) {
        List<Object> resultList = new ArrayList<>();
        for(int i=1; i<=htmlPageButton.eventDoMaxNum(); i++) {
            CrawlerHttpResponse response = seleniumDownload(htmlPageButton, request, i);
            List<Object> dataList = parseResponse(request, response);
            if (dataList == null || dataList.isEmpty()) {
                break;
            }
            resultList.addAll(dataList);
        }
        return resultList;
    }

    private CrawlerHttpResponse seleniumDownload(HtmlPageSingleButton htmlPageButton, CrawlerFieldParserRequest request, int currentPage) {
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

    private CrawlerHttpRequestEvent getCrawlerHttpRequestEvent(HtmlPageSingleButton htmlPageButton, int currentPage) {
        if (HttpRequestEventTypeEnum.click == htmlPageButton.eventType()) {
            return CrawlerHttpRequestEventFactory.getClickEvent(htmlPageButton.eventEndFlag().getKey(), htmlPageButton.eventDoMaxNum());
        }
        else if (HttpRequestEventTypeEnum.inputSetAndMoveCursor == htmlPageButton.eventType()) {
            return CrawlerHttpRequestEventFactory.getInputSetAndMoveCursorEvent(String.valueOf(currentPage));
        }
        else if (HttpRequestEventTypeEnum.inputSetAndClickButton == htmlPageButton.eventType()) {
            return CrawlerHttpRequestEventFactory.getCrawlerHttpRequestInputSetAndClickButtonEvent(getInputCssPath(htmlPageButton), String.valueOf(currentPage));
        }
        return null;
    }

    private String getInputCssPath(HtmlPageSingleButton htmlPageButton) {
        if (htmlPageButton == null) {
            return null;
        }
        String extendData = htmlPageButton.extendData();
        if (StrUtil.isBlank(extendData)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSONUtil.parseObj(extendData);
            return jsonObject.getStr("inputCssPath");
        }catch (Exception e) {
            e.printStackTrace();
            // todo  log
        }
        return null;
    }

    private HtmlPageSingleButton getHtmlPageButton(Field field) {
        return field.getAnnotation(HtmlPageSingleButton.class);
    }
}
