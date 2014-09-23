package io.task.exception;

/**
 * <pre>
 * Created By : Ahmed Mobasher Khan
 * 
 * Purpose : 
 * 
 * Updated By : 
 * Updated Date : 
 * Comments :
 * </pre>
 */
public class CacheException extends BaseException
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;


	/**
	 * <pre>
	 * Default constructor
	 * 
	 * @author - Ahmed Mobasher Khan
	 * 
	 * @param message
	 * </pre>
	 */
	public CacheException(String message)
	{
		super(message);
	}


	/**
	 * <pre>
	 * Default constructor
	 * 
	 * @author - Ahmed Mobasher Khan
	 * 
	 * @param exception
	 * </pre>
	 */
	public CacheException(Throwable exception)
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
	public CacheException(String message, String logLevel)
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
	public CacheException(Throwable exception, String logLevel)
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
	public CacheException(String message, Throwable exception)
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
	public CacheException(Throwable exception, String message, String logLevel)
	{
		super(message, exception);
		setLogLevel(logLevel);
	}

}
