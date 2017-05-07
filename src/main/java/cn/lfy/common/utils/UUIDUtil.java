package cn.lfy.common.utils;

import java.util.UUID;

public class UUIDUtil {

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String salt() {
		return UUID.randomUUID().toString().substring(30);
	} 
	
}
