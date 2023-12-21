package org.sunso.mini.crawler.demo.page.dslandprice.result;

import lombok.Data;
import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.annotation.json.JsonPath;
import org.sunso.mini.crawler.annotation.order.FieldOrder;
import org.sunso.mini.crawler.annotation.request.RequestAttributeGet;
import org.sunso.mini.crawler.annotation.request.RequestAttributeSet;
import org.sunso.mini.crawler.annotation.result.CrawlerResultDefine;
import org.sunso.mini.crawler.common.result.CrawlerResult;
import org.sunso.mini.crawler.downloader.HutoolCrawlerDownloader;
import org.sunso.mini.crawler.handler.ConsoleCrawlerHandler;

/**
 * @author sunso520
 * @Title:DsLandPriceCrawlerResult
 * @Description: 获取地区片价数据--同时获取该地区具体数据<br>
 * @Created on 2023/12/20 15:34
 */
@Data
@CrawlerResultDefine(handlers = { ConsoleCrawlerHandler.class })
public class DsLandPriceCrawlerResult implements CrawlerResult {

	@RequestAttributeGet
	private String provinceName;

	@FieldOrder(sort = 100)
	@RequestAttributeSet("bsm")
	@JsonPath("bsm")
	private String absm;

	@FieldOrder(sort = 1)
	@JsonPath("ssxzqhmc")
	private String provinceCityName; // 所属行政区- 北京市/昌平区, 黑龙江省/哈尔滨市/阿城区

	@FieldOrder(sort = 101)
	@JsonPath("ssxzqhdm")
	@RequestAttributeSet
	private String provinceCityId; // 所属行政区--编码 230112

	@FieldOrder(sort = 2)
	@JsonPath("bzgbsj")
	private String publishDate; // 发布时间

	@FieldOrder(sort = 3)
	@JsonPath("bzsssj")
	private String executionDate; // 执行时间

	@FieldOrder(sort = 102)
	@JsonPath("tdbcbl")
	private double tdbcbl; // 土地补偿比例

	@FieldOrder(sort = 103)
	@JsonPath("azbzbl")
	private double azbzbl;// 安置补助费比例

	@FieldOrder(sort = 4)
	@JsonPath("qpjb")
	private String level; // 区片等级

	@FieldOrder(sort = 5)
	@JsonPath("qpfw")
	private String location; // 区片位置

	@FieldOrder(sort = 104)
	@JsonPath("qpzhdj")
	private double price; // 区片综合地价

	@FieldOrder(sort = 105)
	@JsonPath("yjjbntbcbz")
	private double yjjbntbcbz;// 永久基本农田补偿标准

	@FieldOrder(sort = 106)
	@JsonPath("jsydbdcbz")
	private double jsydbdcbz;// 建设用地补偿标准

	@FieldOrder(sort = 107)
	@JsonPath("wlydbcbz")
	private double wlydbcbz;// 未利用地补偿标准

	@FieldOrder(sort = 108)
	@JsonPath("bz")
	private String remark; // 备注

	@FieldOrder(sort = 109)
	@JsonPath("fabsm")
	@RequestAttributeSet
	private String fileId; // 文件id

	// 通过ajax获取地区具体数据
	@FieldOrder(sort = Integer.MAX_VALUE - 2)
	@JsonPath("result")
	@HtmlAjax(url = "http://gsgk.mnr.gov.cn/wQpjApi/dict/getAreaByCode?code={provinceCityId}",
			downloader = HutoolCrawlerDownloader.class)
	private AreaCrawlerResult area;

}
