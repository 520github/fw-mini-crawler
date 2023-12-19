package org.sunso.mini.crawler.storage.file;

import org.sunso.mini.crawler.storage.CrawlerFileStorageRequest;

/**
 * @author sunso520
 * @Title:CrawlerFileStorage
 * @Description: 爬虫文件存储器
 * 针对CrawlerResult类中定义为需要文件存储的字段，进行文件存储
 * 可以存储到本地或存储到三方服务，如S3等
 * @Created on 2023/10/18 16:06
 */
public interface CrawlerFileStorage {

    /**
     * 针对CrawlerResult类中定义为需要文件存储的字段，进行文件存储
     * 可以存储到本地或存储到三方服务，如S3等
     *
     * @param request 某字段文件存储请求参数
     * @return 返回存储后的文件路径
     */
    String storage(CrawlerFileStorageRequest request);
}
