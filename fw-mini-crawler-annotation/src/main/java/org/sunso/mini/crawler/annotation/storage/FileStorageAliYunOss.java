package org.sunso.mini.crawler.annotation.storage;


import cn.hutool.core.annotation.AliasFor;
import org.sunso.mini.crawler.storage.file.ALiYunOssCrawlerFileStorage;
import org.sunso.mini.crawler.storage.file.CrawlerFileStorage;

import java.lang.annotation.*;

/**
 * @author sunso520
 * @Title:FileStorageAliYunOss
 * @Description: 阿里云OSS文件存储注解
 * @Created on 2023/10/18 15:12
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FileStorage
@Inherited
public @interface FileStorageAliYunOss {

    /**
     * 存储文件名
     * @return
     */
    @AliasFor(annotation = FileStorage.class)
    String fileName() default "";

    /**
     * 文件扩展名
     * @return
     */
    String fileExtension() default "";

    /**
     * 文件对应的存储器
     * @return
     */
    @AliasFor(annotation = FileStorage.class, attribute = "fileStorage")
    Class<? extends CrawlerFileStorage> fileStorage() default ALiYunOssCrawlerFileStorage.class;

    /**
     * oss对应accessKeyId
     * @return
     */
    String accessKeyId() default "";

    /**
     * oss对应accessKeySecret
     * @return
     */
    String accessKeySecret() default "";

    /**
     * oss对应endpoint
     * @return
     */
    String endpoint() default "";

    /**
     * oss对应桶
     * @return
     */
    String bucket() default "";

    /**
     * oss对应的前缀key
     * @return
     */
    String preKey() default "";

    /**
     * oss存储后对应的显示域名
     * @return
     */
    String showDomain() default "";
}
