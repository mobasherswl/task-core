package io.task.util;

/**
 * 
 * @author Mobasher
 *
 */
public class StringUtil
{
	public static String setNullToEmptyTrimmed(String str)
	{
		return (str == null) ? "" : str.trim();
	}


	public static String setNullToEmpty(String str)
	{
		return (str == null) ? "" : str;
	}


	public static boolean isNullOrEmptyTrimmed(String str)
	{
		return str == null || str.trim().length() == 0;
	}


	public static boolean isNullOrEmpty(String str)
	{
		return str == null || str.length() == 0;
	}


	public static String mergeStrings(Object... strings)
	{
		StringBuilder sb = new StringBuilder(500);
		for (Object str : strings) {
			sb.append(str.toString());
		}
		return sb.toString();
	}


	public static <T> String getNullOrString(T t)
	{
		return t == null ? null : t.toString();
	}
}
