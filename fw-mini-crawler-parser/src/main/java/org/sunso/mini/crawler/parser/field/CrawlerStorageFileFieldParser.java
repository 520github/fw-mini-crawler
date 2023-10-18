package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.annotation.storage.FileStorage;
import org.sunso.mini.crawler.annotation.storage.FileStorageAliYunOss;
import org.sunso.mini.crawler.storage.CrawlerFileStorageRequest;
import org.sunso.mini.crawler.storage.file.CrawlerFileStorage;
import org.sunso.mini.crawler.storage.file.CrawlerFileStorageFactory;

import java.lang.reflect.Field;

public class CrawlerStorageFileFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        return CrawlerFileStorageFactory.storage(getCrawlerFileStorageClass(request), getCrawlerFileStorageRequest(request));
    }

    private Class<? extends CrawlerFileStorage> getCrawlerFileStorageClass(CrawlerFieldParserRequest request) {
        Field field = request.getField();
        if (field.isAnnotationPresent(FileStorageAliYunOss.class)) {
            return field.getAnnotation(FileStorageAliYunOss.class).fileStorage();
        }
        else if (field.isAnnotationPresent(FileStorage.class)) {
            return field.getAnnotation(FileStorage.class).fileStorage();
        }
        return null;
    }

    private CrawlerFileStorageRequest getCrawlerFileStorageRequest(CrawlerFieldParserRequest request) {
        CrawlerFileStorageRequest instance = new CrawlerFileStorageRequest();
        instance.setField(request.getField());
        instance.setResponse(request.getResponse());
        return instance;
    }


}
