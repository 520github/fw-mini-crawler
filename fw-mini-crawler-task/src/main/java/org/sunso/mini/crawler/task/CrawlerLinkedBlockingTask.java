package org.sunso.mini.crawler.task;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sunso520
 * @Title:CrawlerLinkedBlockingTask
 * @Description: <br>
 * @Created on 2023/11/1 17:14
 */
public class CrawlerLinkedBlockingTask extends AbstractCrawlerTask {
    private LinkedBlockingQueue<CrawlerHttpRequest> queue;

    public CrawlerLinkedBlockingTask() {
        queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void offerTask(CrawlerHttpRequest request) {
        queue.offer(request);
    }

    @Override
    public CrawlerHttpRequest pollTask() {
        return queue.poll();
    }

    @Override
    public void doneTask(CrawlerHttpRequest request, CrawlerHttpResponse response, CrawlerResult crawlerResult) {
        System.out.println(String.format("doneTask url[%s], responseStatus[%d]", request.getUrl(), response.status()));
    }
}
