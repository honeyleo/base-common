package cn.lfy.common.validator;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import cn.lfy.common.framework.exception.ApplicationException;

/**
 * 参数验证框架
 * @author liaopeng
 *
 */
public final class Validators extends AbstractValidator {

	/**
	 * 判断是否为true，如果bool为false则抛出异常
	 * @param bool
	 * @param errorCode
	 * @param args
	 */
	public static void isTrue( boolean bool, int errorCode, String... args ) {
		if( !bool ) {
			throw new ApplicationException( errorCode, null, args );
		}
	}
	/**
	 * 判断是否为true，如果bool为false则抛出异常
	 * @param bool
	 * @param errorMsg
	 * @param errorCode
	 * @param args
	 */
	public static void isTrue( boolean bool, String errorMsg, int errorCode, String... args) {
		if( !bool ) {
			throw new ApplicationException(errorCode, errorMsg, args);
		}
	}
	/**
	 * 判断是否为false，如果bool为true则抛异常
	 * @param bool
	 * @param errorCode
	 * @param args
	 */
	public static void isFalse( boolean bool, int errorCode, String... args  ) {
		if( bool ) {
			throw new ApplicationException(errorCode, null, args);
		}
	}
	/**
	 * 判断是否为false，如果bool为true则抛异常
	 * @param bool
	 * @param errorMsg
	 * @param errorCode
	 * @param args
	 */
	public static void isFalse( boolean bool, String errorMsg, int errorCode, String... args  ) {
		if( bool ) {
			throw new ApplicationException(errorCode, errorMsg, args);
		}
	}
	/**
	 * 判断是否为null，为null则抛异常
	 * @param value
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static <T> boolean notNull( T value, int errorCode, String... args ) {
		boolean isValid = notNull( value );
		if( !isValid ) {
			throw new ApplicationException( errorCode, null, args );
		}
		return isValid;
	}
	/**
	 * 判断是否为null，为null则抛异常
	 * @param value
	 * @param errorMsg
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static <T> boolean notNull( T value, String errorMsg, int errorCode, String... args ) {
		boolean isValid = notNull( value );
		if( !isValid ) {
			throw new ApplicationException( errorCode, errorMsg, args );
		}
		return isValid;
	}
	/**
	 * 判断数组是否为空，为空则抛异常
	 * @param value
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static <T> boolean notEmpty( T[] value, int errorCode, String... args ) {
		boolean isValid = notEmpty( value );
		if( !isValid ) {
			throw new ApplicationException( errorCode, null, args );
		}
		return isValid;
	}
	/**
	 * 判断数组是否为空，为空则抛异常
	 * @param value
	 * @param errorMsg
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static <T> boolean notEmpty( T[] value, String errorMsg, int errorCode, String... args ) {
		boolean isValid = notEmpty( value );
		if( !isValid ) {
			throw new ApplicationException( errorCode, errorMsg, args );
		}
		return isValid;
	}
	/**
	 * 判断集合是否为空，为空则抛异常
	 * @param value
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static <T extends Collection<?>> boolean notEmpty( T value, int errorCode, String... args ) {
		boolean isValid = notEmpty( value );
		if( !isValid ) {
			throw new ApplicationException( errorCode, null, args );
		}
		return isValid;
	}
	/**
	 * 判断集合是否为空，为空则抛异常
	 * @param value
	 * @param errorMsg
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static <T extends Collection<?>> boolean notEmpty( T value, String errorMsg, int errorCode, String... args ) {
		boolean isValid = notEmpty( value );
		if( !isValid ) {
			throw new ApplicationException( errorCode, errorMsg, args );
		}
		return isValid;
	}
	/**
	 * 判断Map是否为空，为空则抛异常
	 * @param value
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static <T extends Map<?, ?>> boolean notEmpty( T value, int errorCode, String... args ) {
		boolean isValid = notEmpty( value );
		if( !isValid ) {
			throw new ApplicationException( errorCode, null, args );
		}
		return isValid;
	}
	/**
	 * 判断Map是否为空，为空则抛异常
	 * @param value
	 * @param errorMsg
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static <T extends Map<?, ?>> boolean notEmpty( T value, String errorMsg, int errorCode, String... args ) {
		boolean isValid = notEmpty( value );
		if( !isValid ) {
			throw new ApplicationException( errorCode, errorMsg, args );
		}
		return isValid;
	}
	/**
	 * 判断字符串是否为空，为空则抛异常
	 * @param value
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static <T extends CharSequence> boolean notEmpty( T value, int errorCode, String... args ) {
		boolean isValid = notEmpty( value );
		if( !isValid ) {
			throw new ApplicationException( errorCode, null, args );
		}
		return isValid;
	}
	/**
	 * 判断字符串是否为空，为空则抛异常
	 * @param value
	 * @param errorMsg
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static <T extends CharSequence> boolean notEmpty( T value, String errorMsg, int errorCode, String... args ) {
		boolean isValid = notEmpty( value );
		if( !isValid ) {
			throw new ApplicationException( errorCode, errorMsg, args );
		}
		return isValid;
	}
	/**
	 * 判断指定字符串是否超过最大长度，超过最大长度则抛异常
	 * @param value
	 * @param maxLength
	 * @param errorCode
	 * @return
	 */
	public static boolean maxLength( String value, int maxLength, int errorCode ) {
		boolean isValid = maxLength( value, maxLength );
		if( !isValid ) {
			throw new ApplicationException( errorCode, null, new String[] { String.valueOf(maxLength) } );
		}
		return isValid;
	}
	/**
	 * 判断value字符串长度是否在最小和最大区间，如果不是，则抛异常
	 * @param value
	 * @param name
	 * @param minLength
	 * @param maxLength
	 * @param errorCode
	 * @return
	 */
	public static boolean range( String value, String name, int minLength, int maxLength, int errorCode ) {
		boolean isValid = range( value, minLength, maxLength );
		if( !isValid ) {
			throw new ApplicationException( errorCode, null, new String[] { name, String.valueOf(minLength), String.valueOf(maxLength) } );
		}
		return isValid;
	}
	/**
	 * 判断value是否在最小和最大区间，如果不是抛异常
	 * @param value
	 * @param name
	 * @param min
	 * @param max
	 * @param errorCode
	 * @return
	 */
	public static boolean range( Integer value, String name, int min, int max, int errorCode ) {
		boolean isValid = range( value, min, max );
		if( !isValid ) {
			throw new ApplicationException( errorCode, null, new String[] { name, String.valueOf(min), String.valueOf(max) } );
		}
		return isValid;
	}
	/**
	 * 判断value是否不等于空并是数字，如果不是则抛异常
	 * @param value
	 * @param errorCode
	 * @param args
	 */
	public static void notEmptyAndNumeric(String value, int errorCode, String... args ) {
		boolean isValid = notEmpty( value ) && StringUtils.isNumeric(value);
		if( !isValid ) {
			throw new ApplicationException( errorCode, null, args );
		}
	}
	static Pattern p = Pattern.compile("[A-Za-z0-9_]+");
	/**
	 * 用户名验证
	 * @param username
	 * @param errorCode
	 * @param args
	 * @return
	 */
	public static boolean usernameValidator(String username, int errorCode, String...args) {
		Matcher m = p.matcher(username);
		boolean isValid = m.matches();
		if( !isValid ) {
			throw new ApplicationException( errorCode, null, args );
		}
		return isValid;
	}
	/**
	 * 验证密码是否有效
	 * @param password
	 * @return
	 */
	public static boolean isValidPassword(String password) {
		int num = 0;
		num = Pattern.compile("\\d").matcher(password).find() ? num + 1 : num;
		num = Pattern.compile("[a-zA-Z]").matcher(password).find() ? num + 1 : num;
		num = Pattern.compile("[~!@#$%^&*()_+`\\-={}:\";\'<>?,.\\/]").matcher(password).find() ? num + 1 : num;
		return num >=2;
	}
	/**
	 * 验证手机号是否有效
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D])|(17[0-9]))\\d{8}").matcher(phone).matches();
	}
}
