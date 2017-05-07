package cn.lfy.common.utils;

import java.text.DecimalFormat;

public class NumberUtil {

	public static String get2Double(Double value) {
		DecimalFormat fmt=new DecimalFormat("0.##");
		String v = fmt.format(value);
		return v;
	}
}
