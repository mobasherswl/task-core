package io.task.loader;

import io.task.exception.BaseException;

public interface BeanLoader<T> {

	T load() throws BaseException;
}
