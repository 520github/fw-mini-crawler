package org.sunso.mini.crawler.parser.type;

public class FloatTypeConverter extends AbstractTypeConverter<Float> {

	@Override
	protected Float doConvert(Object value, String format) {
		return Float.parseFloat(value.toString());
	}

}
