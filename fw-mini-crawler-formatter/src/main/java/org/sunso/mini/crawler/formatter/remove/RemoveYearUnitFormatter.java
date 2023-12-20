package org.sunso.mini.crawler.formatter.remove;

/**
 * @author sunso520
 * @Title:RemoveYearUnitFormatter
 * @Description: 移除掉"年"关键字的格式化处理<br>
 * @Created on 2023/11/24 17:38
 */
public class RemoveYearUnitFormatter extends AbstractRemoveFormatter {

    public RemoveYearUnitFormatter() {
        this.removeKey = "年";
    }
}
