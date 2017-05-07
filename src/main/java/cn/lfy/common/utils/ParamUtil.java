package cn.lfy.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ParamUtil {

	public static final String concatAll(Map<String, String[]> params) {
		if(params == null || params.isEmpty()) {
			return "";
		}
		List<String> keysSet = new ArrayList<String>();
		keysSet.addAll(params.keySet());
		Collections.sort(keysSet);
		StringBuilder sb = new StringBuilder();
		for (String key : keysSet) {
			String[] value = params.get(key);
			if(value == null) {
				continue;
			}
			for(String val : value) {
				sb.append("&").append(key).append("=").append(val);
			}
		}

		if (sb.length() > 0 && sb.toString().startsWith("&")) {
			sb.delete(0, 1);
		}
		return sb.toString();
	}
}
