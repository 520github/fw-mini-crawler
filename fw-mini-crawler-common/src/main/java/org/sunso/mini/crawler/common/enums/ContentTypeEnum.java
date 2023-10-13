package org.sunso.mini.crawler.common.enums;

import lombok.Getter;

@Getter
public enum ContentTypeEnum {
    applicationJson("application/json", ""),
    applicationXWwwForm("application/x-www-form-urlencoded", "")
    ;

    private String key;
    private String remark;

    ContentTypeEnum(String key, String remark) {
        this.key = key;
        this.remark = remark;
    }
}
