package io.task.tasks.group;

import io.task.exception.TaskException;
import io.task.tasks.Task;
import io.task.util.StringUtil;
import io.task.util.TaskConstant;

import java.util.HashMap;
import java.util.Map;

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
public class TaskGroup extends io.task.tasks.Task
{
	private Map<String, Task>				groupMap;
	private static Map<String, Task>		taskMap;
	private String								defaultTaskId;


	public TaskGroup()
	{
		super();
		groupMap = new HashMap<String, Task>();
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 *
	 * @param groupMap - the groupMap to set
	 * </pre>
	 */
	public void addTask(String taskId)
	{
		this.groupMap.put(taskId, null);
	}


	/**
	 * <pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 *
	 * @param startingTask - the startingTask to set
	 * </pre>
	 */
	public void setDefaultTaskId(String defaultTaskId)
	{
//		if (groupMap.containsKey(defaultTaskId) == true) {
			this.defaultTaskId = defaultTaskId;
//		} else {
//		}
	}


	@Override
	public void process(Map<String, Object> dataMap)
	{
		Task task = null;
		String nextTaskId = defaultTaskId;
//		int i = 0;
		logger.info("Starting task ID: {}",nextTaskId);
		logger.debug(dataMap.toString());
		while (groupMap.containsKey(nextTaskId) == true) {
			task = taskMap.get(nextTaskId);
			if(task == null) {
				String msg = StringUtil.mergeStrings("Group ID: ",getId()," contains invalid/unknown Task ID: ",nextTaskId);
				logger.error(msg);
				throw new TaskException(msg);
			}
			dataMap.put(TaskConstant.NEXT_TASK_ID, "");
			task.process(dataMap);
			nextTaskId = (String) dataMap.get(TaskConstant.NEXT_TASK_ID);
			if (StringUtil.isNullOrEmpty(nextTaskId) /*|| ++i >= totalAttempts*/) {
				logger.info("No next task ID returned: {}",nextTaskId);
				break;
			}
			else
			{
				logger.info("Next task ID: {}",nextTaskId);
			}
		}
	}


	public static void start(Map<String, Object> dataMap)
	{
		String nextTaskId = (String) dataMap.get(TaskConstant.NEXT_TASK_ID);
		Task task;
		boolean loop=true;

		while (loop) {

			if(StringUtil.isNullOrEmpty(nextTaskId))
			{
				logger.error("No next task ID. It's null or empty");
				loop = false;
			}
			else
			{
				logger.info("Next task ID: {}",nextTaskId);
	
				task = taskMap.get(nextTaskId);
	
				if(task == null)
				{
					logger.error("No task found against task ID: {}", nextTaskId);
					loop = false;
				}
				else
				{
					task.process(dataMap);
					nextTaskId = (String) dataMap.get(TaskConstant.NEXT_TASK_ID);
				}
			}
		}
	}


	/**<pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * @param taskMap2 - 
	 * </pre>
	 */
	public static void setTaskMap(Map<String, Task> taskMap)
	{
		TaskGroup.taskMap = taskMap;
	}
	
	public static boolean taskExists(String taskId)
	{
		if(TaskGroup.taskMap == null || taskId == null)
			return false;

		return TaskGroup.taskMap.containsKey(taskId);
	}

}
