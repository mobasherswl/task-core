package io.task.exception;

public class BeanException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5156040007787236097L;

	public BeanException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BeanException(String message, String logLevel) {
		super(message, logLevel);
		// TODO Auto-generated constructor stub
	}

	public BeanException(Throwable exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	public BeanException(Throwable exception, String logLevel) {
		super(exception, logLevel);
		// TODO Auto-generated constructor stub
	}

	public BeanException(String message, Throwable exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

	public BeanException(Throwable exception, String message, String logLevel) {
		super(exception, message, logLevel);
		// TODO Auto-generated constructor stub
	}

}
