package io.task.exception;

public class MissingBeanException extends BeanException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6034944509820269402L;

	public MissingBeanException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MissingBeanException(String message, String logLevel) {
		super(message, logLevel);
		// TODO Auto-generated constructor stub
	}

	public MissingBeanException(Throwable exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	public MissingBeanException(Throwable exception, String logLevel) {
		super(exception, logLevel);
		// TODO Auto-generated constructor stub
	}

	public MissingBeanException(String message, Throwable exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

	public MissingBeanException(Throwable exception, String message,
			String logLevel) {
		super(exception, message, logLevel);
		// TODO Auto-generated constructor stub
	}

}
