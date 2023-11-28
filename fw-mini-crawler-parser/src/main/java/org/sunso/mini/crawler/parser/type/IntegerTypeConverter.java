package org.sunso.mini.crawler.parser.type;

import cn.hutool.core.util.StrUtil;

public class IntegerTypeConverter extends AbstractTypeConverter<Integer> {
    @Override
    protected Integer doConvert(Object value, String format) {
        return Integer.parseInt(value.toString());
    }
}
