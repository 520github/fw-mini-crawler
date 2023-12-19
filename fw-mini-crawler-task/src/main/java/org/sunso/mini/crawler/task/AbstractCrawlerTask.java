package org.sunso.mini.crawler.task;

import cn.hutool.core.util.StrUtil;

/**
 * @author sunso520
 * @Title:AbstractCrawlerTask
 * @Description: 爬虫任务抽象类<br>
 * @Created on 2023/11/2 10:56
 */
public abstract class AbstractCrawlerTask implements CrawlerTask {

    /**
     * 任务对应业务类型
     */
    protected String bizType = "default";

    public String getBizType() {
        return bizType;
    }

    public CrawlerTask setBizType(String bizType) {
        if (StrUtil.isNotBlank(bizType)) {
            this.bizType = bizType;
        }
        return this;
    }
}
