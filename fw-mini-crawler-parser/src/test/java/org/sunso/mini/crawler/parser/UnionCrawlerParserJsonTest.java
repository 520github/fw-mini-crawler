package org.sunso.mini.crawler.parser;

import lombok.Data;
import org.junit.Test;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.request.ResponseBody;
import org.sunso.mini.crawler.annotation.request.ResponseStatus;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpEmptyRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import java.util.List;

public class UnionCrawlerParserJsonTest extends BaseTest {

    private UnionCrawlerParser unionCrawlerParser = new UnionCrawlerParser();

    @Test
    public void parseJsonTest() {
        CrawlerResult crawlerResult  = unionCrawlerParser.parse(JsonDataResult.class, getRequest(), getResponse());
        print(crawlerResult);
        print(crawlerResult);
    }

    private CrawlerHttpRequest getRequest() {
        CrawlerHttpEmptyRequest request = new CrawlerHttpEmptyRequest();
        request.setUrl("http://www.dd.com");
        return request;
    }

    private CrawlerHttpResponse getResponse() {
        return CrawlerHttpResponse.create(jsonData());
    }

    @Data
    class JsonDataResult implements CrawlerResult {
        @ResponseBody
        private String body;
        @ResponseStatus
        private Integer status;

        @JsonPath("code")
        private String code;
        @JsonPath("msg")
        private String msg;
        @JsonPath("data.total")
        private Integer total;
        @JsonPath("data.list")
        private List<JsonDataListResult> resultList;
        @JsonPath("data.list")
        private JsonDataListResult[] resultArray;
        @JsonPath("data.list[3]")
        private JsonDataListResult detail;
    }

    @Data
    class JsonDataListResult implements CrawlerResult {
        @JsonPath("id")
        private Integer id;
        @JsonPath("title")
        private String title;
        @JsonPath("coverImg")
        private String imgUrl;
        @JsonPath("viewNum")
        private Integer count;

    }

    private String jsonData() {
        return "{\n" +
                "  \"code\": \"1\",\n" +
                "  \"msg\": \"请求成功\",\n" +
                "  \"data\": {\n" +
                "    \"total\": 589,\n" +
                "    \"list\": [\n" +
                "      {\n" +
                "        \"id\": 67,\n" +
                "        \"title\": \"孙维律师在央视频普法：安装好门窗却拿不到工钱，能把门窗拆走吗？\",\n" +
                "        \"summary\": \"生活常见法律问题解答\\n\",\n" +
                "        \"coverImg\": \"https://test-files.lddstp.com//pc/doc/2023/09/25/c6bb5216036343e8b0359f4b2a1c5483.jpeg\",\n" +
                "        \"viewNum\": 6,\n" +
                "        \"createDate\": \"2023-09-25\",\n" +
                "        \"preContent\": \"<section id=\\\"readMore\\\"><article class=\\\"news-detail\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 48,\n" +
                "        \"title\": \"公益｜中征律所参加“520爱满星球”孤独症儿童公益活动\",\n" +
                "        \"summary\": \"与爱同行 点亮星星\",\n" +
                "        \"coverImg\": \"https://test-files.lddstp.com//pc/doc/2023/09/25/d4931ddaf7c24e039e7a631707a6de74.jpeg\",\n" +
                "        \"viewNum\": 0,\n" +
                "        \"createDate\": \"2023-09-25\",\n" +
                "        \"preContent\": \"<section id=\\\"readMore\\\"><article class=\\\"news-detail\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 72,\n" +
                "        \"title\": \"123\",\n" +
                "        \"summary\": \"123123\",\n" +
                "        \"coverImg\": \"https://test-files.lddstp.com//pc/doc/2023/09/27/1f02325500f046e695312bf5b1bcdb2b.jpeg\",\n" +
                "        \"viewNum\": 0,\n" +
                "        \"createDate\": \"2023-09-27\",\n" +
                "        \"preContent\": \"<p>2023年7月13日下午，天津市委常委、统战部部长冀国强，市委统战部副部长刘志强，市委统战部副\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 71,\n" +
                "        \"title\": \"团建｜北京中征律师事务所2023年广西桂林充电之旅圆满结束\",\n" +
                "        \"summary\": \"感念过往 共期未来\",\n" +
                "        \"coverImg\": \"https://test-files.lddstp.com//pc/doc/2023/09/27/5fc0dfe5f5e2402d8ebed7437d358e6d.jpg\",\n" +
                "        \"viewNum\": 11,\n" +
                "        \"createDate\": \"2023-09-27\",\n" +
                "        \"preContent\": \"<section id=\\\"readMore\\\"><article class=\\\"news-detail\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 68,\n" +
                "        \"title\": \"hb卡升级宽带连接测试上传图\",\n" +
                "        \"summary\": \"撒打算大撒打算啊啥的啊啥的啊啥的撒的\",\n" +
                "        \"coverImg\": \"https://test-files.lddstp.com//pc/doc/2023/09/25/209a6e759eb14cc1a9ccca3586d928f1.png\",\n" +
                "        \"viewNum\": 2,\n" +
                "        \"createDate\": \"2023-09-25\",\n" +
                "        \"preContent\": \"<p>开机了就拉开手机丢了阿莱克斯大家啊就是打开</p><p style=\\\"text-align: \"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 66,\n" +
                "        \"title\": \"聚焦｜中征律所苗云海主任参加北京电视台民法典颁布三周年特别节目\",\n" +
                "        \"summary\": \"与法相伴，“典”亮生活\\n\",\n" +
                "        \"coverImg\": \"https://test-files.lddstp.com//pc/doc/2023/09/25/691573b17f4444efa5c11d76f43e35c0.jpeg\",\n" +
                "        \"viewNum\": 0,\n" +
                "        \"createDate\": \"2023-09-25\",\n" +
                "        \"preContent\": \"<section id=\\\"readMore\\\"><article class=\\\"news-detail\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 65,\n" +
                "        \"title\": \"中征律所高瑜律师在北京青年报刊发“房屋装修改造”专题普法文章\",\n" +
                "        \"summary\": \"热点新闻事件，专业法律解析\\n\",\n" +
                "        \"coverImg\": \"https://test-files.lddstp.com//pc/doc/2023/09/25/08eb0449480f44c3afe7d348e80e56c3.jpeg\",\n" +
                "        \"viewNum\": 0,\n" +
                "        \"createDate\": \"2023-09-25\",\n" +
                "        \"preContent\": \"<section id=\\\"readMore\\\"><article class=\\\"news-detail\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 64,\n" +
                "        \"title\": \"孙维律师在“央视频”普法：室友散布谣言影响正常生活，该如何维权？\",\n" +
                "        \"summary\": \"擅自散播谣言，可能违反这些法规！\\n\",\n" +
                "        \"coverImg\": \"https://test-files.lddstp.com//pc/doc/2023/09/25/22e6a7d41b8a47b48622f965672a229d.jpg\",\n" +
                "        \"viewNum\": 0,\n" +
                "        \"createDate\": \"2023-09-25\",\n" +
                "        \"preContent\": \"<section id=\\\"readMore\\\"><article class=\\\"news-detail\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 63,\n" +
                "        \"title\": \"孙维律师在“央视频”普法：预定“剧本杀”临时取消，能要求商家退定金吗？\",\n" +
                "        \"summary\": \"生活常见法律问题解答\",\n" +
                "        \"coverImg\": \"https://test-files.lddstp.com//pc/doc/2023/09/25/f661ce45a2e64f5bbbedc0adbd177fbb.jpg\",\n" +
                "        \"viewNum\": 0,\n" +
                "        \"createDate\": \"2023-09-25\",\n" +
                "        \"preContent\": \"<section id=\\\"readMore\\\"><article class=\\\"news-detail\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 61,\n" +
                "        \"title\": \"《法治日报》采访中征孙维律师：暑期“贩卖教育焦虑”的广告应如何规范管理？\",\n" +
                "        \"summary\": \"社会热点话题，专业法律解读\\n\",\n" +
                "        \"coverImg\": \"https://test-files.lddstp.com//pc/doc/2023/09/25/5f888ddabb324a67bff9bc3de6f1179a.jpg\",\n" +
                "        \"viewNum\": 0,\n" +
                "        \"createDate\": \"2023-09-25\",\n" +
                "        \"preContent\": \"<section id=\\\"readMore\\\"><article class=\\\"news-detail\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"pageNum\": 1,\n" +
                "    \"pageSize\": 10,\n" +
                "    \"size\": 10,\n" +
                "    \"startRow\": 1,\n" +
                "    \"endRow\": 10,\n" +
                "    \"pages\": 59,\n" +
                "    \"prePage\": 0,\n" +
                "    \"nextPage\": 2,\n" +
                "    \"isFirstPage\": true,\n" +
                "    \"isLastPage\": false,\n" +
                "    \"hasPreviousPage\": false,\n" +
                "    \"hasNextPage\": true,\n" +
                "    \"navigatePages\": 8,\n" +
                "    \"navigatepageNums\": [\n" +
                "      1,\n" +
                "      2,\n" +
                "      3,\n" +
                "      4,\n" +
                "      5,\n" +
                "      6,\n" +
                "      7,\n" +
                "      8\n" +
                "    ],\n" +
                "    \"navigateFirstPage\": 1,\n" +
                "    \"navigateLastPage\": 8\n" +
                "  }\n" +
                "}";
    }
}
