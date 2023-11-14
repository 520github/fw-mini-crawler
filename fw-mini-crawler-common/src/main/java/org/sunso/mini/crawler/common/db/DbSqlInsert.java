package org.sunso.mini.crawler.common.db;

import lombok.Data;

/**
 * @author sunso520
 * @Title:DbSqlInsert
 * @Description: <br>
 * @Created on 2023/11/14 10:11
 */
@Data
public class DbSqlInsert extends DbDataSource {
    private String sql;

    private Object[] params;

    public static DbSqlInsert newInstance(String sql) {
        DbSqlInsert insert = new DbSqlInsert();
        insert.setSql(sql);
        return insert;
    }
}
