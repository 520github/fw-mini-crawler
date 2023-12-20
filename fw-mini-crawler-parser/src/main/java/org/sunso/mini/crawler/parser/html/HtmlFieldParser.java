package org.sunso.mini.crawler.parser.html;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author sunso520
 * @Title:HtmlFieldParser
 * @Description: html解析器接口
 *
 * @Created on 2023/10/12 11:03
 */
public interface HtmlFieldParser {

    /**
     * 根据字段定义， 解析处理，返回字符串列表
     *
     * @param field 字段
     * @return
     */
    List<String> selectorHtmlList(Field field);

    /**
     * 根据字段定义， 解析处理，返回字符串
     *
     * @param field 字段
     * @return
     */
    String selectorHtml(Field field);

    /**
     * 根据字段定义， 解析处理，返回对象
     *
     * @param field 字段
     * @return
     */
    Object selectorObject(Field field);

    /**
     * 根据字段定义， 解析处理，返回对象列表
     *
     * @param field 字段
     * @return
     */
    List<Object> selectorObjectList(Field field);

}
