package org.sunso.mini.crawler.common.http.option;

/**
 * @author sunso520
 * @Title:OptionFactory
 * @Description: <br>
 * @Created on 2023/11/24 15:19
 */
public class OptionFactory {

    public static Option getDefault() {
        return new Option();
    }

    public static Option getDefaultSwitchArgTrue() {
        Option option = new Option();
        option.setSwitchArgHeadless(true);
        option.setSwitchArgStartMaximized(true);
        option.setSwitchArgDisableInfoBars(true);
        return option;
    }
}
