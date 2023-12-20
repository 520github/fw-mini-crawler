package org.sunso.mini.crawler.common.db;

import lombok.Data;

/**
 * @author sunso520
 * @Title:DbSqlCount
 * @Description: <br>
 * @Created on 2023/11/10 10:31
 */
@Data
public class DbSqlCount extends DbDataTable {

	private String sql;

	private Object[] params;

}
