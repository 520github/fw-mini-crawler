package org.sunso.mini.crawler.storage.data;

import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:CrawlerDataStorage
 * @Description: 爬虫数据存储器
 * 把爬虫最终处理结果CrawlerResult类直接存储到数据库
 * 如H2、Mysql、Pg等
 * @Created on 2023/10/19 10:08
 */
public interface CrawlerDataStorage<B extends CrawlerResult> {

    /**
     * 把爬虫最终处理结果CrawlerResult类直接存储到数据库， 如H2、Mysql、Pg等
     *
     * @param crawlerResult  爬虫最终处理结果CrawlerResult类
     * @return 返回数据存储是否成功
     */
    boolean storage(B crawlerResult);
}
