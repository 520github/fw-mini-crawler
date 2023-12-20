package org.sunso.mini.crawler.common.enums;

import lombok.Getter;

@Getter
public enum HttpRequestEventEndFlagEnum {

	hiddenElement("hiddenElement", "隐藏了元素"), disableElement("disableElement", "禁用了元素"),
	noSuchElement("noSuchElement", "找不到该元素"),;

	private String key;

	private String remark;

	HttpRequestEventEndFlagEnum(String key, String remark) {
		this.key = key;
		this.remark = remark;
	}

}
