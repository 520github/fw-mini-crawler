package org.sunso.mini.crawler.spider;

import lombok.SneakyThrows;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.context.CrawlerContext;

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
            CrawlerHttpResponse response = getDownloader().download(request);
            CrawlerResult crawlerResult = context.getParser().parse(null, request, response);
            Thread.sleep(i*1000);
        }
    }
}
