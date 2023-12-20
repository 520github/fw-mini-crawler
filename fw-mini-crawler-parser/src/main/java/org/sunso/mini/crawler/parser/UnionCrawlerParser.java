package org.sunso.mini.crawler.parser;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;
import org.sunso.mini.crawler.annotation.field.FieldDefine;
import org.sunso.mini.crawler.annotation.html.HtmlUrl;
import org.sunso.mini.crawler.annotation.order.FieldOrder;
import org.sunso.mini.crawler.annotation.result.CrawlerResultChecker;
import org.sunso.mini.crawler.checker.CrawlerCheckerFactory;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.result.FieldCheckFailCrawlerResult;
import org.sunso.mini.crawler.context.CrawlerContextThreadLocal;
import org.sunso.mini.crawler.handler.CrawlerHandlerFactory;
import org.sunso.mini.crawler.parser.field.CrawlerFieldParserRequest;
import org.sunso.mini.crawler.parser.type.TypeConverters;
import org.sunso.mini.crawler.storage.data.CrawlerDataStorageFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author sunso520
 * @Title: UnionCrawlerParser
 * @Description: 统一爬虫解析器<br>
 * @Created on 2023/10/12 10:06
 */
@Slf4j
public class UnionCrawlerParser extends AbstractCrawlerParser {

    @Override
    public CrawlerResult parse(Class<? extends CrawlerResult> clazz, CrawlerHttpRequest request, CrawlerHttpResponse response) {
        Set<Field> fieldSet =  ReflectionUtils.getAllFields(clazz);
        Map<String, Object> dataMap = new HashMap<>();
        CrawlerFieldParserRequest parserRequest = CrawlerFieldParserRequest.newInstance(request, response, this, clazz);
        Map<Integer, List<Field>> orderFieldMap = new TreeMap<>();
        for(Field field: fieldSet) {
            // 字段含有优先级顺序，先设置到TreeMap中，后续在单独处理
            if (field.isAnnotationPresent(FieldOrder.class)) {
                setFiled2OrderFieldList(field, orderFieldMap);
                continue;
            }
            handleField2Map(field, parserRequest, dataMap);
            if (!checker(field, dataMap, clazz)) {
                return FieldCheckFailCrawlerResult.newInstance(field.getName());
            }
        }
        // 处理含有优先级顺序的字段， 按字段优先级顺序进行处理
        for(Integer sort: orderFieldMap.keySet()) {
            for(Field field: orderFieldMap.get(sort)) {
                handleField2Map(field, parserRequest, dataMap);
                if (!checker(field, dataMap, clazz)) {
                    return FieldCheckFailCrawlerResult.newInstance(field.getName());
                }
            }
        }
        // 把dataMap转换成CrawlerResult对象
        CrawlerResult result = BeanUtil.mapToBean(dataMap, clazz, true);
        // 针对CrawlerResult进行爬虫执行器处理
        CrawlerHandlerFactory.doCrawlerHandler(result);
        // 针对CrawlerResult进行数据存储处理
        CrawlerDataStorageFactory.doDataStorage(result);
        return result;
    }

    /**
     * 如果该字段上有配置检查器的话，在解析完获取该字段值之后，进行校验器的校验处理
     *
     * @param field  字段
     * @param dataMap  已解析的字段对应值
     * @param clazz CrawlerResult类
     * @return 返回是否通过校验
     */
    private boolean checker(Field field, Map<String, Object> dataMap, Class<? extends CrawlerResult> clazz) {
        CrawlerResultChecker checker = field.getAnnotation(CrawlerResultChecker.class);
        if (checker == null) {
            return true;
        }
        return CrawlerCheckerFactory.getCrawlerChecker(checker.checker()).check(BeanUtil.mapToBean(dataMap, clazz, true));
    }

    /**
     * 对含有优先级顺序字段，先进行排序
     *
     * @param field 字段
     * @param orderFieldMap 含有优先级顺序的字段map
     */
    private void setFiled2OrderFieldList(Field field, Map<Integer, List<Field>> orderFieldMap) {
        FieldOrder fieldOrder = field.getAnnotation(FieldOrder.class);
        List<Field> fieldList = orderFieldMap.get(fieldOrder.sort());
        if (fieldList == null) {
            fieldList = new ArrayList<>();
        }
        fieldList.add(field);
        orderFieldMap.put(fieldOrder.sort(), fieldList);
    }

    /**
     *
     * @param field 字段
     * @param parserRequest 字段解析请求参数
     * @param dataMap 已解析字段值Map
     */
    private void handleField2Map(Field field, CrawlerFieldParserRequest parserRequest, Map<String, Object> dataMap) {
        try {
            // 解析获取字段值
            Object fieldValue = handleField(field, parserRequest);
            if (fieldValue == null) {
                return;
            }
            // 字段是url链接类型，看是否要触发点击操作
            if (field.isAnnotationPresent(HtmlUrl.class)) {
                handleHtmlUrlTriggerClick(field, fieldValue, parserRequest);
            }
            // 字段解析值设置到map中
            dataMap.put(field.getName(), fieldValue);
        }catch (Exception e) {
            FieldDefine fieldDefine = field.getAnnotation(FieldDefine.class);
            // 发生异常时，如果没有定义默认值，就抛出异常
            if (fieldDefine == null || fieldDefine.defaultValue() == null) {
                log.info(String.format("解析获取字段[%s]对应值发生异常[%s]", field.getName(), e.getMessage()), e);
                throw e;
            }
            // 获取定义的默认值
            Object fieldValue = TypeConverters.getValue(field.getType(), fieldDefine.defaultValue(), null);
            dataMap.put(field.getName(), fieldValue);
        }
    }

    /**
     * 对url类型对字段进行模拟点击操作
     *
     * @param field   字段
     * @param fieldValue 字段值
     * @param parserRequest 字段解析请求参数
     */
    private void handleHtmlUrlTriggerClick(Field field, Object fieldValue, CrawlerFieldParserRequest parserRequest) {
        HtmlUrl htmlUrl = field.getAnnotation(HtmlUrl.class);
        if (!htmlUrl.triggerClick()) {
            return;
        }
        if (fieldValue instanceof List) {
            for(String subUrl: (List<String>)fieldValue) {
                handleHtmlUrlTriggerClick(htmlUrl, subUrl, parserRequest);
            }
        }
        else {
            handleHtmlUrlTriggerClick(htmlUrl, fieldValue.toString(), parserRequest);
        }
    }

    /**
     * 对url模拟触发点击操作
     *
     * @param htmlUrl HtmlUrl注解
     * @param subUrl  当前请求下的子url
     * @param parserRequest 字段解析请求参数
     */
    private void handleHtmlUrlTriggerClick(HtmlUrl htmlUrl, String subUrl, CrawlerFieldParserRequest parserRequest) {
        if(StrUtil.isBlank(subUrl) || !subUrl.startsWith("http")) {
            return;
        }
        CrawlerHttpRequest subRequest = parserRequest.subRequest(htmlUrl, subUrl);
        CrawlerContextThreadLocal.offerRequest(subRequest);
    }
}
