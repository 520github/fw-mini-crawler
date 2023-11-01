package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.storage.FileStorage;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestFactory;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.utils.ReflectUtils;
import org.sunso.mini.crawler.common.utils.UrlUtils;
import org.sunso.mini.crawler.downloader.CrawlerDownloaderFactory;
import org.sunso.mini.crawler.parser.dto.HtmlAjaxDTO;

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
        return newCrawlerFieldParserRequest(request, ajaxDownload(request));
    }

    private  CrawlerFieldParser getCrawlerFieldParser(Field field) {
        if (field.isAnnotationPresent(HtmlCssPath.class)) {
            return new CrawlerHtmlFieldParser();
        }
        else if (field.isAnnotationPresent(JsonPath.class)) {
            return new CrawlerJsonFieldParser();
        }
        else if (isFileStorage(field)) {
            return new CrawlerStorageFileFieldParser();
        }
        return null;
    }

    private boolean isFileStorage(Field field) {
        return ReflectUtils.isAnnotationPresentRecursion(field, FileStorage.class);
    }

    private String ajaxDownloadBody(CrawlerFieldParserRequest request) {
        return ajaxDownload(request).body();
    }

    private byte[] ajaxDownloadBytes(CrawlerFieldParserRequest request) {
        return ajaxDownload(request).bodyBytes();
    }

    private CrawlerHttpResponse ajaxDownload(CrawlerFieldParserRequest request) {
        HtmlAjax htmlAjax = request.getField().getAnnotation(HtmlAjax.class);
        HtmlAjaxDTO ajaxDTO = request.getHtmlAjaxDTO();
        if (ajaxDTO == null) {
            ajaxDTO = HtmlAjaxDTO.newInstance(htmlAjax);
        }
        String url = ajaxDTO.getUrl();
        url = UrlUtils.replaceParams(url, request.fetchAllReplaceParams());
        setHtmlAjaxUrl2RequestAttribute(request, ajaxDTO.getRequestAttributeName(), url);
        CrawlerHttpRequest ajaxRequest = CrawlerHttpRequestFactory.getCrawlerHttpRequest(url, ajaxDTO.getMethod());
        if (ajaxDTO.isCopyHeader()) {
            ajaxRequest.addHeaders(request.getRequest().getHeaders());
        }
        if (ajaxDTO.isCopyCookies()) {
            ajaxRequest.addCookies(request.getRequest().getCookies());
        }
        if (ajaxDTO.isCopyAttribute()) {
            ajaxRequest.setAttributes(request.getRequest().getAttributes());
        }
        ajaxRequest.setContentType(ajaxDTO.getContentType().getKey());
        return CrawlerDownloaderFactory.getCrawlerDownloader(ajaxDTO.getDownloader()).download(ajaxRequest);
    }

    private void setHtmlAjaxUrl2RequestAttribute(CrawlerFieldParserRequest request, String name, String url) {
        if (StrUtil.isBlank(name)) {
            return ;
        }
        request.commitRequestAttribute(name, url);
    }
}
