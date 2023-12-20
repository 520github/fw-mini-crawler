package org.sunso.mini.crawler.downloader.enums;

import lombok.Getter;

@Getter
public enum DownloaderExtendKeyEnum {

	browserType("browserType", "浏览器类型"),;

	private String key;

	private String remark;

	DownloaderExtendKeyEnum(String key, String remark) {
		this.key = key;
		this.remark = remark;
	}

}
