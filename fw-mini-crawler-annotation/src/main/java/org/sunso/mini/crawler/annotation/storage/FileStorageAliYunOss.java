package org.sunso.mini.crawler.annotation.storage;


import cn.hutool.core.annotation.AliasFor;
import org.sunso.mini.crawler.storage.file.ALiYunOssCrawlerFileStorage;
import org.sunso.mini.crawler.storage.file.CrawlerFileStorage;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FileStorage
@Inherited
public @interface FileStorageAliYunOss {

    @AliasFor(annotation = FileStorage.class)
    String fileName() default "";

    String fileExtension() default "";

    @AliasFor(annotation = FileStorage.class, attribute = "fileStorage")
    Class<? extends CrawlerFileStorage> fileStorage() default ALiYunOssCrawlerFileStorage.class;

    String accessKeyId() default "";

    String accessKeySecret() default "";

    String endpoint() default "";

    String bucket() default "";

    String preKey() default "";

    String showDomain() default "";
}
