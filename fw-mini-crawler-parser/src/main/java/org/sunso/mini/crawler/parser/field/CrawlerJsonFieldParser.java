package org.sunso.mini.crawler.parser.field;

import cn.hutool.json.JSONArray;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.parser.json.HuToolJsonFieldParser;
import org.sunso.mini.crawler.parser.json.JsonFieldParser;
import org.sunso.mini.crawler.parser.json.JsonFieldParserFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CrawlerJsonFieldParser extends AbstractCrawlerFieldParser {

    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        return parseJsonField(request);
    }

    private Object parseJsonField(CrawlerFieldParserRequest request) {
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
        JsonFieldParser jsonFieldParser = getJsonFieldParser(request);
        return jsonFieldParser.selectorObject(field);
    }

    private CrawlerResult handleCrawlerResult(CrawlerFieldParserRequest request) {
        Field field = request.getField();
        JsonFieldParser jsonFieldParser = getJsonFieldParser(request);
        Object result = jsonFieldParser.selectorObject(field);
        return request.getCrawlerParser().parse((Class<? extends CrawlerResult>)field.getType(), request.getRequest(), CrawlerHttpResponse.create(result.toString()));
    }

    private List<Object> handleList(CrawlerFieldParserRequest request) {
        return handleList(getGenericClass(request.getField()), request);
    }

    private Object[] handleArray(CrawlerFieldParserRequest request) {
        Class genericClass = request.getField().getType().getComponentType();
        return handleList(genericClass, request).toArray();
    }

    private List<Object> handleList(Class genericClass, CrawlerFieldParserRequest request) {
        Field field = request.getField();
        JsonFieldParser jsonFieldParser = getJsonFieldParser(request);
        if (!isCrawlerResult(genericClass)) {
            return (List)jsonFieldParser.selectorObject(field);
        }
        JSONArray jsonArray = (JSONArray)jsonFieldParser.selectorObject(field);
        List<Object> resultList = new ArrayList<>();
        for(Object json: jsonArray.toArray()) {
            CrawlerResult crawlerResult = request.getCrawlerParser().parse(genericClass, request.getRequest(), CrawlerHttpResponse.create(json.toString()));
            resultList.add(crawlerResult);
        }
        return resultList;
    }

    private JsonFieldParser getJsonFieldParser(CrawlerFieldParserRequest request) {
        return getJsonFieldParser(request.fetchResponseBody(), request);
    }

    private JsonFieldParser getJsonFieldParser(String jsonData, CrawlerFieldParserRequest request) {
        return JsonFieldParserFactory.getJsonFieldParser(jsonData, HuToolJsonFieldParser.class);
    }


}
