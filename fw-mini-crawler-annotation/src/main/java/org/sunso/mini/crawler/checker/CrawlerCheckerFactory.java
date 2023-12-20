package org.sunso.mini.crawler.checker;

import lombok.SneakyThrows;
import org.sunso.mini.crawler.storage.file.CrawlerFileStorage;

/**
 * @author sunso520
 * @Title:CrawlerCheckerFactory
 * @Description: 爬虫校验器工厂类<br>
 * @Created on 2023/11/14 15:33
 */
public class CrawlerCheckerFactory {
    @SneakyThrows
    public static CrawlerChecker getCrawlerChecker(Class<? extends CrawlerChecker> clazz) {
        return clazz.newInstance();
    }
}
