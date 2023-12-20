package org.sunso.mini.crawler.formatter.money;

import org.sunso.mini.crawler.formatter.AbstractFormatter;

import java.math.BigDecimal;

/**
 * @author sunso520
 * @Title:Money2ChineseFormatter
 * @Description: 金额转中文格式化类<br>
 * @Created on 2023/10/16 15:26
 */
public class Money2ChineseFormatter extends AbstractFormatter {

	private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾",
			"佰", "仟" };

	private static final String CN_FULL = "整";

	private static final String CN_NEGATIVE = "负";

	private static final int MONEY_PRECISION = 2;

	private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

	private String toChinese(BigDecimal amount) {
		StringBuilder result = new StringBuilder();
		amount = amount.setScale(MONEY_PRECISION, BigDecimal.ROUND_HALF_UP);
		long number = amount.movePointRight(MONEY_PRECISION).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
		boolean zero = true;
		int unitIndex = 0;
		if (number == 0) {
			return CN_ZEOR_FULL;
		}
		if (number < 0) {
			number = -number;
			result.append(CN_NEGATIVE);
		}
		long scale = 10;
		while (true) {
			if (number == 0) {
				break;
			}
			long numIndex = number % scale;
			if (zero && numIndex == 0) {
				zero = false;
			}
			if (numIndex != 0) {
				result.insert(0, CN_UPPER_MONETRAY_UNIT[unitIndex]).insert(0, CN_UPPER_NUMBER[(int) numIndex]);
				zero = false;
			}
			else if (!zero) {
				result.insert(0, CN_UPPER_NUMBER[(int) numIndex]);
			}
			number = number / scale;
			unitIndex++;
		}
		if (zero) {
			result.append(CN_FULL);
		}
		return result.toString();
	}

	@Override
	protected Object doFormat(Object value) {
		if (value instanceof BigDecimal) {
			return toChinese((BigDecimal) value);
		}
		return toChinese(new BigDecimal(value.toString()));
	}

}
