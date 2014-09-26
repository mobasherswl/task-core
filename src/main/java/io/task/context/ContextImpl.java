package io.task.context;

import io.task.exception.BaseException;
import io.task.loader.Loader;
import io.task.tasks.Task;
import io.task.util.MapUtil;
import io.task.util.StringUtil;
import io.task.util.TaskConstant;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextImpl implements Context {

	protected static final Logger logger = LoggerFactory.getLogger(ContextImpl.class);

	private Map<String, Task>		taskContext;
	private Loader<Void> beanLoader;

	@Override
	public void start() throws BaseException {
		logger.info("Starting bean loader to load bean definitions & their instances");

		beanLoader.load();
		
		logger.info("All beans processed & loaded successfully");
	}

	@Override
	public Task getTask(String taskId) {
		return MapUtil.isNullOrEmpty(taskContext) ? null : taskContext.get(taskId);
	}

	@Override
	public boolean taskExists(String taskId) {
		if(MapUtil.isNullOrEmpty(taskContext) || StringUtil.isNullOrEmptyTrimmed(taskId))
			return false;
		return taskContext.containsKey(taskId);
	}

	@Override
	public void startFlow(Map<String, Object> dataMap) {
		String nextTaskId = (String) dataMap.get(TaskConstant.NEXT_TASK_ID);
		Task task;
		boolean loop=true;

		while (loop) {

			if(StringUtil.isNullOrEmptyTrimmed(nextTaskId))
			{
				logger.error("No next task ID. It's null or empty");
				loop = false;
			}
			else
			{
				logger.info("Next task ID: {}",nextTaskId);
	
				task = taskContext.get(nextTaskId);
	
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

	public Loader<Void> getBeanLoader() {
		return beanLoader;
	}

	public void setBeanLoader(Loader<Void> beanInstanceLoader) {
		this.beanLoader = beanInstanceLoader;
	}

	@Override
	public void setTaskContext(Map<String, Task> taskContext) {
		this.taskContext = taskContext;
	}

}
