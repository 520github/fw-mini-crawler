package org.sunso.mini.crawler.annotation.result;

import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.downloader.EmptyCrawlerDownloader;
import org.sunso.mini.crawler.handler.CrawlerHandler;
import org.sunso.mini.crawler.storage.data.CrawlerDataStorage;
import org.sunso.mini.crawler.storage.data.EmptyCrawlerDataStorage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CrawlerResultDefine {
    Class<? extends CrawlerHandler>[] handlers();

    Class<? extends CrawlerDataStorage>[] dataStorages() default EmptyCrawlerDataStorage.class;

    Class<? extends CrawlerDownloader> downloader() default EmptyCrawlerDownloader.class;
}
