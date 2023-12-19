package org.sunso.mini.crawler.spider;

import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.context.CrawlerContext;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sunso520
 * @Title:ThreadPoolLoopCrawlerSpider
 * @Description: 线程池循环处理爬虫执行器<br>
 * @Created on 2023/11/6 11:28
 */
@Slf4j
public class ThreadPoolLoopCrawlerSpider extends AbstractCrawlerSpider {

    /**
     * 没有可执行任务时，休眠时间
     */
    private final long SLEEP_TIME = 10*1000;

    /**
     * 允许最大的休眠次数
     */
    private final int MAX_SLEEP_NUM = Integer.MAX_VALUE;

    /**
     * 连续休眠的次数
     */
    private AtomicInteger continuousSleep = new AtomicInteger();

    /**
     * 线程执行器
     */
    private ExecutorService executorService;

    public ThreadPoolLoopCrawlerSpider(CrawlerContext context) {
        super(context);
        executorService = new ThreadPoolExecutor(5, 10, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new SpiderThreadFactory());
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
        if (continuousSleep.compareAndSet(MAX_SLEEP_NUM, 0)) {
            log.info("执行器[ThreadPoolLoopCrawlerSpider]休眠次数已超过最大值[{}], 重新重置为0", MAX_SLEEP_NUM);
        }
        return continuousSleep.incrementAndGet();
    }

    private long getSleepTime() {
        long st = continuousSleep.get() * SLEEP_TIME;
        return st;
    }

    private void sleep() {
        try {
            long sleetTime = getSleepTime();
            log.debug("执行器[ThreadPoolLoopCrawlerSpider]准备休眠[{}]毫秒", sleetTime);
            Thread.sleep(sleetTime);
        } catch (InterruptedException e) {
            log.error("sleep exception", e);
        }
    }

    private CrawlerHttpRequest getRequest() {
        return getRequestFromCrawlerTask();
    }
}
