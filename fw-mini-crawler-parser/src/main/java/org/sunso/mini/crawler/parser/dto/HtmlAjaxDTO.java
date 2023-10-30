package org.sunso.mini.crawler.parser.dto;

import lombok.Data;
import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.common.enums.ContentTypeEnum;
import org.sunso.mini.crawler.common.enums.HttpRequestMethodEnum;
import org.sunso.mini.crawler.downloader.CrawlerDownloader;

/**
 * @author sunso520
 * @Title:HtmlAjaxDTO
 * @Description: <br>
 * @Created on 2023/10/27 15:09
 */
@Data
public class HtmlAjaxDTO {
    private String url;

    private HttpRequestMethodEnum method;

    private Class<? extends CrawlerDownloader> downloader;

    private ContentTypeEnum contentType;

    private String requestAttributeName;


    public static HtmlAjaxDTO newInstance(HtmlAjax htmlAjax) {
        HtmlAjaxDTO dto = new HtmlAjaxDTO();
        dto.setUrl(htmlAjax.url());
        dto.setMethod(htmlAjax.method());
        dto.setContentType(htmlAjax.contentType());
        dto.setDownloader(htmlAjax.downloader());
        dto.setRequestAttributeName(htmlAjax.requestAttributeName());
        return dto;
    }
}