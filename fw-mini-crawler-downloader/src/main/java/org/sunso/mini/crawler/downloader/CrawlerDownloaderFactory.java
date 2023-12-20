package org.sunso.mini.crawler.downloader;

import lombok.SneakyThrows;

/**
 * @author sunso520
 * @Title:CrawlerDownloaderFactory
 * @Description: 爬虫下载器工厂类<br>
 * @Created on 2023/10/12 11:26
 */
public class CrawlerDownloaderFactory {

    @SneakyThrows
    public static CrawlerDownloader getCrawlerDownloader(Class<? extends CrawlerDownloader> clazz) {
        if (clazz == null) {
            return getDefaultCrawlerDownloader();
        }
        if (EmptyCrawlerDownloader.class.equals(clazz)) {
            return getDefaultCrawlerDownloader();
        }
        return clazz.newInstance();
    }

    public static CrawlerDownloader getDefaultCrawlerDownloader() {
        return new HutoolCrawlerDownloader();
    }

    public static CrawlerDownloader getSeleniumCrawlerDownloader() {
        return new SeleniumCrawlerDownloader();
    }
}
