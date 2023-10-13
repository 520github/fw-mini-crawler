package org.sunso.mini.crawler.parser.field;

import cn.hutool.json.JSONArray;
import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestFactory;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.downloader.CrawlerDownloaderFactory;
import org.sunso.mini.crawler.parser.json.HuToolJsonFieldParser;
import org.sunso.mini.crawler.parser.json.JsonFieldParser;
import org.sunso.mini.crawler.parser.json.JsonFieldParserFactory;

import java.lang.reflect.Field;
import java.util.List;

public class CrawlerAjaxFieldParser extends AbstractCrawlerFieldParser {

    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        if (request.getField().isAnnotationPresent(JsonPath.class)) {

        }
        else if (request.getField().isAnnotationPresent(HtmlCssPath.class)) {

        }
        return null;
    }

    private Object parseJsonField(String jsonData, CrawlerFieldParserRequest request) {
        Field field = request.getField();
        if (isList(field)) {

        }
        else if (isArray(field)) {

        }
        else if (isCrawlerResult(field)) {

        }
        return null;
    }

    private List<Object> handleJsonList(String jsonData, CrawlerFieldParserRequest request) {

        return null;
    }

    private List<Object> handleJsonList(Class genericClass, String jsonData, CrawlerFieldParserRequest request) {
        Field field = request.getField();
        JsonFieldParser jsonFieldParser = getJsonFieldParser(jsonData, request);
        if (!isCrawlerResult(genericClass)) {
            return (List)jsonFieldParser.selectorObject(field);
        }
        JSONArray jsonArray = (JSONArray)jsonFieldParser.selectorObject(field);
        for(Object json: jsonArray.toArray()) {

        }
        return null;
    }

    private JsonFieldParser getJsonFieldParser(String jsonData, CrawlerFieldParserRequest request) {
        return JsonFieldParserFactory.getJsonFieldParser(jsonData, HuToolJsonFieldParser.class);
    }

    private String ajaxDownload(CrawlerFieldParserRequest request) {
        HtmlAjax htmlAjax = request.getField().getAnnotation(HtmlAjax.class);
        CrawlerHttpRequest ajaxRequest = CrawlerHttpRequestFactory.getCrawlerHttpRequest(htmlAjax.url(), htmlAjax.method());
        CrawlerHttpResponse response = CrawlerDownloaderFactory.getCrawlerDownloader(htmlAjax.downloader()).download(ajaxRequest);
        return response.body();
    }
}
