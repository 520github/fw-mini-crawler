package org.sunso.mini.crawler.parser.field;

import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.annotation.storage.FileStorage;
import org.sunso.mini.crawler.annotation.storage.FileStorageAliYunOss;
import org.sunso.mini.crawler.storage.CrawlerFileStorageRequest;
import org.sunso.mini.crawler.storage.file.CrawlerFileStorage;
import org.sunso.mini.crawler.storage.file.CrawlerFileStorageFactory;

import java.lang.reflect.Field;

/**
 * @author sunso520
 * @Title:CrawlerStorageFileFieldParser
 * @Description: 字段为文件存储类型的解析处理<br>
 * @Created on 2023/10/18 17:03
 */
@Slf4j
public class CrawlerStorageFileFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        return CrawlerFileStorageFactory.storage(getCrawlerFileStorageClass(request), getCrawlerFileStorageRequest(request));
    }

    /**
     * 获取文件存储类型
     *
     * @param request 字段解析请求参数
     * @return
     */
    private Class<? extends CrawlerFileStorage> getCrawlerFileStorageClass(CrawlerFieldParserRequest request) {
        Field field = request.getField();
        if (field.isAnnotationPresent(FileStorageAliYunOss.class)) {
            return field.getAnnotation(FileStorageAliYunOss.class).fileStorage();
        }
        else if (field.isAnnotationPresent(FileStorage.class)) {
            return field.getAnnotation(FileStorage.class).fileStorage();
        }
        log.warn("CrawlerStorageFileFieldParser not found any CrawlerFileStorage with field[{}]", field.getName());
        return null;
    }

    /**
     * 获取文件存储类型请求参数
     *
     * @param request 字段解析请求参数
     * @return
     */
    private CrawlerFileStorageRequest getCrawlerFileStorageRequest(CrawlerFieldParserRequest request) {
        CrawlerFileStorageRequest instance = new CrawlerFileStorageRequest();
        instance.setField(request.getField());
        instance.setResponse(request.getResponse());
        return instance;
    }

}
