package org.sunso.mini.crawler.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

    public static void test(String key, Object... params) {
        for(Object para: params) {
            System.out.println("" + para);
        }
    }

    public static Map<String, String> urlMatch(String matchUrl, String targetUrl) {
        return urlMatch(matchUrl, targetUrl, "{", "}", 3);
    }

    public static Map<String, String> urlMatch(String matchUrl, String targetUrl, String matchBeginFlag, String matchEndFlag, int nextMatchLength) {
        if (matchUrl == null || targetUrl == null) {
            return null;
        }
        if (targetUrl.indexOf(matchBeginFlag) > -1) {
            return null;
        }
        Map<String, String> parameterMap = new HashMap<>();
        //相关url完全相对，说明没有替换参数
        if (matchUrl.indexOf(matchBeginFlag) == -1 && matchUrl.equals(targetUrl)) {
            return parameterMap;
        }

        while (matchUrl.indexOf(matchBeginFlag) > -1 && matchUrl.indexOf(matchEndFlag) > -1) {
            int beginIndex = matchUrl.indexOf(matchBeginFlag);
            int endIndex = matchUrl.indexOf(matchEndFlag);
            if (!matchUrl.substring(0, beginIndex).equals(targetUrl.substring(0, beginIndex))) {
                return null;
            }
//            if (!targetUrl.substring(beginIndex, beginIndex + nextMatchLength).equals(matchUrl.substring(endIndex, 1))) {
//                return null;
//            }

            String key = matchUrl.substring(beginIndex + matchBeginFlag.length(), endIndex);

            // 已经到结尾了
            if (endIndex + nextMatchLength > matchUrl.length() -1) {
                targetUrl = targetUrl.substring(beginIndex);
                parameterMap.put(key, targetUrl);
                return parameterMap;
            }
            String nextMatchStr = matchUrl.substring(endIndex + matchEndFlag.length(), endIndex + matchEndFlag.length() + nextMatchLength);
            matchUrl = matchUrl.substring(endIndex+1);
            targetUrl = targetUrl.substring(beginIndex);
            int nextMatchIndex = targetUrl.indexOf(nextMatchStr);
            if (nextMatchIndex == -1) {
                return null;
            }
            String value = targetUrl.substring(0, nextMatchIndex);
            parameterMap.put(key, value);
            targetUrl = targetUrl.substring(nextMatchIndex);
        }

        if (!matchUrl.equals(targetUrl)) {
            return null;
        }
        return parameterMap;
    }

    public static String replaceParams(String srcUrl, Map<String, Object> params) {
        return replaceRegexs(srcUrl, "\\{(.*?)\\}", params);
    }

    public static String replaceRegexs(String srcUrl, String regex, Map<String, Object> params) {
        if(params == null) {
            return srcUrl;
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(srcUrl);
        while(matcher.find()) {
            String name = matcher.group(1);
            Object value = params.get(name);
            if(value !=null) {
                matcher.appendReplacement(sb, value.toString());
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String getNextPageUrl(String url, String pageKey, int currentPage) {
        pageKey = pageKey + "=";
        int pageIndex = url.indexOf(pageKey);
        String pageBefore = url.substring(0, pageIndex + pageKey.length());
        String pageAfter = url.substring(pageIndex + pageKey.length() + 1);
        int endIndex = pageAfter.indexOf("&");
        if (endIndex == -1) {
            return pageBefore + currentPage;
        }
        return pageBefore + currentPage + pageAfter.substring(endIndex);
    }
}
