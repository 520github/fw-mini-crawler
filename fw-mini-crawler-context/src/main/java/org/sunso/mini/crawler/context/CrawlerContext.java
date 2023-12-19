package org.sunso.mini.crawler.context;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.spider.CrawlerSpider;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;
import org.sunso.mini.crawler.parser.CrawlerParser;
import org.sunso.mini.crawler.task.CrawlerTask;

import java.util.List;
import java.util.Map;

/**
 * @author sunso520
 * @Title:CrawlerContext
 * @Description: 爬虫上下文
 *
 * @Created on 2023/10/12 10:13
 */
@Data
public class CrawlerContext {
    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 爬虫任务列表
     */
    private List<CrawlerHttpRequest> requestList;

    /**
     * 爬虫类型
     */
    private Class<? extends CrawlerSpider> spiderClassType;

    /**
     * 爬虫数量
     */
    private int spiderNum;
    /**
     * 爬虫任务处理器
     */
    private CrawlerTask task;
    /**
     * 爬虫下载器
     */
    private CrawlerDownloader downloader;
    /**
     * 爬虫解析器
     */
    private CrawlerParser parser;

    /**
     * 爬虫url与CrawlerResult类映射关系
     */
    private Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap;


    public CrawlerTask fetchTask() {
        if (task != null && StrUtil.isNotBlank(bizType)
                && !bizType.equals(task.getBizType())) {
            task.setBizType(bizType);
        }
        return task;
    }

}
