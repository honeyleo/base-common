package cn.lfy.common.framework.exception;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ErrorKey
{
	private ErrorKey()
	{
	}

	private static Map map = new HashMap();

	static
	{
		Object o = new Error();
		java.lang.reflect.Field[] fs = o.getClass().getFields();
		try
		{
			for (int i = 0; i < fs.length; i++)
			{
				String fieldName = fs[i].getName().replaceAll("_", ".").toLowerCase();
				if (fs[i].getType() == int.class)
				{
					map.put(new Integer(fs[i].getInt(o)), fieldName);
				}
			}
		}
		catch (Exception e)
		{
		}
	}

	public static String getKey(int errorCode, int unknowCode)
	{
		int errorCodeResult = errorCode;
		Object o = map.get(new Integer(errorCodeResult));
		if (o == null)
		{
			errorCodeResult = unknowCode;
			return getKey(unknowCode);
		}
		return (String) o;
	}

	public static String getKey(int errorCode)
	{
		return getKey(new Integer(errorCode), ErrorCode.ERROR);
	}
}
