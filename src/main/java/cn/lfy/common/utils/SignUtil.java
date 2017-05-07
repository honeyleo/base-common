package cn.lfy.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SignUtil {

	private SignUtil() {
	}

	public static final String concatMap(Map<String, String> params) {
		List<String> keysSet = new ArrayList<String>();
		keysSet.addAll(params.keySet());
		Collections.sort(keysSet);
		StringBuilder sb = new StringBuilder();
		for (String key : keysSet) {
			if (key.equals("sign") || key.equals("sign_type")) {
				continue;
			}
			Object value = params.get(key);
			sb.append("&").append(key).append("=").append(value.toString());
		}

		if (sb.length() > 0 && sb.toString().startsWith("&")) {
			sb.delete(0, 1);
		}
		return sb.toString();
	}
	
	public static final String concatAll(Map<String, String> params) {
		if(params == null || params.isEmpty()) {
			return "";
		}
		List<String> keysSet = new ArrayList<String>();
		keysSet.addAll(params.keySet());
		Collections.sort(keysSet);
		StringBuilder sb = new StringBuilder();
		for (String key : keysSet) {
			String value = params.get(key);
			if(value == null) {
				continue;
			}
			sb.append("&").append(key).append("=").append(value);
		}

		if (sb.length() > 0 && sb.toString().startsWith("&")) {
			sb.delete(0, 1);
		}
		return sb.toString();
	}

	public static final String getSignCode(Map map, String appSecret) {
		String queryString = concatMap(map);
		String signText = queryString + ":" + appSecret;
		String serverSignCode = MessageDigestUtil.getMD5(signText);
		return serverSignCode;
	}

	public static final String getSignCode(String queryString, String appSecret) {
		String signText = queryString + ":" + appSecret;
		String serverSignCode = MessageDigestUtil.getMD5(signText);
		return serverSignCode;
	}

	public static final String getQueryString(Map map) {
		return concatMap(map);
	}
	
	public static void main(String[] args) {
//		System.out.println(MD5.sign("app_id=1905829&buy_amount=1&cp_order_id=1884170002&create_time=1441162412232&pay_type=0&product_body=1884170002&product_id=钻石&product_per_price=0&product_subject=钻石&product_unit=&total_price=3000&uid=23635362&user_info=1884170002:BP5WrBHxxRAk0lxp05N6OGGxWkdgmaZO"));
		
		String text = "app_id=1816347&buy_amount=1&cp_order_id=1&create_time=1&pay_type=0&product_body=Gem&product_id=caromgem1b_china&product_per_price=1&product_subject=Gem&product_unit=Gem&total_price=1&uid=116652769&user_info=56e7daa5c4570d2408004850|caromgem1b_china|0";
		
		String[] texts = text.split("&");
		
		Map<String, String> map = new HashMap<String, String>();
		
		for(String t : texts) {
			String[] kv = t.split("=");
			if(kv.length == 1) {
				map.put(kv[0], "");
			} else {
				map.put(kv[0], kv[1]);
			}
		}
		System.out.println(SignUtil.concatMap(map));
		System.out.println(SignUtil.getSignCode(map, "QWdw5YcXtkYMcFOLpqI3NFIAx9JXdSHh"));
		
		System.out.println(MessageDigestUtil.getMD5("app_id=&2860234buy_amount=1&cp_order_id=2016010520540000914&create_time=130964720712822990&pay_type=0&product_body=60钻石&product_id=1&product_per_price=1.0&product_subject=购买1份60钻石&product_unit=份&total_price=1.0&uid=112012275&user_info=:vK1Xt9wa0DkcBQFlHmlLocHo5c3GJQoA"));
	}
}
