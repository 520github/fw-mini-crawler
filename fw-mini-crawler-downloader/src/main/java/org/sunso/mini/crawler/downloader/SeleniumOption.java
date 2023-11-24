package org.sunso.mini.crawler.downloader;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.sunso.mini.crawler.common.http.option.Option;
import org.sunso.mini.crawler.common.http.request.CrawlerHttpRequest;
import org.sunso.mini.crawler.downloader.enums.DownloaderExtendKeyEnum;

/**
 * @author sunso520
 * @Title:SeleniumOption
 * @Description: <br>
 * @Created on 2023/11/24 15:26
 */
public class SeleniumOption {
    public static ChromiumOptions getOption(CrawlerHttpRequest request) {
        String browserType = request.getAttributeString(DownloaderExtendKeyEnum.browserType.getKey());
        if (isChrome(browserType)) {
            return getChromeOption(request);
        } else if (isSafari(browserType)) {
            return null;
        }
        return getChromeOption(request);
    }

    public static ChromiumOptions getChromeOption(CrawlerHttpRequest request) {
        ChromeOptions options = new ChromeOptions();
        Option option = request.getOption();
        if (option == null) {
            return options;
        }
        if (option.isSwitchArgHeadless()) {
            options.addArguments("--headless");
        }
        if (option.isSwitchArgDisableGpu()) {
            options.addArguments("--disable-gpu");
        }
        if (option.isSwitchArgStartMaximized()) {
            options.addArguments("--start-maximized");
        }
        if (option.isSwitchArgDisableInfoBars()) {
            options.addArguments("--disable-infobars");
        }
        if (option.isSwitchArgIncognito()) {
            options.addArguments("--incognito");
        }
        if (option.isSwitchArgDisableJavascript()) {
            options.addArguments("--disable-javascript");
        }
        //options.addArguments("--no-sandbox");
        //options.addArguments("--disable-dev-shm-usage");
        // 设置ua
        //options.addArguments("--user-agent=Mzodkk  ddd");
        // 设置窗口大小
        //options.addArguments("--window-size=1366,768");
        //设置代理服务器
        //options.addArguments("--proxy-server=http://115.239.102.149:4214");
        return options;
    }

    public static boolean isChrome(String browserType) {
        if ("chrome".equalsIgnoreCase(browserType)) {
            return true;
        }
        return false;
    }

    public static boolean isSafari(String browserType) {
        if ("safari".equalsIgnoreCase(browserType)) {
            return true;
        }
        return false;
    }
}
