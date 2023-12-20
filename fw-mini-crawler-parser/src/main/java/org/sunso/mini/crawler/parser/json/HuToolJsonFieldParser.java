package org.sunso.mini.crawler.parser.json;

import cn.hutool.json.*;
import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.utils.JsonUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunso520
 * @Title:HuToolJsonFieldParser
 * @Description: 用于解析json的HuToolJson解析器
 * @Created on 2023/10/12 11:18
 */
@Slf4j
public class HuToolJsonFieldParser extends AbstractJsonFieldParser {

	JSON jsonObject = null;

	public HuToolJsonFieldParser(String jsonContent) {
		super(jsonContent);
		initJson();
	}

	@Override
	public Object selectorObject(Field field) {
		String jsonPath = getJsonPath(field);
		/**
		 * 简单单一的jsonPath情况 如：data.li[0].list[1].type.id[6]
		 */
		if (!jsonPath.contains(",")) {
			Object result = jsonObject.getByPath(jsonPath);
			if (result == null) {
				return null;
			}
			if (result instanceof JSONNull) {
				return null;
			}
			return result;
		}
		/**
		 * 非单一的jsonPath情况
		 *
		 * 如：data.li[0].list[1,2,3].type.id[6] 需要被解析成： data.li[0].list[1].type.id[6]
		 * data.li[0].list[2].type.id[6] data.li[0].list[3].type.id[6]]
		 */
		List<String> jsonPathList = getMultiJsonPath(jsonPath);
		List<Object> list = null;
		JSONArray jsonArray = null;
		for (String path : jsonPathList) {
			Object data = jsonObject.getByPath(path);
			if (data instanceof JSONArray || data instanceof JSONObject) {
				if (jsonArray == null) {
					jsonArray = new JSONArray();
				}
				jsonArray.put(data);
			}
			else {
				if (list == null) {
					list = new ArrayList<>();
				}
				list.add(data);
			}
		}
		return list != null ? list : jsonArray;
	}

	private String getJsonPath(Field field) {
		JsonPath jsonPath = field.getAnnotation(JsonPath.class);
		return jsonPath.value();
	}

	private List<String> getMultiJsonPath(String jsonPath) {
		return JsonUtils.getMultiJsonPath(jsonPath);
	}

	/**
	 * json字符串初始化成json对象
	 */
	private void initJson() {
		String jsonContent = getJsonContent();
		if (jsonContent.startsWith("{")) {
			jsonObject = JSONUtil.parseObj(jsonContent);
		}
		else if (jsonContent.startsWith("[")) {
			jsonObject = JSONUtil.parseArray(jsonContent);
		}
		else {
			log.error("HuToolJsonFieldParser found unknown json format data[{}]", jsonContent);
		}
	}

}
