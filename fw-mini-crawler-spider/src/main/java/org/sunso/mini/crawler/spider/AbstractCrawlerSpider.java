package org.sunso.mini.crawler.spider;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
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

/**
 * @author sunso520
 * @Title:AbstractCrawlerSpider
 * @Description: 爬虫执行器的抽象处理类
 * @Created on 2023/10/12 11:05
 */
@Slf4j
public abstract class AbstractCrawlerSpider implements CrawlerSpider {
    protected CrawlerContext context;

    protected  AbstractCrawlerSpider(CrawlerContext context) {
        this.context = context;
    }

    /**
     * 获取爬虫下载器
     *
     * @param clazz 爬虫任务对应的最终处理结果类
     * @return 返回爬虫下载器
     */
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

    /**
     * 获取爬虫分析器
     *
     * @param clazz 爬虫任务对应的最终处理结果类
     * @return 返回爬虫分析器
     */
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

    /**
     * 获取CrawlerHttpRequest
     *
     * @return 返回CrawlerHttpRequest
     */
    protected CrawlerHttpRequest getRequestFromCrawlerTask() {
        return context.getTask().pollTask();
    }

    /**
     * 获取爬虫任务
     *
     * @return 返回CrawlerTask
     */
    protected CrawlerTask getCrawlerTask() {
        return context.fetchTask();
    }

    /**
     * 处理CrawlerHttpRequest
     */
    protected void doRequest() {
        doRequest(getRequestFromCrawlerTask());
    }

    /**
     * 处理CrawlerHttpRequest
     *
     * @param request CrawlerHttpRequest
     */
    protected void doRequest(CrawlerHttpRequest request) {
        if (request == null) {
            log.info("本次处理的CrawlerHttpRequest为空，退出当前处理逻辑");
            return ;
        }
        Class<? extends CrawlerResult> clazz = getCrawlerResultClass(request);
        if (clazz == null) {
            return ;
        }
        // 下载url对应结果
        CrawlerHttpResponse response = getDownloader(clazz).download(request);
        // 根据下载结果，对CrawlerResult定义的字段进行解析，并设置对应值
        CrawlerResult crawlerResult = getCrawlerParser(clazz).parse(clazz, request, response);
        // 完成本次的爬虫任务
        getCrawlerTask().doneTask(request, response, crawlerResult);
    }

    protected CrawlerResultDefine getCrawlerResultDefine(Class<? extends CrawlerResult> clazz) {
        return clazz.getAnnotation(CrawlerResultDefine.class);
    }

    /**
     * 获取CrawlerHttpRequest对应的CrawlerResult类
     *
     * @param request CrawlerHttpRequest
     * @return 返回CrawlerResult类
     */
    protected Class<? extends CrawlerResult> getCrawlerResultClass(CrawlerHttpRequest request) {
        Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap =  context.getUrlCrawlerResultMap();
        if (urlCrawlerResultMap == null || urlCrawlerResultMap.size() == 0) {
            log.info("CrawlerHttpRequest[{}] no corresponding class[CrawlerResult] found, it must be set.", request);
            return null;
        }
        // 根据url获取CrawlerResult类
        for(String url: urlCrawlerResultMap.keySet()) {
            Map<String,String> parameterMap = UrlUtils.urlMatch(url, request.getUrl());
            if (parameterMap != null) {
                request.setParameters(parameterMap);
                return urlCrawlerResultMap.get(url);
            }
        }
        // 根据url别名获取CrawlerResult类
        String urlAlias = request.getUrlAlias();
        if (StrUtil.isNotBlank(urlAlias)) {
            return urlCrawlerResultMap.get(urlAlias);
        }

        log.info("CrawlerHttpRequest[{}] no corresponding class[CrawlerResult] found, it must be set.", request);

        return null;
    }

}
