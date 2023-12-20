package org.sunso.mini.crawler.handler;

import lombok.SneakyThrows;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunso520
 * @Title:CrawlerHandlerFactory
 * @Description: 爬虫结果处理器工厂类<br>
 * @Created on 2023/10/16 11:26
 */
public class CrawlerHandlerFactory {

	/**
	 * 获取爬虫结果处理器类数组
	 * @param crawlerResult 爬虫结果对象
	 * @return
	 */
	public static Class<? extends CrawlerHandler>[] getCrawlerHandlers(CrawlerResult crawlerResult) {
		CrawlerResultDefine crawlerResultDefine = crawlerResult.getClass().getAnnotation(CrawlerResultDefine.class);
		if (crawlerResultDefine == null) {
			return null;
		}
		return crawlerResultDefine.handlers();
	}

	/**
	 * 获取爬虫结果处理器列表
	 * @param crawlerResult 爬虫结果对象
	 * @return
	 */
	@SneakyThrows
	public static List<CrawlerHandler> getCrawlerHandlerList(CrawlerResult crawlerResult) {
		List<CrawlerHandler> list = new ArrayList<>();
		Class<? extends CrawlerHandler>[] classes = getCrawlerHandlers(crawlerResult);
		if (classes == null) {
			return list;
		}
		for (Class<? extends CrawlerHandler> clazz : classes) {
			list.add(clazz.newInstance());
		}
		return list;
	}

	/**
	 * 执行所有爬虫处理器操作
	 * @param crawlerResult 爬虫结果对象
	 */
	public static void doCrawlerHandler(CrawlerResult crawlerResult) {
		for (CrawlerHandler handler : getCrawlerHandlerList(crawlerResult)) {
			handler.handle(crawlerResult);
		}
	}

}
