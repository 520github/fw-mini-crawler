package org.sunso.mini.crawler.common.db;

import lombok.Data;

/**
 * @author sunso520
 * @Title:DbDataInsert
 * @Description: <br>
 * @Created on 2023/11/2 13:47
 */
@Data
public class DbDataInsert extends DbDataTable {

	private String[] checkExistColumns;

}
