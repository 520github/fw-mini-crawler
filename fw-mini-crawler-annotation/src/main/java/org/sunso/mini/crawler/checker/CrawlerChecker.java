package org.sunso.mini.crawler.checker;

import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:CrawlerChecker
 * @Description: <br>
 * @Created on 2023/11/14 15:29
 */
public interface CrawlerChecker<B extends CrawlerResult> {
    boolean check(B crawlerResult);
}
