package org.sunso.mini.crawler.common.db;

import lombok.Data;

/**
 * @author sunso520
 * @Title:DbDataSqlQuery
 * @Description: <br>
 * @Created on 2023/11/2 15:25
 */
@Data
public class DbDataSqlQuery extends DbDataTable {

	private String sql;

	private Object[] params;

}
