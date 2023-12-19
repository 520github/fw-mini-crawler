package org.sunso.mini.crawler.enginer;

import org.sunso.mini.crawler.context.CrawlerContext;

/**
 * @author sunso520
 * @Title:CrawlerEnginer
 * @Description:爬虫引擎定义接口
 * 不同业务需要场景，需要不同的爬虫引擎去处理
 * @Created on 2023/10/12 11:09
 */
public interface CrawlerEnginer {

    /**
     * 设置爬虫上下文
     * @param context 爬虫上下文
     */
    void setCrawlerContext(CrawlerContext context);

    /**
     * 获取爬虫上下文
     * @return 爬虫上下文
     */
    CrawlerContext getCrawlerContext();


    /**
     * 启动爬虫
     * @return
     */
    CrawlerEnginer startCrawler();
}
