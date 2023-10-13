package org.sunso.mini.crawler.downloader;

import lombok.SneakyThrows;

public class CrawlerDownloaderFactory {

    @SneakyThrows
    public static CrawlerDownloader getCrawlerDownloader(Class<? extends CrawlerDownloader> clazz) {
        if (clazz == null) {
            return new HutoolCrawlerDownloader();
        }
        return clazz.newInstance();
    }
}
