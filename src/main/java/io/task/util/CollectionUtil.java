package io.task.util;

import java.util.Collection;

public class CollectionUtil {

	public static <T extends Collection<E>, E> boolean isNullOrEmpty(T collection)
	{
		return collection == null || collection.isEmpty();
	}
}
