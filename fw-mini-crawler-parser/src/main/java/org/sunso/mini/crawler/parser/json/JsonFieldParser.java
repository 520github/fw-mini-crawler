package org.sunso.mini.crawler.parser.json;

import java.lang.reflect.Field;

/**
 * @author sunso520
 * @Title:JsonFieldParser
 * @Description: json解析器接口
 * @Created on 2023/10/12 11:16
 */
public interface JsonFieldParser {

	/**
	 * 根据字段定义，从json数据中获取对应的数据
	 * @param field 字段
	 * @return
	 */
	Object selectorObject(Field field);

}
