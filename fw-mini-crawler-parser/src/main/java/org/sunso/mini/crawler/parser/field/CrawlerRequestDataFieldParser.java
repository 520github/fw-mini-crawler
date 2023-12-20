package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.sunso.mini.crawler.annotation.request.RequestData;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sunso520
 * @Title:CrawlerRequestDataFieldParser
 * @Description: 字段为请求数据的解析处理<br>
 * @Created on 2023/10/16 10:11
 */
public class CrawlerRequestDataFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        Field field = request.getField();
        // 字段为列表情况
        if (isList(field)) {
            return handleList(request);
        }
        // 字段为数组情况
        else if (isArray(field)) {
            return handleArray(request);
        }
        // 字段为CrawlerResult类型
        else if (isCrawlerResult(field)) {
            return handleCrawlerResult(request);
        }
        return getRequestDataValue(request);
    }

    /**
     * 处理字段为CrawlerResult类型的情况
     *
     * @param request 字段解析请求参数
     * @return
     */
    private CrawlerResult handleCrawlerResult(CrawlerFieldParserRequest request) {
        Field field = request.getField();
        Object requestDataValue = getRequestDataValue(request);
        if (requestDataValue == null) {
            return null;
        }
        return request.getCrawlerParser().parse((Class<? extends CrawlerResult>)field.getType(), request.getRequest(), CrawlerHttpResponse.create(requestDataValue.toString()));
    }

    /**
     * 处理字段为数组的情况
     *
     * @param request 字段解析请求参数
     * @return
     */
    private Object[] handleArray(CrawlerFieldParserRequest request) {
        Class genericClass = request.getField().getType().getComponentType();
        return handleList(genericClass, request).toArray();
    }

    /**
     * 处理字段为列表的情况
     *
     * @param request 字段解析请求参数
     * @return
     */
    private List<Object> handleList(CrawlerFieldParserRequest request) {
        Field field = request.getField();
        //获取泛型的类型
        Type genericType = field.getGenericType();
        Class genericClass = ReflectUtils.getGenericClass(genericType, 0);
        return handleList(genericClass, request);
    }

    /**
     * 处理字段为列表的情况
     *
     * @param genericClass 字段对应类型
     * @param request 字段解析请求参数
     * @return
     */
    private List<Object> handleList(Class genericClass, CrawlerFieldParserRequest request) {
        JSONArray jsonArray = (JSONArray) getRequestDataValue(request);
        if (containSuperType(genericClass, CrawlerResult.class)) {
            List<Object> resultList = new ArrayList<>();
            for(Object object: jsonArray.toArray()) {
                CrawlerResult crawlerResult = request.getCrawlerParser().parse(genericClass, request.getRequest(), CrawlerHttpResponse.create(object.toString()));
                resultList.add(crawlerResult);
            }
            return resultList;
        }
        return jsonArray2List(jsonArray);
    }

    /**
     * 把json数组转化为list
     *
     * @param jsonArray json数组
     * @return 返回list列表数据
     */
    private List<Object> jsonArray2List(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        return Arrays.asList(jsonArray.toArray());
    }

    /**
     * 从RequestData里获取指定key的数据
     *
     * @param request 字段解析请求参数
     * @return
     */
    private Object getRequestDataValue(CrawlerFieldParserRequest request) {
        RequestData requestData = request.fetchFieldAnnotation(RequestData.class);
        String jsonKey = requestData.value();
        if (StrUtil.isBlank(jsonKey)) {
            jsonKey = request.fetchFieldName();
        }
        return JSONUtil.getByPath(request.fetchRequestDataJson(), jsonKey);
    }
}
