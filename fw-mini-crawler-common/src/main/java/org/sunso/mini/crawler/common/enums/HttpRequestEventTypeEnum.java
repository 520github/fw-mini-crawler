package org.sunso.mini.crawler.common.enums;

import lombok.Getter;

@Getter
public enum HttpRequestEventTypeEnum {
    click("click", "点击事件"),
    ;

    private String key;
    private String remark;

    HttpRequestEventTypeEnum(String key, String remark) {
        this.key = key;
        this.remark = remark;
    }
}
