package org.sunso.mini.crawler.storage.file;

import lombok.SneakyThrows;
import org.sunso.mini.crawler.storage.CrawlerFileStorageRequest;

public class CrawlerFileStorageFactory {
    @SneakyThrows
    public static CrawlerFileStorage getCrawlerFileStorage(Class<? extends CrawlerFileStorage> clazz) {
        return clazz.newInstance();
    }

    public static String storage(Class<? extends CrawlerFileStorage> clazz, CrawlerFileStorageRequest request) {
        return getCrawlerFileStorage(clazz).storage(request);
    }
}
