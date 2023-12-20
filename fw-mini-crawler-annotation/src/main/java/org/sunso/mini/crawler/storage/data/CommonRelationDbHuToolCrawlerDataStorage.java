package org.sunso.mini.crawler.storage.data;

import org.sunso.mini.crawler.annotation.db.CommonRelationDb;
import org.sunso.mini.crawler.common.db.DbDataInsertOrUpdate;
import org.sunso.mini.crawler.common.db.HuToolDb;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * @author sunso520
 * @Title:CommonRelationDbHuToolCrawlerDataStorage
 * @Description: 通用关系数据库的数据存储器 可以把爬虫最终爬取的数据存储到关系数据库中，如H2、Mysql、Pg等
 * @Created on 2023/10/19 10:07
 */
public class CommonRelationDbHuToolCrawlerDataStorage implements CrawlerDataStorage {

	@Override
	public boolean storage(CrawlerResult crawlerResult) {
		CommonRelationDb commonRelationDb = crawlerResult.getClass().getAnnotation(CommonRelationDb.class);
		DbDataInsertOrUpdate insert = getDbDataInsert(commonRelationDb);
		Long result;
		if (commonRelationDb.insertOrUpdate()) {
			result = Long.valueOf(HuToolDb.insertOrUpdateData(insert, crawlerResult));
		}
		else {
			result = HuToolDb.insertData(insert, crawlerResult);
		}
		if (result != null && result > 0) {
			return true;
		}
		return false;
	}

	private DbDataInsertOrUpdate getDbDataInsert(CommonRelationDb commonRelationDb) {
		DbDataInsertOrUpdate insert = new DbDataInsertOrUpdate();
		insert.setUrl(commonRelationDb.url());
		insert.setUser(commonRelationDb.user());
		insert.setPassword(commonRelationDb.password());
		insert.setTableName(commonRelationDb.tableName());
		insert.setFilterColumns(commonRelationDb.filterColumns());
		insert.setIgnoreNullValue(commonRelationDb.ignoreNullValue());
		insert.setToUnderlineCase(commonRelationDb.isToUnderlineCase());
		insert.setCheckExistColumns(commonRelationDb.checkExistColumns());
		return insert;
	}

}
