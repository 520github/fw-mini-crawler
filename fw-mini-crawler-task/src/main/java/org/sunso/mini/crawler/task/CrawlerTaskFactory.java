package org.sunso.mini.crawler.task;

/**
 * @author sunso520
 * @Title:CrawlerTaskFactory
 * @Description: <br>
 * @Created on 2023/11/2 10:58
 */
public class CrawlerTaskFactory {

    public static CrawlerTask getDefaultCrawlerTask() {
        return getDefaultCrawlerTask(null);
    }

    public static CrawlerTask getDefaultCrawlerTask(String bizKey) {
        return new CrawlerLinkedBlockingTask().setBizType(bizKey);
    }
}
