package io.task.tasks;

import java.util.Map;

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
public abstract class Task
{
	protected static final Logger logger = LoggerFactory.getLogger(Task.class);
	private String id;
	protected int		totalAttempts = 1;


	public abstract void process(Map<String, Object> dataMap);


	/**<pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 *
	 * @return - Returns the id
	 * </pre>
	 */
	public String getId()
	{
		return id;
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 *
	 * @return - Returns the totalAttempts
	 * </pre>
	 */
	public int getTotalAttempts()
	{
		return totalAttempts;
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 *
	 * @param totalAttempts - the totalAttempts to set
	 * </pre>
	 */
	public void setTotalAttempts(int totalAttempts)
	{
		this.totalAttempts = totalAttempts;
	}
}
