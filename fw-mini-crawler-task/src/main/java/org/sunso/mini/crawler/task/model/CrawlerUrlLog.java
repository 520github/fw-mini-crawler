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
 * @Description: 爬虫url日志表<br>
 * @Created on 2023/11/2 10:14
 */

@Data
public class CrawlerUrlLog {
    //主键id
    private Integer id;
    //业务类型
    private String bizType;
    //请求id
    private String requestId;
    //请求url
    private String requestUrl;
    //请求class类
    private String requestClass;
    //请求json数据
    private String requestJsonData;
    //请求扩展json数据
    private String requestExtendJsonData;
    //读取请求线程
    private String requestReadSpider;
    //处理请求线性
    private String requestHandleSpider;
    //下载url返回状态
    private String urlResponseStatus;
    //下载url返回数据
    private String urlResponseData;
    //爬虫处理结果
    private String crawlerResult;
    //爬虫异常
    private String crawlerException;
    //爬虫所处步骤
    private String crawlerStep;
    //爬虫开始时间
    private Date crawlerStartTime;
    //爬虫结束时间
    private Date crawlerEndTime;
    //重试次数
    private int retryNum;
    //排序
    private int sort;
    //状态
    private String status;
    //创建时间
    private Date createTime;
    //更新时间
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
