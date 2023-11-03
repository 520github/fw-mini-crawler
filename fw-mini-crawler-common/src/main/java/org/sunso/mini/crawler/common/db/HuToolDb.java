package org.sunso.mini.crawler.common.db;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author sunso520
 * @Title:HuToolDb
 * @Description: <br>
 * @Created on 2023/11/2 11:56
 */
public class HuToolDb {

    @SneakyThrows
    public static int updateData(DbDataSqlUpdate update) {
        Db db = getDb(update);
        return db.execute(update.getSql(), update.getParams());
    }

    public static Long insertData(DbDataInsert insert, Object data) {
        Db db = getDb(insert);
        Entity entity = getEntity(insert, data);
        if (checkExistData(insert, db, entity)) {
            return Long.valueOf(-1);
        }
        return HuToolDb.insertData(db, entity);
    }

    @SneakyThrows
    public static <T> T getDataOne(DbDataSqlQuery query, Class<T> beanClass) {
        List<T> list =  getDataList(query, beanClass);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @SneakyThrows
    public static <T> List<T> getDataList(DbDataSqlQuery query, Class<T> beanClass) {
        Db db = getDb(query);
        return db.query(query.getSql(), beanClass, query.getParams());
    }

    public static boolean checkExistData(DbDataInsert insert, Db db, Entity entity) {
        String[] columns = insert.getCheckExistColumns();
        if (columns == null || columns.length < 1) {
            return false;
        }
        try {
            List<Entity> list = db.find(Arrays.asList(columns), entity);
            if (list!= null && list.size() > 0) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static Entity getEntity(DbDataTable table, Object data) {
        Entity entity = Entity.create(table.getTableName()).parseBean(data, table.isToUnderlineCase(), table.isIgnoreNullValue());
        String filterColumns[] = table.getFilterColumns();
        if (filterColumns != null && filterColumns.length > 0) {
            return entity.filter(filterColumns);
        }
        return entity;
    }

    @SneakyThrows
    public static Long insertData(Db db, Entity entity) {
        return db.insertForGeneratedKey(entity);
    }

    public static DataSource getDataSource(DbDataSource dataSource)  {
        String url = dataSource.getUrl();
        String user = dataSource.getUser();
        if (StrUtil.isBlank(url) || StrUtil.isBlank(user)) {
            return null;
        }
        return new SimpleDataSource(url, user, dataSource.getPassword());
    }

    public static Db getDb(DbDataSource dataSource) {
        DataSource ds = getDataSource(dataSource);
        if (ds == null) {
            return Db.use();
        }
        return Db.use(ds);
    }
}
