package org.sunso.mini.crawler.annotation.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:CommonRelationDb
 * @Description: 通用关系数据库注解
 * @Created on 2023/10/19 10:07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonRelationDb {

	/**
	 * 数据库url
	 * @return
	 */
	String url() default "";

	/**
	 * 数据库用户名
	 * @return
	 */
	String user() default "";

	/**
	 * 数据库密码
	 * @return
	 */
	String password() default "";

	/**
	 * 表名
	 * @return
	 */
	String tableName() default "";

	/**
	 * 检查数据是否存在的列表
	 * @return
	 */
	String[] checkExistColumns() default "";

	/**
	 * 指定需要入库的列
	 * @return
	 */
	String[] filterColumns() default "";

	/**
	 * 大写转下滑线
	 * @return
	 */
	boolean isToUnderlineCase() default true;

	/**
	 * 是否忽略空值
	 * @return
	 */
	boolean ignoreNullValue() default true;

	/**
	 * 不存在插入，存在更新
	 * @return
	 */
	boolean insertOrUpdate() default false;

}
