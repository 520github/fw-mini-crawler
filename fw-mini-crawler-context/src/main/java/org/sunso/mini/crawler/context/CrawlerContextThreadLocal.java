package org.sunso.mini.crawler.context;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;

public class CrawlerContextThreadLocal {
    private static ThreadLocal<CrawlerContext> crawlerContextThreadLocal = new ThreadLocal<>();


    public static void set(CrawlerContext context) {
        System.out.println("set thread name:" + Thread.currentThread().getName());
        System.out.println("set context:" + context);
        crawlerContextThreadLocal.set(context);
    }

    public static CrawlerContext get() {
        return crawlerContextThreadLocal.get();
    }

    public static void offerRequest(CrawlerHttpRequest request) {
        System.out.println("offerRequest thread name:" + Thread.currentThread().getName());
        CrawlerContext context = get();
        if (context == null) {
            System.out.println("offerRequest thread name:" + Thread.currentThread().getName() + " find CrawlerContext is null");
            return;
        }
        get().getQueue().offer(request);
    }
}
