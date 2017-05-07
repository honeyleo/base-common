package cn.lfy.common.utils;

/**
 * 字符串相关的函数
 * @author lyon.liao
 *
 */
public class Strings {

	/**
	 * 慢慢的比较
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean slowEquals(String a, String b) {
	    return slowEquals(a.getBytes(), b.getBytes());
	}
	/**
	 * 慢慢的比较
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean slowEquals(byte[] a, byte[] b) {
	    int diff = a.length ^ b.length;
	    for(int i = 0; i < a.length && i < b.length; i++) {
	    	diff |= a[i] ^ b[i];
	    }
	    return diff == 0;
	}
	
	public static void main(String[] args) {
		System.out.println(slowEquals("liaopeng", "liaopeng324234"));
	}
}
