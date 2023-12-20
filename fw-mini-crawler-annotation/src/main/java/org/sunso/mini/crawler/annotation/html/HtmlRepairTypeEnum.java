package org.sunso.mini.crawler.annotation.html;

import lombok.Getter;

/**
 * @author sunso520
 * @Title:HtmlRepairTypeEnum
 * @Description: <br>
 * @Created on 2023/11/21 14:15
 */
@Getter
public enum HtmlRepairTypeEnum {

	empty("empty", "默认值"), none("none", "不需要做任何修补处理"), tableTag("tableTag", "需要补充table标签"),;

	private String type;

	private String remark;

	HtmlRepairTypeEnum(String type, String remark) {
		this.type = type;
		this.remark = remark;
	}

}
