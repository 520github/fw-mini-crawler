package org.sunso.mini.crawler.parser.field;

import org.sunso.mini.crawler.annotation.html.HtmlAjax;
import org.sunso.mini.crawler.annotation.html.HtmlPageUrl;
import org.sunso.mini.crawler.common.utils.UrlUtils;
import org.sunso.mini.crawler.parser.dto.HtmlAjaxDTO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunso520
 * @Title:CrawlerPageUrlFieldParser
 * @Description: <br>
 * @Created on 2023/10/27 14:51
 */
public class CrawlerPageUrlFieldParser extends AbstractCrawlerFieldParser {
    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        List<Object> dataList = new ArrayList<>();
        Field field = request.getField();
        HtmlPageUrl htmlPageUrl = getHtmlPageUrl(field);
        int currentPage = htmlPageUrl.startPageNo();
        while(currentPage <= htmlPageUrl.endPageNo()) {
            List<Object> pageList = getPageDataList(htmlPageUrl, currentPage, request);
            currentPage++;
            System.out.println("currentPage: " + currentPage);
            System.out.println("currentPage data: " + pageList);
            if (pageList == null || pageList.isEmpty()) {
                break;
            }
            dataList.addAll(pageList);
        }
        return dataList;
    }

    private List<Object> getPageDataList(HtmlPageUrl htmlPageUrl, int currentPage, CrawlerFieldParserRequest request) {
        Field field = request.getField();
        HtmlAjaxDTO ajaxDTO = getHtmlAjaxDTO(request.getField());
        String url = UrlUtils.getNextPageUrl(ajaxDTO.getUrl(), htmlPageUrl.pageNoKey(), currentPage);
        ajaxDTO.setUrl(url);
        request.setHtmlAjaxDTO(ajaxDTO);
        CrawlerFieldParser parser = getCrawlerFieldParser(field);
        if (parser == null) {
            return null;
        }
        Object result = parser.parseField(request);
        if (result == null) {
            return null;
        }
        if (result instanceof List) {
            return (List<Object>)result;
        }
        System.out.println("getPageDataList result["+result.getClass().getName()+"] is not list");
        return null;
    }

    private  CrawlerFieldParser getCrawlerFieldParser(Field field) {
        if (field.isAnnotationPresent(HtmlAjax.class)) {
            return new CrawlerAjaxFieldParser();
        }
        return null;
    }

    private HtmlAjaxDTO getHtmlAjaxDTO(Field field) {
        return HtmlAjaxDTO.newInstance(field.getAnnotation(HtmlAjax.class));
    }

    private HtmlPageUrl getHtmlPageUrl(Field field) {
        return field.getAnnotation(HtmlPageUrl.class);
    }
}
