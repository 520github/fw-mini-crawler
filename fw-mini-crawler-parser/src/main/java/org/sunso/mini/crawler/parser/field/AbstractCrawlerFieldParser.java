package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.expression.ExpressionUtil;
import cn.hutool.json.JSONUtil;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.result.FieldCheckFailCrawlerResult;
import org.sunso.mini.crawler.common.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunso520
 * @Title: AbstractCrawlerFieldParser
 * @Description: 爬虫字段解析器抽象类<br>
 * @Created on 2023/10/13 11:06
 */
public abstract class AbstractCrawlerFieldParser implements CrawlerFieldParser {

    /**
     * 判断字段是否是数组
     *
     * @param field 字段
     * @return 返回是否是数组
     */
    protected boolean isArray(Field field) {
        return field.getType().isArray();
    }

    /**
     * 判断字段是否是列表
     *
     * @param field 字段
     * @return 返回是否是列表
     */
    protected boolean isList(Field field) {
        return containSuperType(field.getType(), List.class);
    }

    /**
     * 判断字段是否是CrawlerResult接口
     *
     * @param field 字段
     * @return 返回是否是CrawlerResult接口
     */
    protected boolean isCrawlerResult(Field field) {
        return containSuperType(field.getType(), CrawlerResult.class);
    }

    /**
     * 判断类是否实现了CrawlerResult接口
     *
     * @param clazz 类
     * @return 返回是否实现了CrawlerResult接口
     */
    protected boolean isCrawlerResult(Class clazz) {
        return containSuperType(clazz, CrawlerResult.class);
    }


    /**
     * 判断类是否继承或实现了某个父类
     *
     * @param type  类
     * @param superType 父类
     * @return
     */
    protected boolean containSuperType(Class<?> type, Class<?> superType) {
        return ReflectUtils.containSuperType(type, superType);
    }

    /**
     * 获取字段对应的类型
     * @param field 字段
     * @return Class
     */
    protected Class getGenericClass(Field field) {
        Type genericType = field.getGenericType();
        return ReflectUtils.getGenericClass(genericType, 0);
    }

    /**
     * 对字段类型为CrawlerResult进行filter条件过滤
     * <p><p/>
     * <ul>
     *     <li>@JsonPath(value = "data.list", expressionFilter = "viewNumList>0 && viewNumList<=5")</li>
     * </ul>
     * @param field 字段
     * @param crawlerResult CrawlerResult
     * @return
     */
    protected boolean checkFilter(Field field, CrawlerResult crawlerResult) {
        if (crawlerResult == null) {
            return false;
        }
        if (crawlerResult instanceof FieldCheckFailCrawlerResult) {
            return false;
        }
        String expression = getExpression(field);
        if (StrUtil.isBlank(expression)) {
            return true;
        }
        return evalExpression(expression, JSONUtil.toBean(JSONUtil.toJsonStr(crawlerResult), Map.class));
    }

    /**
     * 对字段值进行filter条件过滤
     *
     * @param field 字段
     * @param list 字段列表值
     * @return 返回是否满足filter条件过滤
     */
    protected List<Object> checkFilter(Field field, List<Object> list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        List<Object> resultList = new ArrayList<>();
        for(Object value: list) {
            if (checkFilter(field, value) && !resultList.contains(value)) {
                resultList.add(value);
            }
        }
        return resultList;
    }

    /**
     * 对字段值进行filter条件过滤
     *
     * @param field 字段
     * @param value 字段值
     * @return 返回是否满足filter条件过滤
     */
    protected boolean checkFilter(Field field, Object value) {
        String expression = getExpression(field);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put(field.getName(), value);
        return evalExpression(expression, dataMap);
    }

    /**
     * 获取数据过滤器表达式
     *
     * @param field 字段
     * @return 返回数据过滤器表达式
     */
    protected String getExpression(Field field) {
        if (field.isAnnotationPresent(HtmlCssPath.class)) {
            return field.getAnnotation(HtmlCssPath.class).expressionFilter();
        }
        else if (field.isAnnotationPresent(JsonPath.class)) {
            return field.getAnnotation(JsonPath.class).expressionFilter();
        }
        return null;
    }

    /**
     * 执行表达式
     * @param expression 表达式
     * @param dataMap 数据
     *
     * @return 返回表达式执行结果
     */
    protected boolean evalExpression(String expression, Map<String, Object> dataMap) {
        Object result = ExpressionUtil.eval(expression, dataMap);
        if (result instanceof Boolean) {
            return (Boolean) result;
        }
        return true;
    }

    protected CrawlerFieldParserRequest newCrawlerFieldParserRequest(CrawlerFieldParserRequest request, CrawlerHttpResponse response) {
        CrawlerFieldParserRequest instance = request.cloneExcludeResponse();
        instance.setResponse(response);
        return instance;
    }

}
