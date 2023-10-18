package org.sunso.mini.crawler.annotation.storage;

import org.sunso.mini.crawler.storage.file.CrawlerFileStorage;
import org.sunso.mini.crawler.storage.file.NoneCrawlerFileStorage;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileStorage {

    String fileName() default "";

    Class<? extends CrawlerFileStorage> fileStorage() default NoneCrawlerFileStorage.class;


}
