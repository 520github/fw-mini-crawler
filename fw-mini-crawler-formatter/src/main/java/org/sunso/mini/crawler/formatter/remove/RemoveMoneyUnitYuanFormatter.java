package org.sunso.mini.crawler.formatter.remove;

/**
 * @author sunso520
 * @Title:RemoveMoneyUnitYuanFormatter
 * @Description: 移除掉"元"关键字的格式化处理<br>
 * @Created on 2023/11/24 17:40
 */
public class RemoveMoneyUnitYuanFormatter extends AbstractRemoveFormatter {
    public RemoveMoneyUnitYuanFormatter() {
        this.removeKey = "元";
    }
}
