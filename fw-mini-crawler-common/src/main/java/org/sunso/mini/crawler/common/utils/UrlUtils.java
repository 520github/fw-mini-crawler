package org.sunso.mini.crawler.common.utils;

import java.util.HashMap;
import java.util.Map;

public class UrlUtils {

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
            targetUrl = targetUrl.substring(beginIndex+1);
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
}
