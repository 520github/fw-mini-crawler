package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlRepairTypeEnum;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpEmptyRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.utils.ReflectUtils;
import org.sunso.mini.crawler.parser.html.HtmlFieldParser;
import org.sunso.mini.crawler.parser.html.HtmlFieldParserFactory;
import org.sunso.mini.crawler.parser.html.JsoupHtmlFieldParser;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CrawlerHtmlFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        HtmlFieldParser htmlFieldParser = getHtmlFieldParser(request);
        Field field = request.getField();
        Class<?> type = field.getType();
        boolean isArray = type.isArray();
        boolean isList = containSuperType(type, List.class);
        if (isList) {
            return handleList(request, htmlFieldParser);
        }
        if (isArray) {
            return handleArray(request, htmlFieldParser);
        }
        if (containSuperType(type, CrawlerResult.class)) {
            return handleCrawlerResult(request, htmlFieldParser);
        }

        return htmlFieldParser.selectorObject(field);
    }

    private CrawlerResult handleCrawlerResult(CrawlerFieldParserRequest request, HtmlFieldParser htmlFieldParser) {
        Field field = request.getField();
        String subHtml = htmlFieldParser.selectorHtml(field);
        return request.getCrawlerParser().parse((Class<? extends CrawlerResult>)field.getType(), request.getRequest(), CrawlerHttpResponse.create(subHtml));
    }

    private Object[] handleArray(CrawlerFieldParserRequest request, HtmlFieldParser htmlFieldParser) {
        Class genericClass = request.getField().getType().getComponentType();
        return handleList(genericClass, request, htmlFieldParser).toArray();
    }

    private List<Object> handleList(CrawlerFieldParserRequest request, HtmlFieldParser htmlFieldParser) {
        Field field = request.getField();
        //获取泛型的类型
        Type genericType = field.getGenericType();
        Class genericClass = ReflectUtils.getGenericClass(genericType, 0);
        return handleList(genericClass, request, htmlFieldParser);
    }

    private List<Object> handleList(Class genericClass, CrawlerFieldParserRequest request, HtmlFieldParser htmlFieldParser) {
        Field field = request.getField();
        if (containSuperType(genericClass, CrawlerResult.class)) {
            List<Object> resultList = new ArrayList<>();
            for(String html: htmlFieldParser.selectorHtmlList(field)) {
                CrawlerResult crawlerResult = request.getCrawlerParser().parse(genericClass, request.getRequest(), CrawlerHttpResponse.create(html));
                if (checkFilter(field, crawlerResult) ) {
                    resultList.add(crawlerResult);
                }
            }
            return resultList;
        }
        return checkFilter(field, htmlFieldParser.selectorObjectList(field));
    }

    private HtmlFieldParser getHtmlFieldParser(CrawlerFieldParserRequest request) {
        String body = getResponseBody(request);
        return HtmlFieldParserFactory.getHtmlFieldParser(request.fetchRequestUrl(), body, JsoupHtmlFieldParser.class);
    }

    private String getResponseBody(CrawlerFieldParserRequest request) {
        String body = request.fetchResponseBody();
        if (StrUtil.isBlank(body)) {
            return body;
        }
//        HtmlCssPath htmlCssPath = request.fetchHtmlCssPath();
//        if (htmlCssPath == null) {
//            return body;
//        }
        HtmlRepairTypeEnum repairTypeEnum = getHtmlRepairTypeEnum(request);
        if (repairTypeEnum == null) {
            return body;
        }
        if (HtmlRepairTypeEnum.empty == repairTypeEnum ) {
            return body;
        }
        if (HtmlRepairTypeEnum.none == repairTypeEnum ) {
            return body;
        }
        if (HtmlRepairTypeEnum.tableTag == repairTypeEnum) {
            return "<table>" + body + "</table>";
        }
        return body;
    }

    private HtmlRepairTypeEnum getHtmlRepairTypeEnum(CrawlerFieldParserRequest request) {
        HtmlRepairTypeEnum repairTypeEnum = null;
        CrawlerResultDefine crawlerResultDefine = request.fetchCrawlerResultDefine();
        if (crawlerResultDefine != null && HtmlRepairTypeEnum.empty != crawlerResultDefine.repairType()) {
            repairTypeEnum = crawlerResultDefine.repairType();
        }
        HtmlCssPath htmlCssPath = request.fetchHtmlCssPath();
        if (htmlCssPath != null && HtmlRepairTypeEnum.empty != htmlCssPath.repairType()) {
            repairTypeEnum = htmlCssPath.repairType();
        }
        return repairTypeEnum;
    }

//    private boolean containSuperType(Class<?> type, Class<?> superType) {
//        return ReflectUtils.containSuperType(type, superType);
//    }
}
