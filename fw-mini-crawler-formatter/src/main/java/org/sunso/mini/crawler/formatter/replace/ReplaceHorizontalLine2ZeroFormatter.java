package org.sunso.mini.crawler.formatter.replace;

/**
 * @author sunso520
 * @Title:ReplaceHorizontalLine2ZeroFormmater
 * @Description: 把"-"替换成"0"的格式化处理<br>
 * @Created on 2023/11/27 15:50
 */
public class ReplaceHorizontalLine2ZeroFormatter extends AbstractReplaceFormatter {
    public ReplaceHorizontalLine2ZeroFormatter() {
        this.replaceKey = "-";
        this.replaceValue = "0";
    }
}
