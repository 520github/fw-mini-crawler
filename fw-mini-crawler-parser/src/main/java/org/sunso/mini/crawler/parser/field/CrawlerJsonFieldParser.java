package org.sunso.mini.crawler.parser.field;

import cn.hutool.json.JSONArray;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.parser.json.HuToolJsonFieldParser;
import org.sunso.mini.crawler.parser.json.JsonFieldParser;
import org.sunso.mini.crawler.parser.json.JsonFieldParserFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunso520
 * @Title: CrawlerJsonFieldParser
 * @Description: 字段为json类型的解析处理器<br>
 * @Created on 2023/10/13 14:08
 */
public class CrawlerJsonFieldParser extends AbstractCrawlerFieldParser {

	@Override
	public Object parseField(CrawlerFieldParserRequest request) {
		return parseJsonField(request);
	}

	private Object parseJsonField(CrawlerFieldParserRequest request) {
		Field field = request.getField();
		// 字段为list列表
		if (isList(field)) {
			return handleList(request);
		}
		// 字段为数组
		else if (isArray(field)) {
			return handleArray(request);
		}
		// 字段类型实现了CrawlerResult接口
		else if (isCrawlerResult(field)) {
			return handleCrawlerResult(request);
		}
		JsonFieldParser jsonFieldParser = getJsonFieldParser(request);
		return jsonFieldParser.selectorObject(field);
	}

	/**
	 * 解析处理字段为CrawlerResult的情况
	 * @param request
	 * @return
	 */
	private CrawlerResult handleCrawlerResult(CrawlerFieldParserRequest request) {
		Field field = request.getField();
		JsonFieldParser jsonFieldParser = getJsonFieldParser(request);
		Object result = jsonFieldParser.selectorObject(field);
		return request.getCrawlerParser().parse((Class<? extends CrawlerResult>) field.getType(), request.getRequest(),
				CrawlerHttpResponse.create(result.toString()));
	}

	/**
	 * 解析处理字段为列表类型的情况
	 * @param request 字段解析请求参数
	 * @return
	 */
	private List<Object> handleList(CrawlerFieldParserRequest request) {
		return handleList(getGenericClass(request.getField()), request);
	}

	/**
	 * 解析处理字段为数组类型的情况
	 * @param request 字段解析请求参数
	 * @return
	 */
	private Object[] handleArray(CrawlerFieldParserRequest request) {
		Class genericClass = request.getField().getType().getComponentType();
		return handleList(genericClass, request).toArray();
	}

	/**
	 * 解析处理字段为列表类型的情况
	 * @param genericClass 字段对应类型
	 * @param request 字段解析请求参数
	 * @return
	 */
	private List<Object> handleList(Class genericClass, CrawlerFieldParserRequest request) {
		Field field = request.getField();
		JsonFieldParser jsonFieldParser = getJsonFieldParser(request);
		// 字段类型非CrawlerResult的情况
		if (!isCrawlerResult(genericClass)) {
			return checkFilter(field, (List) jsonFieldParser.selectorObject(field));
		}

		// 字段类型是CrawlerResult的情况
		JSONArray jsonArray = (JSONArray) jsonFieldParser.selectorObject(field);
		List<Object> resultList = new ArrayList<>();
		for (Object json : jsonArray.toArray()) {
			CrawlerResult crawlerResult = request.getCrawlerParser().parse(genericClass, request.getRequest(),
					CrawlerHttpResponse.create(json.toString()));
			if (checkFilter(field, crawlerResult)) {
				resultList.add(crawlerResult);
			}
		}
		return resultList;
	}

	private JsonFieldParser getJsonFieldParser(CrawlerFieldParserRequest request) {
		return getJsonFieldParser(request.fetchResponseBody(), request);
	}

	private JsonFieldParser getJsonFieldParser(String jsonData, CrawlerFieldParserRequest request) {
		return JsonFieldParserFactory.getJsonFieldParser(jsonData, HuToolJsonFieldParser.class);
	}

}
