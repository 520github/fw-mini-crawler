package org.sunso.mini.crawler.storage.data;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import lombok.SneakyThrows;
import org.sunso.mini.crawler.annotation.db.CommonRelationDb;
import org.sunso.mini.crawler.common.db.DbDataInsert;
import org.sunso.mini.crawler.common.db.HuToolDb;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CommonRelationDbHuToolCrawlerDataStorage implements CrawlerDataStorage {
    @Override
    public boolean storage(CrawlerResult crawlerResult) {
        CommonRelationDb commonRelationDb = crawlerResult.getClass().getAnnotation(CommonRelationDb.class);
        DbDataInsert insert = getDbDataInsert(commonRelationDb);
        Long id = HuToolDb.insertData(insert, crawlerResult);
        if (id != null && id >0) {
            return true;
        }
        return false;
    }

//    private boolean checkExistData(CommonRelationDb commonRelationDb,  Db db, Entity entity) {
//        String[] columns = commonRelationDb.checkExistColumns();
//        if (columns == null || columns.length < 1) {
//            return false;
//        }
//        try {
//            List<Entity> list = db.find(Arrays.asList(columns), entity);
//            if (list!= null && list.size() > 0) {
//                return true;
//            }
//        } catch (SQLException e) {
//            return false;
//        }
//        return false;
//    }
//
//    private Entity getEntity(CommonRelationDb commonRelationDb, CrawlerResult crawlerResult) {
//        Entity entity = Entity.create(commonRelationDb.tableName()).parseBean(crawlerResult, commonRelationDb.isToUnderlineCase(), commonRelationDb.ignoreNullValue());
//        String filterColumns[] = commonRelationDb.filterColumns();
//        if (filterColumns != null && filterColumns.length > 0) {
//            return entity.filter(filterColumns);
//        }
//        return entity;
//    }
//
//    @SneakyThrows
//    private boolean insertData(Db db, Entity entity) {
//        db.insertForGeneratedKey(entity);
//        return true;
//    }
//
//    private DataSource getDataSource(CommonRelationDb commonRelationDb)  {
//        String url = commonRelationDb.url();
//        String user = commonRelationDb.user();
//        if (StrUtil.isBlank(url) || StrUtil.isBlank(user)) {
//            return null;
//        }
//        return new SimpleDataSource(url, user, commonRelationDb.password());
//    }
//
//    private Db getDb(CommonRelationDb commonRelationDb) {
//        DataSource dataSource = getDataSource(commonRelationDb);
//        if (dataSource == null) {
//            return Db.use();
//        }
//        return Db.use(dataSource);
//    }

    private DbDataInsert getDbDataInsert(CommonRelationDb commonRelationDb) {
        DbDataInsert insert = new DbDataInsert();
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
