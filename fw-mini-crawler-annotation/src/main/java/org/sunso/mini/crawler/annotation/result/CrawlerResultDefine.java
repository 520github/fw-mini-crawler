package org.sunso.mini.crawler.annotation.result;

import org.sunso.mini.crawler.annotation.html.HtmlRepairTypeEnum;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.downloader.EmptyCrawlerDownloader;
import org.sunso.mini.crawler.handler.CrawlerHandler;
import org.sunso.mini.crawler.parser.CrawlerParser;
import org.sunso.mini.crawler.parser.EmptyCrawlerParser;
import org.sunso.mini.crawler.storage.data.CrawlerDataStorage;
import org.sunso.mini.crawler.storage.data.EmptyCrawlerDataStorage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:CrawlerResultDefine
 * @Description: 针对CrawlerResult类自定义相关操作
 *
 * 包含：
 * <ul>
 *     <li>定义爬虫处理器</li>
 *     <li>定义爬虫下载器</li>
 *     <li>定义爬虫数据存储器</li>
 *     <li>定义爬虫解析器</li>
 *     <li>定义修补方式</li>
 * </ul>
 * @Created on 2023/10/12 10:27
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CrawlerResultDefine {
    /**
     * 指定爬虫最终结果的处理器，可以指定多个
     *
     * @return
     */
    Class<? extends CrawlerHandler>[] handlers();

    /**
     * 指定爬虫最终结果的存储器
     * @return
     */
    Class<? extends CrawlerDataStorage>[] dataStorages() default EmptyCrawlerDataStorage.class;

    /**
     * 指定该爬虫任务的下载器
     * @return
     */
    Class<? extends CrawlerDownloader> downloader() default EmptyCrawlerDownloader.class;

    /**
     * 指定该爬虫任务的解析器
     * @return
     */
    Class<? extends CrawlerParser> parser() default EmptyCrawlerParser.class;

    /**
     * 针对一些缺失标签的，指定修补类型，自动修补缺失的标签
     * @return
     */
    HtmlRepairTypeEnum repairType() default HtmlRepairTypeEnum.empty;
}
