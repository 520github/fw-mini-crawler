package org.sunso.mini.crawler.spider;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sunso520
 * @Title:SpiderThreadFactory
 * @Description: 爬虫线程工厂类<br>
 * @Created on 2023/11/6 17:17
 */
public class SpiderThreadFactory implements ThreadFactory {

	/**
	 * 线程池数
	 */
	private static final AtomicInteger poolNumber = new AtomicInteger(1);

	/**
	 * 线程数
	 */
	private final AtomicInteger threadNumber = new AtomicInteger(1);

	/**
	 * 线程名前缀
	 */
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
