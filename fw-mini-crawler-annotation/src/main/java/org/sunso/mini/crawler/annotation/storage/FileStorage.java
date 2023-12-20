package org.sunso.mini.crawler.annotation.storage;

import org.sunso.mini.crawler.storage.file.CrawlerFileStorage;
import org.sunso.mini.crawler.storage.file.NoneCrawlerFileStorage;

import java.lang.annotation.*;

/**
 * @author sunso520
 * @Title:FileStorage
 * @Description: 文件存储注解
 * @Created on 2023/10/18 15:12
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileStorage {

	/**
	 * 存储文件名
	 * @return
	 */
	String fileName() default "";

	/**
	 * 文件对应存储器
	 * @return
	 */
	Class<? extends CrawlerFileStorage> fileStorage() default NoneCrawlerFileStorage.class;

}
