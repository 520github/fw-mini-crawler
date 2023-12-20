package org.sunso.mini.crawler.formatter.remove;

/**
 * @author sunso520
 * @Title:RemovePercentageFormatter
 * @Description: 移除掉"%"关键字的格式化处理<br>
 * @Created on 2023/11/24 17:40
 */
public class RemovePercentageFormatter extends AbstractRemoveFormatter {
    public RemovePercentageFormatter() {
        this.removeKey = "%";
    }
}
