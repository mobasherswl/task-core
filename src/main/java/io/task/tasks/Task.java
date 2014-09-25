package io.task.tasks;

import io.task.context.Context;

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
//	private static Map<String, Task>		taskMap;
	protected static final Logger logger = LoggerFactory.getLogger(Task.class);
	private String id;
	protected int		totalAttempts = 1;
	
	private Context context;


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


	public Context getContext() {
		return context;
	}


	public void setContext(Context context) {
		this.context = context;
	}

//	public static void start(Map<String, Object> dataMap)
//	{
//		String nextTaskId = (String) dataMap.get(TaskConstant.NEXT_TASK_ID);
//		Task task;
//		boolean loop=true;
//
//		while (loop) {
//
//			if(StringUtil.isNullOrEmpty(nextTaskId))
//			{
//				logger.error("No next task ID. It's null or empty");
//				loop = false;
//			}
//			else
//			{
//				logger.info("Next task ID: {}",nextTaskId);
//	
//				task = taskMap.get(nextTaskId);
//	
//				if(task == null)
//				{
//					logger.error("No task found against task ID: {}", nextTaskId);
//					loop = false;
//				}
//				else
//				{
//					task.process(dataMap);
//					nextTaskId = (String) dataMap.get(TaskConstant.NEXT_TASK_ID);
//				}
//			}
//		}
//	}
//
//
//	public static void setTaskMap(Map<String, Task> taskMap)
//	{
//		Task.taskMap = taskMap;
//	}
//	
//	public static boolean taskExists(String taskId)
//	{
//		if(taskMap == null || taskId == null)
//			return false;
//
//		return taskMap.containsKey(taskId);
//	}
//
//	public static Task getTask(String taskId)
//	{
//		return taskMap.get(taskId);
//	}
}
