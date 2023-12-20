package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
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

/**
 * @author sunso520
 * @Title: CrawlerAjaxFieldParser
 * @Description: ajax字段解析处理<br>
 * @Created on 2023/10/12 11:07
 */
@Slf4j
public class CrawlerAjaxFieldParser extends AbstractCrawlerFieldParser {

    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        CrawlerFieldParser fieldParser = getCrawlerFieldParser(request.getField());
        if (fieldParser == null) {
            log.warn("CrawlerAjaxFieldParser parse field[{}] not found CrawlerFieldParser", request.getField().getName());
            return null;
        }
        return fieldParser.parseField(newCrawlerFieldParserRequest(request));
    }

    private CrawlerFieldParserRequest newCrawlerFieldParserRequest(CrawlerFieldParserRequest request) {
        return newCrawlerFieldParserRequest(request, ajaxDownload(request));
    }

    /**
     * 获取字段解析器类型
     *
     * @param field 字段
     * @return 返回字段解析器类型
     */
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

    /**
     * 判断是否含有文件存储的注解
     *
     * @param field 字段
     * @return
     */
    private boolean isFileStorage(Field field) {
        return ReflectUtils.isAnnotationPresentRecursion(field, FileStorage.class);
    }

    /**
     * 执行ajax请求，获取返回结果
     *
     * @param request 字段解析请求参数
     * @return 返回ajax请求结果
     */
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
            ajaxRequest.setHeaders(request.getRequest().getHeaders());
        }
        if (ajaxDTO.isCopyCookies()) {
            ajaxRequest.setCookies(request.getRequest().getCookies());
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
