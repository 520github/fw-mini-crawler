# fw-mini-crawler

fw-mini-crawler是一整套java爬虫框架.

核心模块有爬虫引擎、爬虫任务、爬虫下载器、爬虫解析器、爬虫处理器、爬虫存储器、
爬虫校验器、数据过滤器、数据格式化等。

整个爬虫框架完全模块化设计，对于框架的每一个节点都可以进行自定义扩展，拥有超强的可扩展能力。

## 核心概念说明

| 概念    | 说明                            | 接口-basePackage=org.sunso.mini.crawler                           |
|-------|-------------------------------|-----------------------------------------------------------------|
| CrawlerEnginer | 爬虫引擎，提供不同爬虫需求的快速入口            | $basePackage.enginer.CrawlerEnginer                             |
| CrawlerTask | 爬虫任务,定义爬虫任务的存储方式              | $basePackage.task.CrawlerTask                         |
| CrawlerSpider | 爬虫执行器,定义爬虫任务的执行方式             | $basePackage.spider.CrawlerSpider                         |
| CrawlerDownloader      | 爬虫下载器，支持接口爬取和浏览器爬取            | $basePackage.downloader.CrawlerDownloader             |
| CrawlerParser | 爬虫解析器,支持html、json、xml等格式解析处理  | $basePackage.parser.CrawlerParser                     |
| CrawlerHandler | 爬虫处理器,定义爬取数据后的业务处理            | $basePackage.handler.CrawlerHandler                   |
| CrawlerFileStorage | 爬虫文件存储器，爬取文件自动存储本地或s3等        | $basePackage.storage.file.CrawlerFileStorage          |
| CrawlerDataStorage | 爬虫数据存储,处理后的爬取数据可以自动存储到数据库     | $basePackage.storage.data.CrawlerDataStorage          |
| CrawlerChecker | 爬虫校验器,对爬取的数据进行校验，以判断是否继续后面流程  | $basePackage.checker.CrawlerChecker                   |
| Formatter | 对爬虫数据的格式化                     | $basePackage.formatter.Formatter                      |
| CrawlerResult | 爬虫结果标记接口                      | $basePackage.common.result.CrawlerResult              |
| CrawlerHttpRequest | 爬虫url请求参数                     | $basePackage.common.http.request.CrawlerHttpRequest   |
| CrawlerHttpResponse | 爬虫url响应结果                     | $basePackage.common.http.response.CrawlerHttpResponse |
| CrawlerHttpRequestEvent | 爬虫url请求触发事件,如点击按钮、滚动屏幕等       | $basePackage.common.http.event.CrawlerHttpRequestEvent |
| CrawlerResultDefine | 爬虫url自定义参数的注解,可定义处理器、下载器、存储器等 | $basePackage.annotation.result.CrawlerResultDefine    |
| HtmlCssPath | 从html内容提取数据的注解                | $basePackage.annotation.html.HtmlCssPath              |
| JsonPath | 从json内容提取数据的注解                | $basePackage.annotation.json.JsonPath                 |
| HtmlPageUrl | 通过接口读取所有分页数据的注解               | $basePackage.annotation.html.HtmlPageUrl              |
| HtmlPageSingleButton | 通过页面点击分页按钮获取所有分页数据的注解         | $basePackage.annotation.html.HtmlPageSingleButton     |
| CrawlerContext | 爬虫上下文                         | $basePackage.context.CrawlerContext      |


## 功能特性
- 支持分布式网络爬虫。
- 基于java注解的实现方式。
- 同时支持HttpClient和浏览器方式爬取数据。
- 支持html、json、xml等爬取数据解析方式。
- 采用类css selector方式的字段注解选择器。
- 爬虫任务可存储于本地内存、数据库、redis等。
- 针对爬取文件可自动存储到本地、S3等。
- 针对爬取的数据可自动入库。
- 支持Ajax请求处理。
- 支持获取接口方式的分页数据。
- 支持获取页面点击分页按钮方式的分页数据。
- 支持针对爬取的数据进行数据格式化。
- 支持针对爬取的数据进行校验，以确定是否继续后面流程。
- 支持父子url之间的cookies、header、attribute等传递。
- 支持父子url之间爬取字段值的传递。

## 爬取字段定义解析方式

### json数据字段解析
~~~
    @JsonPath("result.records")
    private List<DsLandPriceCrawlerResult> list;
~~~

### html数据字段解析
- @HtmlText注解 (文本内容)
~~~
    @HtmlCssPath(value = "tr>td:eq(0)")
    @HtmlText
    private String code;
~~~
- @HtmlAttr注解 (属性类)
~~~
    @HtmlAttr("data-title")
    @HtmlCssPath("li")
    private String title;
~~~
- @HtmlHtml注解 (html代码)
~~~
    @HtmlHtml
    @HtmlCssPath("div#js_content > section:eq(2)")
    private String content;
~~~
- @HtmlImage注解 (图片)
~~~
    @HtmlImage
    @HtmlCssPath(".ModuleProduteDetailMain .inSwiper-slide .mobile-inSlide-size")
    private String img;
~~~
- @HtmlUrl注解 (链接)
~~~
    @HtmlCssPath(value = "tr>td:eq(0) a")
    @HtmlUrl(triggerClick = true, waitTime = 3000, urlAlias = "detail", copyAttribute = true)
    private String detailUrl;
~~~

### Ajax方式
- @HtmlAjax注解 (ajax请求)
~~~
    @JsonPath("result")
    @HtmlAjax(url = "http://gsgk.mnr.gov.cn/wQpjApi/qpj/qpjInfo?bsm={bsm}", downloader = HutoolCrawlerDownloader.class)
    private DsLandPriceDetailCrawlerResult detail;
~~~

### 分页方式
- @HtmlPageUrl注解 (url分页)
~~~
    @HtmlAjax(url = "http://gsgk.mnr.gov.cn/wQpjApi/qpj/qpjSearch?xzq={provinceCode}&wz=&pageNo=1&pageSize=10", downloader = HutoolCrawlerDownloader.class, requestAttributeName = "qpjSearchUrl", copyAttribute = true)
    @HtmlPageUrl(pageNoKey = "pageNo")
    @JsonPath("result.records")
    private List<DsLandPriceCrawlerResult> list;
~~~

- @HtmlPageSingleButton注解 (页面点击按钮分页)
~~~
    @HtmlCssPath("div#dataview_kzz .dataview-body table tbody tr")
    @HtmlHtml
    @HtmlPageSingleButton(url = "https://data.eastmoney.com/xg/xg/?mkt=kzz", waitTime = 4000, cssSelector = "input.btn", eventType = HttpRequestEventTypeEnum.inputSetAndClickButton, startPage = 12, eventDoMaxNum = 19, extendData = "{'inputCssPath':'#gotopageindex','clickWait':'3000'}")
    private List<ConvertibleBondsDataCrawlerResult> dataList;
~~~

## 框架内置强劲功能
框架内置了诸多强劲的功能，助力您用更少、更简单的代码完成对网站的爬取工作。

- 获取url链接并点击该链接，同时获取该链接数据
~~~
    @HtmlCssPath(value = "tr>td:eq(0) a")
    @HtmlUrl(triggerClick = true, waitTime = 3000, urlAlias = "detail", copyAttribute = true)
    @FieldOrder(sort = 100)
    private String detailUrl;
~~~

- 通过页面按钮点击获取分页数据
~~~
    @HtmlCssPath("div#dataview_kzz .dataview-body table tbody tr")
    @HtmlHtml
    @HtmlPageSingleButton(url = "https://data.eastmoney.com/xg/xg/?mkt=kzz", waitTime = 4000, cssSelector = "input.btn", eventType = HttpRequestEventTypeEnum.inputSetAndClickButton, startPage = 12, eventDoMaxNum = 19, extendData = "{'inputCssPath':'#gotopageindex','clickWait':'3000'}")
    private List<ConvertibleBondsDataCrawlerResult> dataList;
~~~

- 获取ajax分页数据
~~~
    @HtmlAjax(url = "http://gsgk.mnr.gov.cn/wQpjApi/qpj/qpjSearch?xzq={provinceCode}&wz=&pageNo=1&pageSize=10", downloader = HutoolCrawlerDownloader.class, requestAttributeName = "qpjSearchUrl", copyAttribute = true)
    @HtmlPageUrl(pageNoKey = "pageNo")
    @JsonPath("result.records")
    private List<DsLandPriceCrawlerResult> list;
~~~

- 字段格式化处理
~~~
  @HtmlCssPath(value = "tr>td:eq(11)", formatter = ReplaceHorizontalLine2ZeroFormatter.class)
  @HtmlText
  private double bondCurrentPrice;
~~~

- 爬取数据直接写入数据库
~~~
@CommonRelationDb(tableName = "convertible_bond", insertOrUpdate = true, checkExistColumns = {"bond_code"})
@CrawlerResultDefine(handlers = {ConvertibleBondsDetailCrawlerHandler.class}, downloader = SeleniumCrawlerDownloader.class, dataStorages = CommonRelationDbHuToolCrawlerDataStorage.class)
@Data
public class ConvertibleBondsDetailCrawlerResult implements CrawlerResult {

    @HtmlCssPath(".jbzl_table tbody tr:eq(0) td:eq(5)")
    private String bondCode;

    @HtmlCssPath(".jbzl_table tbody tr:eq(0) td:eq(3)")
    private String bondName;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(0) td:eq(7)", formatter = RemoveYearUnitFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private int bondTerm;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(1) td:eq(3)", formatter = RemoveMoneyUnitYuanFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private double issuePrice;

    @HtmlCssPath(".jbzl_table tbody tr:eq(1) td:eq(5)")
    private String interestCalculationMethod;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(1) td:eq(7)", formatter = RemovePercentageFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private double couponRate;

    @HtmlCssPath(".jbzl_table tbody tr:eq(3) td:eq(1)")
    @FieldDefine(defaultValue = "2000-01-01")
    private Date startDate;

    @HtmlCssPath(".jbzl_table tbody tr:eq(3) td:eq(3)")
    @FieldDefine(defaultValue = "2000-01-01")
    private Date dueDate;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(3) td:eq(7)", formatter = RemoveDayUnitFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private int remainingDay;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(4) td:eq(7)", formatter = ReplaceHorizontalLine2ZeroFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private double positiveStockPrice;

    @HtmlCssPath(".jbzl_table tbody tr:eq(5) td:eq(1)")
    @FieldDefine(defaultValue = "2000-01-01")
    private Date startConversionStockDate;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(5) td:eq(3)", formatter = ReplaceHorizontalLine2ZeroFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private double conversionStockPrice;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(5) td:eq(5)", formatter = ReplaceHorizontalLine2ZeroFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private double conversionStockValue;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(5) td:eq(7)", formatter = RemovePercentageFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private double conversionStockPremiumRate;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(6) td:eq(1)", formatter = ReplaceHorizontalLine2ZeroFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private double buyBackTriggerPrice;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(6) td:eq(3)", formatter = ReplaceHorizontalLine2ZeroFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private double forcedRedemptionTriggerPrice;

    @HtmlCssPath(value = ".jbzl_table tbody tr:eq(6) td:eq(5)", formatter = ReplaceHorizontalLine2ZeroFormatter.class)
    @FieldDefine(defaultValue = "-1")
    private double expireBackPrice;

    @RequestAttributeGet
    private String creditRating;

    @RequestAttributeGet
    @FieldDefine(defaultValue = "-1")
    private double bondCurrentPrice;


    private Date updateTime = new Date();
}
~~~

- 父子url之间爬取字段值的传递
~~~
    父url爬取字段，并设置传递
    @RequestAttributeSet
    @FieldDefine(defaultValue = "-1")
    @HtmlCssPath(value = "tr>td:eq(11)", formatter = ReplaceHorizontalLine2ZeroFormatter.class)
    private double bondCurrentPrice;
    
    子url读取父url传递的字段值
    @RequestAttributeGet
    @FieldDefine(defaultValue = "-1")
    private double bondCurrentPrice;
~~~


## demo
### 爬取baidu首页demo
- 定义CrawlerResult
~~~
package org.sunso.mini.crawler.demo.simple.baidu;

import lombok.Data;
import org.sunso.mini.crawler.annotation.html.HtmlAttr;
import org.sunso.mini.crawler.annotation.html.HtmlCssPath;
import org.sunso.mini.crawler.annotation.html.HtmlImage;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;

/**
 * 百度首页爬取结果字段定义
 */
@CrawlerResultDefine(handlers = { BaiduCrawlerResultHandler.class })
@Data
public class BaiduCrawlerResult implements CrawlerResult {

	@HtmlCssPath("div#lg>img")
	@HtmlImage
	private String baiduLog; // 百度logo

	@HtmlCssPath("input#su")
	@HtmlAttr("value")
	private String baiduSubmitValue; // 百度搜索按钮的名称

}
~~~

- 定义CrawlerHandler
~~~
package org.sunso.mini.crawler.demo.simple.baidu;

import org.sunso.mini.crawler.handler.CrawlerHandler;

/**
 * 针对BaiduCrawlerResult爬取结果的处理
 */
public class BaiduCrawlerResultHandler implements CrawlerHandler<BaiduCrawlerResult> {

	@Override
	public void handle(BaiduCrawlerResult crawlerResult) {
	    //简单打印爬取的结果
            System.out.println("crawlerResult: " + crawlerResult);
	}

}
~~~

- 启动爬虫
~~~
package org.sunso.mini.crawler.demo.simple.baidu;

import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequestBuilder;
import org.sunso.mini.crawler.enginer.builder.CrawlerEnginerBuilder;

public class BaiduCrawler {

	public static void main(String[] args) {
		String url = "https://www.baidu.com/";
		CrawlerEnginerBuilder.create()
		        .request(CrawlerHttpRequestBuilder.get(url)) // 请求对象
				.urlCrawlerResult(url, BaiduCrawlerResult.class) // url对应的爬取结果类
				.buildOfSingleCrawlerEnginer() // 采用SingleCrawlerEnginer引擎
				.startCrawler(); // 启动爬虫
		System.out.println("baidu  crawler finish!");
	}

}
~~~

