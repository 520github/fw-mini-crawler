package org.sunso.mini.crawler.downloader;

import org.junit.Test;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpGetRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpPostRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

public class HutoolCrawlerDownloaderTest extends BaseTest {

    private CrawlerDownloader crawlerDownloader = new HutoolCrawlerDownloader();
    @Test
    public void getSimpleTest() {
        String url = "http://www.zhongzhenglawyer.com/Content/2232432.html";
        CrawlerHttpGetRequest get = new CrawlerHttpGetRequest(url);
        CrawlerHttpResponse response = crawlerDownloader.download(get);
        //
    }

    @Test
    public void getHeaderAndCookiesTest() {
        int page =1;
        String url = "http://www.zhongzhenglawyer.com/index.php?c=Front/LoadModulePageData&PageNo="+page+"&ClassID=0&responseModuleId=420292825";
        CrawlerHttpGetRequest start = new CrawlerHttpGetRequest(url);
        start.addCookie("53gid2", "12490367064007");
        start.addCookie("53revisit", "1686296417474");
        start.addCookie("Lang", "cn");
        start.addCookie("InitSiteID", "83330");
        start.addCookie("SiteType", "1");
        start.addCookie("IsDefaultLang", "1");
        start.addCookie("WUserID", "16460279052931");
        start.addCookie("PHPSESSID", "a131393c4af2ca0094378c4d610f95c1");
        start.addCookie("count_clientid", "a98ec59d3c70eced68fd34e762f1aad7");
        start.addCookie("HadVisited", "1");
        start.addCookie("visitor_type", "old");

        start.addCookie("53kf_72307521_from_host", "www.zhongzhenglawyer.com");
        start.addCookie("uuid_53kf_72307521", "13f9c358c495ae5515418d31333461b8");
        start.addCookie("53kf_72307521_land_page", "http%253A%252F%252Fwww.zhongzhenglawyer.com%252F");
        start.addCookie("kf_72307521_land_page_ok", "1");

        start.addCookie("53uvid", "1");
        start.addCookie("onliner_zdfq72307521", "0");
        start.addCookie("53kf_72307521_keyword", "http%3A%2F%2Fwww.zhongzhenglawyer.com%2F");


        //start.addCookie("SUV", "1460541527037365");
        start.addHeader("Host", "www.zhongzhenglawyer.com");
        start.addHeader("X-Requested-With", "XMLHttpRequest");
        start.addHeader("Accept", "*/*");
        start.addHeader("Accept-Encoding", "gzip, deflate");
        start.addHeader("Referer", "http://www.zhongzhenglawyer.com/Content/1871484.html");
        start.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
        CrawlerHttpResponse response = crawlerDownloader.download(start);
    }

    @Test
    public void getWwwFormTest() {
        String url = "https://dev-cqb.lddstp.com/zz-official/article/info/detail/40";
        CrawlerHttpGetRequest getRequest = new CrawlerHttpGetRequest(url);
        //getRequest.setContentType("application/x-www-form-urlencoded");
        crawlerDownloader.download(getRequest);
    }

    @Test
    public void postJsonTest() {
        String url = "https://dev-cqb.lddstp.com/zz-official/article/info/page/list";
        CrawlerHttpPostRequest postRequest = new CrawlerHttpPostRequest(url);
        postRequest.addData("pageNum", "1");
        postRequest.addData("pageSize", "2");
        postRequest.setContentType("application/json");
        CrawlerHttpResponse response = crawlerDownloader.download(postRequest);
        print(response.body());
    }
}
