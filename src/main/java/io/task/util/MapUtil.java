package io.task.util;

import java.util.Map;

public class MapUtil {
	
	public static <T extends Map<K, V>, K, V> void clear(T t)
	{
		if(t != null)
		{
			t.clear();
		}
	}

	public static int getOptimumMapSize(int itemCount)
	{
		return (int) (itemCount/0.75 + 1);
	}
}
