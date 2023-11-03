package org.sunso.mini.crawler.common.db;

import com.sun.org.apache.xpath.internal.operations.String;
import lombok.Data;

import java.util.Collection;
import java.util.List;

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
