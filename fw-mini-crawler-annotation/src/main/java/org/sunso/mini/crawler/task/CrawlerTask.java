package org.sunso.mini.crawler.task;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:CrawlerTask
 * @Description: 爬虫任务
 * 定义爬虫任务存储方式及执行方式
 * @Created on 2023/11/1 17:07
 */
public interface CrawlerTask {

    /**
     * 获取爬虫任务对于的业务类型
     * @return 业务类型
     */
    String getBizType();

    /**
     * 设置业务类型
     * @param bizType 业务类型
     * @return
     */
    CrawlerTask setBizType(String bizType);

    /**
     * 提交爬虫任务
     * @param request 爬虫任务HttpRequest
     */
    void offerTask(CrawlerHttpRequest request);

    /**
     * 消费爬虫任务
     * @return 爬虫任务HttpRequest
     */
    CrawlerHttpRequest pollTask();

    /**
     * 完成爬虫任务的处理
     * @param request 爬虫任务HttpRequest
     * @param response 爬虫任务HttpRequest对于的响应结果
     * @param crawlerResult  爬虫任务对应爬取的业务结果
     */
    void doneTask(CrawlerHttpRequest request, CrawlerHttpResponse response, CrawlerResult crawlerResult);
}
