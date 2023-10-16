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

public class CrawlerRequestDataFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        Field field = request.getField();
        if (isList(field)) {
            return handleList(request);
        }
        else if (isArray(field)) {
            return handleArray(request);
        }
        else if (isCrawlerResult(field)) {
            return handleCrawlerResult(request);
        }
        return getRequestDataValue(request);
    }

    private CrawlerResult handleCrawlerResult(CrawlerFieldParserRequest request) {
        Field field = request.getField();
        Object requestDataValue = getRequestDataValue(request);
        if (requestDataValue == null) {
            return null;
        }
        return request.getCrawlerParser().parse((Class<? extends CrawlerResult>)field.getType(), request.getRequest(), CrawlerHttpResponse.create(requestDataValue.toString()));
    }

    private Object[] handleArray(CrawlerFieldParserRequest request) {
        Class genericClass = request.getField().getType().getComponentType();
        return handleList(genericClass, request).toArray();
    }

    private List<Object> handleList(CrawlerFieldParserRequest request) {
        Field field = request.getField();
        //获取泛型的类型
        Type genericType = field.getGenericType();
        Class genericClass = ReflectUtils.getGenericClass(genericType, 0);
        return handleList(genericClass, request);
    }

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

    private List<Object> jsonArray2List(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        return Arrays.asList(jsonArray.toArray());
    }

    private Object getRequestDataValue(CrawlerFieldParserRequest request) {
        RequestData requestData = request.fetchFieldAnnotation(RequestData.class);
        String jsonKey = requestData.value();
        if (StrUtil.isBlank(jsonKey)) {
            jsonKey = request.fetchFieldName();
        }
        return JSONUtil.getByPath(request.fetchRequestDataJson(), jsonKey);
    }
}
