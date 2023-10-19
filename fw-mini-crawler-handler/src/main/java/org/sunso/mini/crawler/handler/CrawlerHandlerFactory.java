package org.sunso.mini.crawler.handler;

import lombok.SneakyThrows;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import java.util.ArrayList;
import java.util.List;

public class CrawlerHandlerFactory {

    public static Class<? extends CrawlerHandler>[] getCrawlerHandlers(CrawlerResult crawlerResult) {
        CrawlerResultDefine crawlerResultDefine = crawlerResult.getClass().getAnnotation(CrawlerResultDefine.class);
        if (crawlerResultDefine == null) {
            return null;
        }
        return crawlerResultDefine.handlers();
    }

    @SneakyThrows
    public static List<CrawlerHandler> getCrawlerHandlerList(CrawlerResult crawlerResult) {
        List<CrawlerHandler> list = new ArrayList<>();
        Class<? extends CrawlerHandler>[] classes = getCrawlerHandlers(crawlerResult);
        if (classes == null) {
            return list;
        }
        for(Class<? extends CrawlerHandler> clazz: classes) {
            list.add(clazz.newInstance());
        }
        return list;
    }

    public static void doCrawlerHandler(CrawlerResult crawlerResult) {
        for(CrawlerHandler handler: getCrawlerHandlerList(crawlerResult)) {
            handler.handle(crawlerResult);
        }
    }

}
