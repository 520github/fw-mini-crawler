package org.sunso.mini.crawler.parser.html;

import java.util.List;

public interface HtmlCssPathParser {
    String parse(String cssPath);

    List<String> parseList(String cssPath);


}
