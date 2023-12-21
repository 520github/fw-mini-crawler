package org.sunso.mini.crawler.demo.page.dslandprice.result;

import lombok.Data;
import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.annotation.html.HtmlPageUrl;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.request.RequestAttributeSet;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.demo.page.dslandprice.handler.ProvinceCrawlerResultHandler;
import org.sunso.mini.crawler.downloader.HutoolCrawlerDownloader;

import java.util.List;

/**
 * @author sunso520
 * @Title:ProvinceCrawlerResult
 * @Description: 省份数据-同时获取省下所有地区片价数据<br>
 * @Created on 2023/12/20 15:25
 */
@Data
@CrawlerResultDefine(handlers = { ProvinceCrawlerResultHandler.class })
public class ProvinceCrawlerResult implements CrawlerResult {

	@JsonPath("parentid")
	private String parentCode;

	@JsonPath("parentname")
	private String parentName;

	@RequestAttributeSet
	@JsonPath("value")
	private String provinceCode; // 省份code

	@RequestAttributeSet
	@JsonPath("name")
	private String provinceName; // 省份名称

	@JsonPath("type")
	private String type; // 行政区划

	// 获取ajax指定分页的数据
	@HtmlAjax(url = "http://gsgk.mnr.gov.cn/wQpjApi/qpj/qpjSearch?xzq={provinceCode}&wz=&pageNo=1&pageSize=10",
			downloader = HutoolCrawlerDownloader.class, requestAttributeName = "qpjSearchUrl", copyAttribute = true)
	@HtmlPageUrl(pageNoKey = "pageNo", startPageNo = 1, endPageNo = 2)
	@JsonPath("result.records")
	private List<DsLandPriceCrawlerResult> list;

}
