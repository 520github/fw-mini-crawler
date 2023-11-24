package org.sunso.mini.crawler.parser.field;

import cn.hutool.core.util.StrUtil;
import org.sunso.mini.crawler.annotation.custom.CustomUrl;
import org.sunso.mini.crawler.common.utils.UrlUtils;
import org.sunso.mini.crawler.context.CrawlerContextThreadLocal;

/**
 * @author sunso520
 * @Title:CrawlerCustomUrlFieldParser
 * @Description: <br>
 * @Created on 2023/11/23 16:20
 */
public class CrawlerCustomUrlFieldParser extends AbstractCrawlerFieldParser {

    @Override
    public Object parseField(CrawlerFieldParserRequest request) {
        CustomUrl customUrl = request.fetchCustomUrl();
        String url = customUrl.url();
        url = UrlUtils.replaceParams(url, request.fetchAllReplaceParams());
        if (StrUtil.isBlank(url) || !url.startsWith("http")) {
            return url;
        }
        if (customUrl.triggerClick()) {
            CrawlerContextThreadLocal.offerRequest(request.subRequest(customUrl, url));
        }
        return url;
    }


}
