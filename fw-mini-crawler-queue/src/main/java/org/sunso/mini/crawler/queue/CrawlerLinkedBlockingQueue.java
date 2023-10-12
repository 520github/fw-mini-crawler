package org.sunso.mini.crawler.queue;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;

import java.util.concurrent.LinkedBlockingQueue;

public class CrawlerLinkedBlockingQueue implements CrawlerQueue {

    private LinkedBlockingQueue<CrawlerHttpRequest> queue;

    public CrawlerLinkedBlockingQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void offer(CrawlerHttpRequest request) {
        queue.add(request);
    }

    @Override
    public CrawlerHttpRequest poll() {
        return queue.poll();
    }
}
