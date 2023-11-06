package org.sunso.mini.crawler.spider;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.context.CrawlerContext;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sunso520
 * @Title:ThreadPoolLoopCrawlerSpider
 * @Description: <br>
 * @Created on 2023/11/6 11:28
 */
public class ThreadPoolLoopCrawlerSpider extends AbstractCrawlerSpider {

    private final long sleepTime = 10*1000;
    private AtomicInteger continuousSleep = new AtomicInteger();

    private ExecutorService executorService;

    public ThreadPoolLoopCrawlerSpider(CrawlerContext context) {
        super(context);
        //Executors.newFixedThreadPool()
        executorService = new ThreadPoolExecutor(1, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new SpiderThreadFactory());
    }

    @Override
    public void run() {
        while (true) {
            CrawlerHttpRequest request = getRequest();
            if (request == null) {
                setContinuousSleep();
                sleep();
                continue;
            }
            executorService.execute(() -> {
                doRequest(request);
            });
        }
    }

    private int setContinuousSleep() {
        if (continuousSleep.compareAndSet(Integer.MAX_VALUE, 0)) {
            System.out.println("超过了");
        }
        return continuousSleep.incrementAndGet();
    }

    private long getSleepTime() {
        long st = continuousSleep.get() * sleepTime;
        System.out.println("暂停一会:" + st);
        return st;
    }

    private void sleep() {
        try {
            Thread.sleep(getSleepTime());
        } catch (InterruptedException e) {
            System.out.println("");
        }
    }

    private CrawlerHttpRequest getRequest() {
        return getRequestFromCrawlerTask();
    }

}
