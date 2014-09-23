package io.task.exception;

public class SpringException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7132509870984433716L;

	public SpringException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SpringException(String message, String logLevel) {
		super(message, logLevel);
		// TODO Auto-generated constructor stub
	}

	public SpringException(Throwable exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	public SpringException(Throwable exception, String logLevel) {
		super(exception, logLevel);
		// TODO Auto-generated constructor stub
	}

	public SpringException(String message, Throwable exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

	public SpringException(Throwable exception, String message, String logLevel) {
		super(exception, message, logLevel);
		// TODO Auto-generated constructor stub
	}

}
