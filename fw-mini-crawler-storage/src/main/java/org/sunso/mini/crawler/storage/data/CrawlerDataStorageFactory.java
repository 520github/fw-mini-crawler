package org.sunso.mini.crawler.storage.data;

import lombok.SneakyThrows;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import java.util.ArrayList;
import java.util.List;

public class CrawlerDataStorageFactory {

	public static Class<? extends CrawlerDataStorage>[] getCrawlerDataStorages(CrawlerResult crawlerResult) {
		CrawlerResultDefine crawlerResultDefine = crawlerResult.getClass().getAnnotation(CrawlerResultDefine.class);
		if (crawlerResultDefine == null) {
			return null;
		}
		return crawlerResultDefine.dataStorages();
	}

	@SneakyThrows
	public static List<CrawlerDataStorage> getCrawlerDataStorageList(CrawlerResult crawlerResult) {
		List<CrawlerDataStorage> list = new ArrayList<>();
		Class<? extends CrawlerDataStorage>[] classes = getCrawlerDataStorages(crawlerResult);
		if (classes == null) {
			return list;
		}
		for (Class<? extends CrawlerDataStorage> clazz : classes) {
			list.add(getCrawlerFileStorage(clazz));
		}
		return list;
	}

	@SneakyThrows
	public static CrawlerDataStorage getCrawlerFileStorage(Class<? extends CrawlerDataStorage> clazz) {
		return clazz.newInstance();
	}

	public static boolean doDataStorage(Class<? extends CrawlerDataStorage> clazz, CrawlerResult crawlerResult) {
		return getCrawlerFileStorage(clazz).storage(crawlerResult);
	}

	public static void doDataStorage(CrawlerResult crawlerResult) {
		for (CrawlerDataStorage dataStorage : getCrawlerDataStorageList(crawlerResult)) {
			dataStorage.storage(crawlerResult);
		}
	}

}
