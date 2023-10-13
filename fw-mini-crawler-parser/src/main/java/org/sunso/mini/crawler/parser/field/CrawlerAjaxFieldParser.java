package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestFactory;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.downloader.CrawlerDownloaderFactory;
import java.lang.reflect.Field;

public class CrawlerAjaxFieldParser extends AbstractCrawlerFieldParser {

    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        CrawlerFieldParser fieldParser = getCrawlerFieldParser(request.getField());
        if (fieldParser == null) {
            return null;
        }
        return fieldParser.parseField(newCrawlerFieldParserRequest(request));
    }

    private CrawlerFieldParserRequest newCrawlerFieldParserRequest(CrawlerFieldParserRequest request) {
        CrawlerFieldParserRequest instance = request.cloneExcludeResponse();
        instance.setResponse(CrawlerHttpResponse.create(ajaxDownload(request)));
        return instance;
    }

    private  CrawlerFieldParser getCrawlerFieldParser(Field field) {
        if (field.isAnnotationPresent(HtmlCssPath.class)) {
            return new CrawlerHtmlFieldParser();
        }
        else if (field.isAnnotationPresent(JsonPath.class)) {
            return new CrawlerJsonFieldParser();
        }
        return null;
    }

    private String ajaxDownload(CrawlerFieldParserRequest request) {
        HtmlAjax htmlAjax = request.getField().getAnnotation(HtmlAjax.class);
        CrawlerHttpRequest ajaxRequest = CrawlerHttpRequestFactory.getCrawlerHttpRequest(htmlAjax.url(), htmlAjax.method());
        ajaxRequest.setContentType(htmlAjax.contentType().getKey());
        CrawlerHttpResponse response = CrawlerDownloaderFactory.getCrawlerDownloader(htmlAjax.downloader()).download(ajaxRequest);
        return response.body();
    }
}
