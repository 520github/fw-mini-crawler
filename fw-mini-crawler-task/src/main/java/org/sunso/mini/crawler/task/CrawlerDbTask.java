package org.sunso.mini.crawler.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import org.sunso.mini.crawler.common.db.DbDataInsert;
import org.sunso.mini.crawler.common.db.DbDataSqlQuery;
import org.sunso.mini.crawler.common.db.DbDataSqlUpdate;
import org.sunso.mini.crawler.common.db.HuToolDb;
import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEvent;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.task.enums.CrawlerUrlLogStatusEnum;
import org.sunso.mini.crawler.task.model.CrawlerUrlLog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunso520
 * @Title:CrawlerDbTask
 * @Description: <br>
 * @Created on 2023/11/1 17:18
 */
public class CrawlerDbTask extends AbstractCrawlerTask {

    private static String eventsKey = "events";

    private static String tableName = "crawler_url_log";

    private boolean responseBodySave2File = false;

    private String fileBasePath = "/tmp";

    public CrawlerDbTask setResponseBodySave2File(boolean responseBodySave2File) {
        this.responseBodySave2File = responseBodySave2File;
        return this;
    }

    public CrawlerDbTask setFileBasePath(String fileBasePath) {
        this.fileBasePath = fileBasePath;
        return this;
    }

    @Override
    public void offerTask(CrawlerHttpRequest request) {
        CrawlerUrlLog crawlerUrlLog = CrawlerUrlLog.newInstance(getBizType(), request);
        HuToolDb.insertData(getDbDataInsert(), crawlerUrlLog);
    }

    private DbDataInsert getDbDataInsert() {
        DbDataInsert insert = new DbDataInsert();
        insert.setTableName(tableName);
        return insert;
    }

    @Override
    public CrawlerHttpRequest pollTask() {
        DbDataSqlQuery query = new DbDataSqlQuery();
        query.setSql(getCurrentSpiderSql());
        CrawlerUrlLog crawlerUrlLog = HuToolDb.getDataOne(query, CrawlerUrlLog.class);
        if (crawlerUrlLog != null) {
            return parse2CrawlerHttpRequest(crawlerUrlLog);
        }
        query.setSql(getDefaultSpiderSql());
        crawlerUrlLog = HuToolDb.getDataOne(query, CrawlerUrlLog.class);
        if (crawlerUrlLog != null) {
            return parse2CrawlerHttpRequest(crawlerUrlLog);
        }
        query.setSql(getFailSql());
        crawlerUrlLog = HuToolDb.getDataOne(query, CrawlerUrlLog.class);
        if (crawlerUrlLog != null) {
            return parse2CrawlerHttpRequest(crawlerUrlLog);
        }
        return null;
    }

    @SneakyThrows
    private CrawlerHttpRequest parse2CrawlerHttpRequest(CrawlerUrlLog crawlerUrlLog) {
        DbDataSqlUpdate update = new DbDataSqlUpdate();
        update.setSql(getDoingSql(crawlerUrlLog.getRequestId()));
        HuToolDb.updateData(update);

        JSONObject requestJsonData = JSONUtil.parseObj(crawlerUrlLog.getRequestJsonData());
        JSONObject eventsJsonData = null;

        if (requestJsonData.containsKey(eventsKey)) {
            eventsJsonData = requestJsonData.getJSONObject(eventsKey);
            requestJsonData.remove(eventsKey);
        }

        CrawlerHttpRequest request =  (CrawlerHttpRequest)JSONUtil.toBean(requestJsonData, Class.forName(crawlerUrlLog.getRequestClass()));
        Map<String, CrawlerHttpRequestEvent> eventMap = getCrawlerHttpRequestEventMap(eventsJsonData, crawlerUrlLog.getRequestExtendJsonData());
        if (eventMap != null && !eventMap.isEmpty()) {
            request.setEvents(eventMap);
        }
        return request;
    }

    @SneakyThrows
    private Map<String, CrawlerHttpRequestEvent> getCrawlerHttpRequestEventMap(JSONObject eventsJsonData, String requestExtendJsonData) {
        if (eventsJsonData == null) {
            return null;
        }
        if (requestExtendJsonData == null) {
            return null;
        }
        JSONObject requestExtendJson = JSONUtil.parseObj(requestExtendJsonData);
        if (!requestExtendJson.containsKey(eventsKey)) {
            return null;
        }
        Map<String, CrawlerHttpRequestEvent> eventMap = new HashMap<>();
        JSONObject eventExtendJson = requestExtendJson.getJSONObject(eventsKey);
        for(String eventKey: eventsJsonData.keySet()) {
            JSONObject eventData = eventsJsonData.getJSONObject(eventKey);
            String className = eventExtendJson.getStr(eventKey);
            if (StrUtil.isBlank(className)) {
                continue;
            }
            eventMap.put(eventKey, (CrawlerHttpRequestEvent)JSONUtil.toBean(eventData, Class.forName(className)));
        }
        return eventMap;
    }

    private DbDataSqlQuery getDefaultDbDataSqlQuery() {
        DbDataSqlQuery query = new DbDataSqlQuery();
        query.setSql(getSqlTable() + " where biz_type=? and ");
        return query;
    }

    private String getCurrentSpiderSql() {
        return String.format(getSqlTable() + " where biz_type='%s' and request_spider='%s' and status in ('init') order by sort desc limit 1", getBizType(), currentSpider());
    }

    private String getDefaultSpiderSql() {
        return String.format(getSqlTable() + " where biz_type='%s' and request_spider='default' and status in ('init') order by sort desc limit 1", getBizType());
    }

    private String getFailSql() {
        return String.format(getSqlTable() + " where biz_type='%s' and request_spider='%s' and status in ('fail','exception') order by sort desc limit 1", getBizType(), currentSpider());
    }

    private String getSqlTable() {
        return "select * from " + tableName;
    }

    private String currentSpider() {
        return Thread.currentThread().getName();
    }

    @Override
    public void doneTask(CrawlerHttpRequest request, CrawlerHttpResponse response, CrawlerResult crawlerResult) {
        DbDataSqlUpdate update = new DbDataSqlUpdate();
        update.setSql(getFinishSql(request, response, crawlerResult));
        Object[] param = new Object[2];
        param[0] = getResponseBody(request, response);
        param[1] = getCrawlerResultJsonData(crawlerResult);
        update.setParams(param);
        HuToolDb.updateData(update);
        System.out.println(String.format("doneTask url[%s], responseStatus[%d]", request.getUrl(), response.status()));
    }

    private String getDoingSql(String requestId) {
        StringBuilder sb = new StringBuilder();
        sb.append("update ").append(tableName).append(" set ");
        sb.append("status=").append(getFlagValue(CrawlerUrlLogStatusEnum.doing.getKey())).append(",");
        sb.append("request_spider=").append(getFlagValue(currentSpider())).append(", ");
        sb.append("update_time=").append(getFlagValue(getNowDateTime())).append(",");
        sb.append("crawler_start_time=").append(getFlagValue(getNowDateTime()));
        sb.append(" where request_id=").append(getFlagValue(requestId));
        return sb.toString();
    }

    private String getNowDateTime() {
        return DateUtil.formatDateTime(new Date());
    }

    private String getFlagValue(String value) {
        if (value == null) {
            return null;
        }
        return "'" + value + "'";
    }

    private String getFinishSql(CrawlerHttpRequest request, CrawlerHttpResponse response, CrawlerResult crawlerResult) {
        StringBuilder sb = new StringBuilder();
        sb.append("update ").append(tableName).append(" set ");
        sb.append("url_response_status").append("=").append("%d,");
        sb.append("url_response_data").append("=?,");//.append("'%s',");
        sb.append("crawler_result").append("=?,");//.append("'%s',");
        sb.append("status").append("=").append("'%s',");
        sb.append("update_time='%s',");
        sb.append("crawler_end_time='%s' ");
        sb.append(" where request_id=").append("'%s'");
        return String.format(sb.toString(), response.status(), getStatus(response, crawlerResult),
                getNowDateTime(), getNowDateTime(),
                request.getRequestId());
    }

    private String getResponseBody(CrawlerHttpRequest request, CrawlerHttpResponse response) {
        if (response == null) {
            return null;
        }
        if (responseBodySave2File) {
            String filePathName = getFilePath()+getFileName(request,response);
            FileUtil.appendString(response.body(), filePathName, response.charset());
            return filePathName;
        }
        return response.body();
    }

    private String getFilePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(fileBasePath);
        if (!fileBasePath.endsWith("/")) {
            sb.append("/");
        }
        sb.append(getBizType()).append("/");
        sb.append(new DateTime().toString("yyyy/MM/dd")+"/");

        return sb.toString();
    }

    private String getFileName(CrawlerHttpRequest request, CrawlerHttpResponse response) {
        return request.getRequestId() + "." + response.getResponseDataType();
    }

    private String getCrawlerResultJsonData(CrawlerResult crawlerResult) {
        if (crawlerResult == null) {
            return null;
        }
        return JSONUtil.toJsonStr(crawlerResult);
    }

    private String getStatus(CrawlerHttpResponse response, CrawlerResult crawlerResult) {
        if (crawlerResult != null) {
            return CrawlerUrlLogStatusEnum.success.getKey();
        }
        return CrawlerUrlLogStatusEnum.fail.getKey();
    }
}
