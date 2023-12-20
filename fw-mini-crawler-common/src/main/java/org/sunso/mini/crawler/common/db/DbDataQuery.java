package org.sunso.mini.crawler.common.db;

import lombok.Data;

import java.util.Collection;

/**
 * @author sunso520
 * @Title:DbDataQuery
 * @Description: <br>
 * @Created on 2023/11/2 14:13
 */
@Data
public class DbDataQuery extends DbDataTable {

    private Collection<String> queryColumns;
}
