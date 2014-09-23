package io.task.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
public class BaseException extends RuntimeException
{
	private static final Logger	log					= LoggerFactory.getLogger(BaseException.class);
	private static final long		serialVersionUID	= 2860786859911836882L;
	private boolean					isLogged				= false;
	private String						logLevel				= "ERROR";
	private static String			nextLine				= "\r\n";

	static {
		try {
			nextLine = System.getProperties().getProperty("line.separator");
		} catch (Throwable e) {
			log.error("{}", e.fillInStackTrace());
		}
	}


	/**
	 * <pre>
	 * Default constructor
	 * 
	 * @author - Ahmed Mobasher Khan
	 * 
	 * @param message
	 * </pre>
	 */
	public BaseException(String message)
	{
		super(message);
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
	public BaseException(String message, String logLevel)
	{
		super(message);
		setLogLevel(logLevel);
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
	public BaseException(Throwable exception)
	{
		super(exception);
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
	public BaseException(Throwable exception, String logLevel)
	{
		super(exception);
		setLogLevel(logLevel);
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
	public BaseException(String message, Throwable exception)
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
	public BaseException(Throwable exception, String message, String logLevel)
	{
		super(message, exception);
		setLogLevel(logLevel);
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * @return - 
	 * </pre>
	 */
	public boolean isLogged()
	{
		return isLogged;
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * @param isLogged - 
	 * </pre>
	 */
	public void setLogged(boolean isLogged)
	{
		this.isLogged = isLogged;
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * @return - 
	 * </pre>
	 */
	public Throwable getException()
	{
		return super.getCause();
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * </pre>
	 */
	public String getMessage()
	{
		return super.getMessage();
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * @return - 
	 * </pre>
	 */
	public String getRootMethod()
	{
		return this.getMethod(0);
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * @param depth
	 * @return - 
	 * </pre>
	 */
	public String getMethod(int depth)
	{
		return (depth > -1 && super.getStackTrace() != null && depth < super.getStackTrace().length && super.getStackTrace()[depth] != null) ? super.getStackTrace()[depth].getMethodName() : "";
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * @return - 
	 * </pre>
	 */
	public Class<?> getRootClass()
	{
		return this.getClass(0);
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * @param depth
	 * @return - 
	 * </pre>
	 */
	public Class<?> getClass(int depth)
	{
		return (depth > -1 && super.getStackTrace() != null && depth < super.getStackTrace().length && super.getStackTrace()[depth] != null) ? super.getStackTrace()[depth].getClass() : null;
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan
	 *
	 * @param logLevel - the logLevel to set
	 * </pre>
	 */
	public void setLogLevel(String logLevel)
	{
		this.logLevel = logLevel;
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan
	 *
	 * @return - Returns the logLevel
	 * </pre>
	 */
	public String getLogLevel()
	{
		return logLevel;
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * @param t
	 * @return - 
	 * </pre>
	 */
	public static String getStringOfStackTrace(Throwable t)
	{
		StringBuilder sb = new StringBuilder(500);
		sb.append(t.getClass().getName());
		if (t.getMessage() != null && t.getMessage().length() > 0) {
			sb.append(": ");
			sb.append(t.getMessage());
		}
		sb.append(nextLine);
		for (StackTraceElement ste : t.getStackTrace()) {
			if (ste != null) {
				sb.append("\t\tat ");
				sb.append(ste.toString());
				sb.append(nextLine);
			}
		}
		return sb.toString();
	}

}
