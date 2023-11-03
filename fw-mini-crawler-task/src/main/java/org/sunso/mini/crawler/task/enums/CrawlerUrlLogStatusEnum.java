package org.sunso.mini.crawler.task.enums;

import lombok.Getter;

/**
 * @author sunso520
 * @Title:CrawlerUrlLogStatusEnum
 * @Description: <br>
 * @Created on 2023/11/2 10:37
 */
@Getter
public enum CrawlerUrlLogStatusEnum {

    init("init", "初始化"),
    doing("doing", "处理中"),
    success("success", "处理成功"),
    fail("fail", "处理失败"),
    exception("exception", "处理异常"),
    ;

    private String key;
    private String remark;

    CrawlerUrlLogStatusEnum(String key, String remark) {
        this.key = key;
        this.remark = remark;
    }
}
