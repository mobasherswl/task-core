package io.task.exception;

public class BeanSetterDelayedException extends BeanException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1924905743319291457L;

	public BeanSetterDelayedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BeanSetterDelayedException(String message, String logLevel) {
		super(message, logLevel);
		// TODO Auto-generated constructor stub
	}

	public BeanSetterDelayedException(Throwable exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	public BeanSetterDelayedException(Throwable exception, String logLevel) {
		super(exception, logLevel);
		// TODO Auto-generated constructor stub
	}

	public BeanSetterDelayedException(String message, Throwable exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

	public BeanSetterDelayedException(Throwable exception, String message,
			String logLevel) {
		super(exception, message, logLevel);
		// TODO Auto-generated constructor stub
	}

}
