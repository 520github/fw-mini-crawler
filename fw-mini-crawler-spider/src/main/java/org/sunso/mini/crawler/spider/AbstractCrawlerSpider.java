package org.sunso.mini.crawler.spider;

import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.utils.UrlUtils;
import org.sunso.mini.crawler.context.CrawlerContext;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.downloader.CrawlerDownloaderFactory;
import org.sunso.mini.crawler.downloader.EmptyCrawlerDownloader;
import org.sunso.mini.crawler.parser.CrawlerParser;
import org.sunso.mini.crawler.parser.CrawlerParserFactory;
import org.sunso.mini.crawler.parser.EmptyCrawlerParser;
import org.sunso.mini.crawler.task.CrawlerTask;

import java.util.Map;

public abstract class AbstractCrawlerSpider implements CrawlerSpider {
    protected CrawlerContext context;

    protected  AbstractCrawlerSpider(CrawlerContext context) {
        this.context = context;
    }

    protected CrawlerDownloader getDownloader(Class<? extends CrawlerResult> clazz) {
        CrawlerResultDefine crawlerResultDefine = getCrawlerResultDefine(clazz);
        if (crawlerResultDefine != null &&  !EmptyCrawlerDownloader.class.equals(crawlerResultDefine.downloader())) {
            return CrawlerDownloaderFactory.getCrawlerDownloader(crawlerResultDefine.downloader());
        }
        if (context.getDownloader() != null) {
            return context.getDownloader();
        }
        return CrawlerDownloaderFactory.getDefaultCrawlerDownloader();
    }

    protected CrawlerParser getCrawlerParser(Class<? extends CrawlerResult> clazz) {
        CrawlerResultDefine crawlerResultDefine = getCrawlerResultDefine(clazz);
        if (crawlerResultDefine != null && !EmptyCrawlerParser.class.equals(crawlerResultDefine.parser())) {
            return CrawlerParserFactory.getCrawlerParser(crawlerResultDefine.parser());
        }
        if (context.getParser() != null) {
            return context.getParser();
        }
        return CrawlerParserFactory.getDefaultCrawlerParser();
    }

    protected CrawlerHttpRequest getRequestFromCrawlerQueue() {
        return context.getQueue().poll();
    }


    protected CrawlerHttpRequest getRequestFromCrawlerTask() {
        return context.getTask().pollTask();
    }

    protected CrawlerTask getCrawlerTask() {
        return context.fetchTask();
    }

    protected void doRequest() {
        doRequest(getRequestFromCrawlerTask());
    }

    protected void doRequest(CrawlerHttpRequest request) {
        if (request == null) {
            System.out.println("没有需要爬取的url，退出");
            return ;
        }
        System.out.println("start download:" + Thread.currentThread().getName());
        Class<? extends CrawlerResult> clazz = getCrawlerResultClass(request);
        if (clazz == null) {
            return ;
        }
        CrawlerHttpResponse response = getDownloader(clazz).download(request);
        //System.out.println("body:" + response.body());
        CrawlerResult crawlerResult = getCrawlerParser(clazz).parse(clazz, request, response);
        //System.out.println("crawlerResult:" + crawlerResult);

        getCrawlerTask().doneTask(request, response, crawlerResult);
    }


    protected CrawlerResultDefine getCrawlerResultDefine(Class<? extends CrawlerResult> clazz) {
        return clazz.getAnnotation(CrawlerResultDefine.class);
    }

    protected Class<? extends CrawlerResult> getCrawlerResultClass(CrawlerHttpRequest request) {
        Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap =  context.getUrlCrawlerResultMap();
        if (urlCrawlerResultMap == null || urlCrawlerResultMap.size() == 0) {
            return null;
        }
        for(String url: urlCrawlerResultMap.keySet()) {
            Map<String,String> parameterMap = UrlUtils.urlMatch(url, request.getUrl());
            if (parameterMap != null) {
                request.setParameters(parameterMap);
                return urlCrawlerResultMap.get(url);
            }
        }
        String urlAlias = request.getUrlAlias();
        if (StrUtil.isNotBlank(urlAlias)) {
            return urlCrawlerResultMap.get(urlAlias);
        }
        return null;
    }

}
