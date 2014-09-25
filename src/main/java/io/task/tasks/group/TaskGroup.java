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
public class TaskGroup extends Task
{
	private Map<String, Task>				groupMap;
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
//			task = Task.getTask(nextTaskId);
			task = getContext().getTask(nextTaskId);
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

}
