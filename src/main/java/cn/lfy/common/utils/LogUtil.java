package cn.lfy.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class LogUtil {
	
	public static final String LOG_SPLIT = ",";

	public static String getLogStr(Object... params) {
		return StringUtils.join(params, LOG_SPLIT);
	}

	public static String getLogStr(HttpServletRequest request) {
		String paramStr = JSONObject.toJSONString(request.getParameterMap());
		return getLogStr("[url]", request.getRequestURL().toString(), "[param]", paramStr);
	}
	
}
