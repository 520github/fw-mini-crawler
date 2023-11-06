package org.sunso.mini.crawler.spider;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sunso520
 * @Title:SpiderThreadFactory
 * @Description: <br>
 * @Created on 2023/11/6 17:17
 */
public class SpiderThreadFactory implements ThreadFactory  {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public SpiderThreadFactory() {
        namePrefix = "crawler-pool-" + poolNumber.getAndIncrement() + "-spider-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, namePrefix + threadNumber.getAndIncrement());
        return thread;
    }
}
