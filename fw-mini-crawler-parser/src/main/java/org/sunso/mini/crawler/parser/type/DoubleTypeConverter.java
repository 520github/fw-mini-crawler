package org.sunso.mini.crawler.parser.type;

public class DoubleTypeConverter extends AbstractTypeConverter<Double> {

	@Override
	protected Double doConvert(Object value, String format) {
		return Double.parseDouble(value.toString());
	}

}
