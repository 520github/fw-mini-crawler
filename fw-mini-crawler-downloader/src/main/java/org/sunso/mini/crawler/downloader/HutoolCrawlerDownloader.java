package org.sunso.mini.crawler.downloader;

import cn.hutool.http.*;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpEmptyRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpGetRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpPostRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;

public class HutoolCrawlerDownloader implements CrawlerDownloader {
    @Override
    public CrawlerHttpResponse download(CrawlerHttpRequest request) {
        if (request instanceof CrawlerHttpEmptyRequest) {
            return null;
        }
        HttpRequest httpRequest = getHttpRequest(request);
        addHeaders(request, httpRequest);
        addCookies(request, httpRequest);
        addData(request, httpRequest);
        HttpResponse response = httpRequest.setFollowRedirects(true).execute();
        //HttpUtil.createRequest(Method.POST, "").cookie();
        System.out.println("do download:" + request.getUrl());
        //HttpResponse response = HttpUtil.createGet(request.getUrl(), true).execute();
        //System.out.println(response.body());
//        InputStream inputStream = response.bodyStream();
//        try {
//            System.out.println("available:" + inputStream.available());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return getCrawlerHttpResponse(response);
    }

    private CrawlerHttpResponse  getCrawlerHttpResponse(HttpResponse response) {
        CrawlerHttpResponse crawlerHttpResponse = new CrawlerHttpResponse();
        crawlerHttpResponse.setIn(response.bodyStream());
        crawlerHttpResponse.setStatus(response.getStatus());
        return crawlerHttpResponse;
    }

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

    private void addJson(CrawlerHttpRequest crawlerHttpRequest, HttpRequest request) {
        request.body(JSONUtil.toJsonStr(crawlerHttpRequest.getData()));
    }

    private void addWwwForm(CrawlerHttpRequest crawlerHttpRequest, HttpRequest request) {
        request.form(crawlerHttpRequest.getData());
    }

    private void addHeaders(CrawlerHttpRequest crawlerHttpRequest, HttpRequest request) {
        request.addHeaders(crawlerHttpRequest.getHeaders());
    }

    private void addCookies(CrawlerHttpRequest crawlerHttpRequest, HttpRequest request) {
        request.cookie(crawlerHttpRequest.getCookiesString());
    }

    private HttpRequest getHttpRequest(CrawlerHttpRequest request) {
        //reqObj.addHeader("User-Agent", UserAgent.getUserAgent(isMobile));
        return HttpUtil.createRequest(getMethod(request), request.getUrl());
    }

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
