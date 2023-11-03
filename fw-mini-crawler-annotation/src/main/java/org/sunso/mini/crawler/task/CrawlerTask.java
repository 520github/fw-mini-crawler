package org.sunso.mini.crawler.task;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:CrawlerTask
 * @Description: <br>
 * @Created on 2023/11/1 17:07
 */
public interface CrawlerTask {

    String getBizType();

    CrawlerTask setBizType(String bizType);

    void offerTask(CrawlerHttpRequest request);

    CrawlerHttpRequest pollTask();

    void doneTask(CrawlerHttpRequest request, CrawlerHttpResponse response, CrawlerResult crawlerResult);
}
