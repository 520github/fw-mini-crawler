package org.sunso.mini.crawler.parser.type;

import java.math.BigDecimal;

public class BigDecimalTypeConverter extends AbstractTypeConverter<BigDecimal> {

	@Override
	protected BigDecimal doConvert(Object value, String format) {
		return new BigDecimal(value.toString());
	}

}
