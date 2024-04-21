package org.sunso.mini.crawler.downloader;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpEmptyRequest;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.common.http.response.CrawlerHttpResponse;

import java.nio.charset.Charset;

/**
 * @author sunso520
 * @Title:AbstractCrawlerDownloader
 * @Description: <br>
 * @Created on 2024/4/19 09:32
 */
public abstract class AbstractCrawlerDownloader implements CrawlerDownloader {

	public CrawlerHttpResponse download(CrawlerHttpRequest request) {
		if (request instanceof CrawlerHttpEmptyRequest) {
			return null;
		}
		String url = request.getUrl();
		if (StrUtil.isEmpty(url)) {
			return null;
		}
		if (url.startsWith("file:")) {
			return getContentFromFile(request);
		}
		return doDownload(request);
	}

	protected CrawlerHttpResponse getContentFromFile(CrawlerHttpRequest request) {
		String result = FileUtil.readString(request.getFileUrl(), Charset.forName("utf-8"));
		return CrawlerHttpResponse.create(result);
	}

	protected abstract CrawlerHttpResponse doDownload(CrawlerHttpRequest request);

}
