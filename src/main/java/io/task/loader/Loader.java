package io.task.loader;

import io.task.exception.BaseException;

public interface Loader<T> {

	T load() throws BaseException;
}
