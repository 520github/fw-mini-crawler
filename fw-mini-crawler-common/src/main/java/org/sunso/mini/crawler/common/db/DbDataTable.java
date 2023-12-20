package org.sunso.mini.crawler.common.db;

import lombok.Data;

/**
 * @author sunso520
 * @Title:DbDataTable
 * @Description: <br>
 * @Created on 2023/11/2 13:46
 */
@Data
public class DbDataTable extends DbDataSource {

	private String tableName;

	private String[] filterColumns;

	private boolean isToUnderlineCase = true;

	private boolean ignoreNullValue = true;

}
