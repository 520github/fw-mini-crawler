package org.sunso.mini.crawler.downloader;

import cn.hutool.http.*;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpEmptyRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpGetRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpPostRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

/**
 * @author sunso520
 * @Title:HutoolCrawlerDownloader
 * @Description: 基于Hutool处理http请求的爬虫下载器<br>
 * @Created on 2023/10/12 11:26
 */
@Slf4j
public class HutoolCrawlerDownloader implements CrawlerDownloader {
    @Override
    public CrawlerHttpResponse download(CrawlerHttpRequest request) {
        if (request instanceof CrawlerHttpEmptyRequest) {
            return null;
        }
        HttpRequest httpRequest = getHttpRequest(request);
        // 添加请求头
        addHeaders(request, httpRequest);
        // 添加cookies
        addCookies(request, httpRequest);
        // 添加请求数据
        addData(request, httpRequest);
        HttpResponse response = httpRequest.setFollowRedirects(true).execute();
        log.info("HutoolCrawlerDownloader do url[{}] and response status[{}]", request.getUrl(), response.getStatus());
        return getCrawlerHttpResponse(response);
    }

    /**
     * 把Hutool对应http请求结果转化成爬虫Http请求的响应结果
     * @param response Hutool对应http请求结果
     * @return
     */
    private CrawlerHttpResponse  getCrawlerHttpResponse(HttpResponse response) {
        CrawlerHttpResponse crawlerHttpResponse = new CrawlerHttpResponse();
        crawlerHttpResponse.setIn(response.bodyStream());
        crawlerHttpResponse.setStatus(response.getStatus());
        return crawlerHttpResponse;
    }

    /**
     * 向Hutool request添加请求数据
     * @param crawlerHttpRequest 爬虫http请求
     * @param request Hutool对应的http请求
     */
    private void addData(CrawlerHttpRequest crawlerHttpRequest, HttpRequest request) {
        String contentType = crawlerHttpRequest.getContentType();
        if (contentType == null) {
            return;
        }
        if (contentType.contains("application/json")) {
            addJson(crawlerHttpRequest, request);
        }
        else if (contentType.contains("application/x-www-form-urlencoded")) {
            addWwwForm(crawlerHttpRequest, request);
        }
    }

    /**
     * 向Hutool request添加json格式请求数据
     * @param crawlerHttpRequest 爬虫http请求
     * @param request Hutool对应的http请求
     */
    private void addJson(CrawlerHttpRequest crawlerHttpRequest, HttpRequest request) {
        request.body(JSONUtil.toJsonStr(crawlerHttpRequest.getData()));
    }

    /**
     * 向Hutool request添加form格式请求数据
     * @param crawlerHttpRequest 爬虫http请求
     * @param request Hutool对应的http请求
     */
    private void addWwwForm(CrawlerHttpRequest crawlerHttpRequest, HttpRequest request) {
        request.form(crawlerHttpRequest.getData());
    }

    /**
     * 向Hutool request添加请求头
     * @param crawlerHttpRequest 爬虫http请求
     * @param request Hutool对应的http请求
     */
    private void addHeaders(CrawlerHttpRequest crawlerHttpRequest, HttpRequest request) {
        request.addHeaders(crawlerHttpRequest.getHeaders());
    }

    /**
     * 向Hutool request添加请求cookies
     * @param crawlerHttpRequest 爬虫http请求
     * @param request Hutool对应的http请求
     */
    private void addCookies(CrawlerHttpRequest crawlerHttpRequest, HttpRequest request) {
        request.cookie(crawlerHttpRequest.getCookiesString());
    }

    /**
     * 获取Hutool request对象
     * @param request 爬虫http请求
     * @return
     */
    private HttpRequest getHttpRequest(CrawlerHttpRequest request) {
        return HttpUtil.createRequest(getMethod(request), request.getUrl());
    }

    /**
     * 获取请求方式
     * @param request 爬虫http请求
     * @return
     */
    private Method getMethod(CrawlerHttpRequest request) {
        if (request instanceof CrawlerHttpGetRequest) {
            return Method.GET;
        }
        if (request instanceof CrawlerHttpPostRequest) {
            return Method.POST;
        }
        return null;
    }
}
