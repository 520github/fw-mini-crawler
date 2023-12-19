package org.sunso.mini.crawler.checker;

import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:CrawlerChecker
 * @Description:
 * 爬虫校验器,对爬取的数据进行校验，以判断是否继续后面流程
 * @Created on 2023/11/14 15:29
 */
public interface CrawlerChecker<B extends CrawlerResult> {

    /**
     * 爬虫校验器,对爬虫最终处理结果CrawlerResult类进行校验，以判断是否继续后面流程
     *
     * @param crawlerResult  爬虫最终处理结果CrawlerResult类
     * @return 返回是否校验成功
     */
    boolean check(B crawlerResult);
}
