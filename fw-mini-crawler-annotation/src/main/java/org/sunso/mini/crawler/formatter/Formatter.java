package org.sunso.mini.crawler.formatter;

/**
 * @author sunso520
 * @Title:Formatter
 * @Description: 数据格式化，如
 * <ul>
 * <li>把long日期格式化成Date日期</li>
 * <li>去掉%格式化</li>
 * <li>去掉单位元格式化</li>
 * <li>......等</li>
 * <ul/>
 * @Created on 2023/11/14 15:29
 */
public interface Formatter {

	/**
	 * 数据格式化
	 *
	 * <ul>
	 * <li>把long日期格式化成Date日期</li>
	 * <li>去掉%格式化</li>
	 * <li>去掉单位元格式化</li>
	 * <li>......等</li>
	 * <ul/>
	 * @param value 需要格式化的值
	 * @return 返回格式化后的值
	 */
	Object format(Object value);

}
