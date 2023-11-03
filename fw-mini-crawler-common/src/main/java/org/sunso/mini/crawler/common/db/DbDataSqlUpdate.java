package org.sunso.mini.crawler.common.db;

import lombok.Data;

/**
 * @author sunso520
 * @Title:DbDataSqlUpdate
 * @Description: <br>
 * @Created on 2023/11/2 18:06
 */
@Data
public class DbDataSqlUpdate extends DbDataTable {
    private String sql;

    private Object[] params;
}
