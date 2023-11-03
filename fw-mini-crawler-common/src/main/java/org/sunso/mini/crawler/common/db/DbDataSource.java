package org.sunso.mini.crawler.common.db;

import lombok.Data;

/**
 * @author sunso520
 * @Title:DbDataSource
 * @Description: <br>
 * @Created on 2023/11/2 13:44
 */
@Data
public class DbDataSource {
    private String url;
    private String user;
    private String password;

}
