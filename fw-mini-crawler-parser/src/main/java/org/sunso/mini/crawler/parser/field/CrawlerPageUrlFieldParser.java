package org.sunso.mini.crawler.parser.field;

import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.annotation.html.HtmlPageUrl;
import org.sunso.mini.crawler.common.utils.UrlUtils;
import org.sunso.mini.crawler.parser.dto.HtmlAjaxDTO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunso520
 * @Title:CrawlerPageUrlFieldParser
 * @Description: 字段为接口访问的分页数据的解析处理情况<br>
 * @Created on 2023/10/27 14:51
 */
@Slf4j
public class CrawlerPageUrlFieldParser extends AbstractCrawlerFieldParser {

	@Override
	public Object parseField(CrawlerFieldParserRequest request) {
		List<Object> dataList = new ArrayList<>();
		Field field = request.getField();
		HtmlPageUrl htmlPageUrl = getHtmlPageUrl(field);
		int currentPage = htmlPageUrl.startPageNo();
		while (currentPage <= htmlPageUrl.endPageNo()) {
			List<Object> pageList = getPageDataList(htmlPageUrl, currentPage, request);
			currentPage++;
			if (pageList == null || pageList.isEmpty()) {
				break;
			}
			dataList.addAll(pageList);
		}
		return dataList;
	}

	/**
	 * 获取指定页的分页数据
	 * @param htmlPageUrl HtmlPageUrl注解
	 * @param currentPage 指定页
	 * @param request 字段解析请求参数
	 * @return
	 */
	private List<Object> getPageDataList(HtmlPageUrl htmlPageUrl, int currentPage, CrawlerFieldParserRequest request) {
		Field field = request.getField();
		HtmlAjaxDTO ajaxDTO = getHtmlAjaxDTO(request.getField());
		String url = UrlUtils.getNextPageUrl(ajaxDTO.getUrl(), htmlPageUrl.pageNoKey(), currentPage);
		ajaxDTO.setUrl(url);
		// 设置HtmlAjax请求参数
		request.setHtmlAjaxDTO(ajaxDTO);
		CrawlerFieldParser parser = getCrawlerFieldParser(field);
		// 未发现可用的字段解析器
		if (parser == null) {
			return null;
		}
		// 结果为空
		Object result = parser.parseField(request);
		if (result == null) {
			return null;
		}
		// 返回结果类型不是list
		if (result instanceof List) {
			return (List<Object>) result;
		}
		log.warn("CrawlerPageUrlFieldParser getPageDataList result[{}] is not list with field[{}]",
				result.getClass().getName(), field.getName());
		return null;
	}

	/**
	 * 获取爬虫字段解析器
	 * @param field 字段
	 * @return
	 */
	private CrawlerFieldParser getCrawlerFieldParser(Field field) {
		if (field.isAnnotationPresent(HtmlAjax.class)) {
			return new CrawlerAjaxFieldParser();
		}
		log.warn("CrawlerPageUrlFieldParser not found any CrawlerFieldParser with field[{}]", field.getName());
		return null;
	}

	/**
	 * 把HtmlAjax注解转化成HtmlAjaxDTO对象
	 * @param field 字段
	 * @return 返回HtmlAjaxDTO对象
	 */
	private HtmlAjaxDTO getHtmlAjaxDTO(Field field) {
		return HtmlAjaxDTO.newInstance(field.getAnnotation(HtmlAjax.class));
	}

	/**
	 * 获取HtmlPageUrl注解
	 * @param field 字段
	 * @return
	 */
	private HtmlPageUrl getHtmlPageUrl(Field field) {
		return field.getAnnotation(HtmlPageUrl.class);
	}

}
