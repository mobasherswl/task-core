package io.task.exception;

public class BeanCircularDependencyException extends BeanException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4613960639433631973L;

	public BeanCircularDependencyException(String message) {
		super(message);
	}

	public BeanCircularDependencyException(String message, Throwable exception) {
		super(message, exception);
	}

}
