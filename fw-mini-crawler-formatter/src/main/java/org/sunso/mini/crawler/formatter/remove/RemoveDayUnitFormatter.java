package org.sunso.mini.crawler.formatter.remove;

/**
 * @author sunso520
 * @Title:RemoveDayUnitFormatter
 * @Description: 移除掉"天"关键字的格式化处理<br>
 * @Created on 2023/11/24 17:40
 */
public class RemoveDayUnitFormatter extends AbstractRemoveFormatter {
    public RemoveDayUnitFormatter() {
        this.removeKey = "天";
    }
}
