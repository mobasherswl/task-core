package io.task.exception;

public class DbException extends BaseException
{

	private static final long	serialVersionUID	= 1L;


	public DbException(String message)
	{
		super(message);
	}


	public DbException(Throwable exception)
	{
		super(exception);
	}


	/**
	 * <pre>
	 * Default constructor
	 * 
	 * @author - Ahmed Mobasher Khan
	 * 
	 * @param message
	 * @param logLevel
	 * </pre>
	 */
	public DbException(String message, String logLevel)
	{
		super(message, logLevel);
	}


	/**
	 * <pre>
	 * Default constructor
	 * 
	 * @author - Ahmed Mobasher Khan
	 * 
	 * @param exception
	 * @param logLevel
	 * </pre>
	 */
	public DbException(Throwable exception, String logLevel)
	{
		super(exception, logLevel);
	}


	/**
	 * <pre>
	 * Default constructor
	 * 
	 * @author - Ahmed Mobasher Khan
	 * 
	 * @param message
	 * @param exception
	 * </pre>
	 */
	public DbException(String message, Throwable exception)
	{
		super(message, exception);

	}


	/**
	 * <pre>
	 * Default constructor
	 * 
	 * @author - Ahmed Mobasher Khan
	 * 
	 * @param exception
	 * @param message
	 * @param logLevel
	 * </pre>
	 */
	public DbException(Throwable exception, String message, String logLevel)
	{
		super(message, exception);
		setLogLevel(logLevel);
	}

}
