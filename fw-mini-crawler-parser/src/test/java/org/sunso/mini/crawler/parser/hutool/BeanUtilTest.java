package org.sunso.mini.crawler.parser.hutool;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import org.junit.Test;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.parser.BaseTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtilTest extends BaseTest {

	@Test
	public void map2Bean() {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("name", "黎明");
		dataMap.put("age", 22);
		dataMap.put("cityList", Arrays.asList("bj", "sh"));
		DefaultCrawlerResult child = new DefaultCrawlerResult();
		child.setName("child");
		child.setAge(1);
		dataMap.put("child", child);
		DefaultCrawlerResult result = BeanUtil.mapToBean(dataMap, DefaultCrawlerResult.class, true);
		print(result);
	}

	@Data
	class DefaultCrawlerResult implements CrawlerResult {

		private String name;

		private int age;

		private List<String> cityList;

		private DefaultCrawlerResult child;

	}

}
