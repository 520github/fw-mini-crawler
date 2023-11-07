package org.sunso.mini.crawler.task.model;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.sunso.mini.crawler.common.http.event.CrawlerHttpRequestEvent;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.task.enums.CrawlerUrlLogStatusEnum;

import java.util.Date;
import java.util.Map;

/**
 * @author sunso520
 * @Title:CrawlerUrlLog
 * @Description: <br>
 * @Created on 2023/11/2 10:14
 */

@Data
public class CrawlerUrlLog {
    private Integer id;
    private String bizType;
    private String requestId;
    private String requestUrl;
    private String requestClass;
    private String requestJsonData;
    private String requestExtendJsonData;
    private String requestReadSpider;
    private String requestHandleSpider;
    private String urlResponseStatus;
    private String urlResponseData;
    private String crawlerResult;
    private String crawlerException;
    private String crawlerStep;
    private Date crawlerStartTime;
    private Date crawlerEndTime;
    private int retryNum;
    private int sort;
    private String status;
    private Date createTime;
    private Date updateTime;

    public static CrawlerUrlLog newInstance(String bizType, CrawlerHttpRequest request) {
        CrawlerUrlLog instance = new CrawlerUrlLog();
        instance.setBizType(bizType);
        instance.setRequestId(request.getAndSetRequestIdIfEmpty());
        instance.setRequestUrl(request.getUrl());
        instance.setRequestClass(request.getClass().getName());
        instance.setRequestJsonData(JSONUtil.toJsonStr(request));
        instance.setRequestExtendJsonData(getRequestExtendJsonData(request));
        instance.setRequestReadSpider("default");
        instance.setCrawlerStep("");
        instance.setRetryNum(0);
        instance.setSort(request.getSort());
        instance.setStatus(CrawlerUrlLogStatusEnum.init.getKey());
        instance.setCreateTime(new Date());
        instance.setUpdateTime(new Date());
        return instance;
    }

    private static String getRequestExtendJsonData(CrawlerHttpRequest request) {
        JSONObject jsonObject = new JSONObject();
        Map<String, CrawlerHttpRequestEvent> eventMap =  request.getEvents();
        if (eventMap != null && eventMap.size() > 0) {
            JSONObject eventJson = new JSONObject();
            for(String key: eventMap.keySet()) {
                eventJson.putOnce(key, eventMap.get(key).getClass().getName());
            }
            jsonObject.putOnce("events", eventJson);
        }
        return jsonObject.toString();
    }
}
