package cn.lfy.common.validator;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractValidator {

	public static <T> boolean notNull( T value ) {
		return value != null;
	}

	public static <T> boolean notEmpty( T[] value ) {
		return ArrayUtils.isNotEmpty( value );
	}

	public static <T extends Collection<?>> boolean notEmpty( T value ) {
		return !( value == null || value.isEmpty() );
	}

	public static <T extends Map<?, ?>> boolean notEmpty( T value ) {
		return !( value == null || value.isEmpty() );
	}

	public static <T extends CharSequence> boolean notEmpty( T value ) {
		return StringUtils.isNotEmpty( value );
	}

	public static boolean maxLength( String value, int maxLength ) {
		int len = StringUtils.length( value );
		return len <= maxLength;
	}

	public static boolean range( String value, int minLength, int maxLength ) {
		int len = StringUtils.length( value );
		return len >= minLength && len <= maxLength;
	}

	public static boolean range( Integer value, int min, int max ) {
		if( value == null ) {
			return true;
		}
		return value >= min && value <= max;
	}
}
