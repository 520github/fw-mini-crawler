package org.sunso.mini.crawler.spider;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.utils.UrlUtils;
import org.sunso.mini.crawler.context.CrawlerContext;
import org.sunso.mini.crawler.context.CrawlerContextThreadLocal;

import java.util.Map;

@Slf4j
public class OneTimeCrawlerSpider extends AbstractCrawlerSpider {

    public OneTimeCrawlerSpider(CrawlerContext context) {
        super(context);
    }

    @SneakyThrows
    @Override
    public void run() {
        CrawlerContextThreadLocal.set(this.context);
        while (true) {
            CrawlerHttpRequest request = getRequestFromCrawlerQueue();
            if (request == null) {
                System.out.println("没有需要爬取的url，退出");
                break;
            }
            System.out.println("start download:" + Thread.currentThread().getName());
            Class<? extends CrawlerResult> clazz = getCrawlerResultClass(request);
            if (clazz == null) {
                log.error("url[{}] not found crawlerResult define", request.getUrl());
                continue;
            }
            CrawlerHttpResponse response = getDownloader(clazz).download(request);
            //System.out.println("body:" + response.body());
            CrawlerResult crawlerResult = getCrawlerParser(clazz).parse(clazz, request, response);
            System.out.println("crawlerResult:" + crawlerResult);

            //CrawlerHandlerFactory.doCrawlerHandler(crawlerResult);
            //Thread.sleep(i*1000);
        }
    }

    private Class<? extends CrawlerResult> getCrawlerResultClass(CrawlerHttpRequest request) {
        Map<String, Class<? extends CrawlerResult>> urlCrawlerResultMap =  context.getUrlCrawlerResultMap();
        if (urlCrawlerResultMap == null || urlCrawlerResultMap.size() == 0) {
            return null;
        }
        for(String url: urlCrawlerResultMap.keySet()) {
            Map<String,String> parameterMap = UrlUtils.urlMatch(url, request.getUrl());
            if (parameterMap != null) {
                request.setParameters(parameterMap);
                return urlCrawlerResultMap.get(url);
            }
        }
        String urlAlias = request.getUrlAlias();
        if (StrUtil.isNotBlank(urlAlias)) {
            return urlCrawlerResultMap.get(urlAlias);
        }
        return null;
    }

}
