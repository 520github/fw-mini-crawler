package org.sunso.mini.crawler.storage;

import lombok.Data;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

import java.lang.reflect.Field;

@Data
public class CrawlerFileStorageRequest {
    private Field field;
    CrawlerHttpResponse response;
}
