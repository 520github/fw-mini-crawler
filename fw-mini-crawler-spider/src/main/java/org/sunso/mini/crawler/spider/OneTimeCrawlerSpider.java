package org.sunso.mini.crawler.spider;

import lombok.SneakyThrows;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.common.utils.UrlUtils;
import org.sunso.mini.crawler.context.CrawlerContext;
import org.sunso.mini.crawler.handler.CrawlerHandler;
import org.sunso.mini.crawler.handler.CrawlerHandlerFactory;

import java.util.Map;

public class OneTimeCrawlerSpider extends AbstractCrawlerSpider {

    public OneTimeCrawlerSpider(CrawlerContext context) {
        super(context);
    }

    @SneakyThrows
    @Override
    public void run() {
        for(int i=0; i<10; i++) {
            CrawlerHttpRequest request = getRequestFromCrawlerQueue();
            if (request == null) {
                System.out.println("没有需要爬取的url，退出");
                break;
            }
            System.out.println("start download");
            CrawlerHttpResponse response = getDownloader().download(request);
            //System.out.println("body:" + response.body());
            CrawlerResult crawlerResult = context.getParser().parse(getCrawlerResultClass(request), request, response);
            System.out.println("crawlerResult:" + crawlerResult);

            //CrawlerHandlerFactory.doCrawlerHandler(crawlerResult);
            Thread.sleep(i*1000);
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
        return null;
    }


}
