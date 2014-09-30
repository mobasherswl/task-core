package io.task.context;

import io.task.exception.BaseException;
import io.task.factory.BeanFactory;
import io.task.tasks.Task;
import io.task.util.StringUtil;
import io.task.util.TaskConstant;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextImpl implements Context {

	protected static final Logger logger = LoggerFactory.getLogger(ContextImpl.class);

	private BeanFactory beanFactory;

	@Override
	public void start() throws BaseException {
		logger.info("Starting bean loader to load bean definitions & their instances");

		beanFactory.load();
		
		logger.info("All beans processed & loaded successfully");
	}

	@Override
	public Task getTask(String taskId) {
		return beanFactory.getTask(taskId);
	}

	@Override
	public boolean taskExists(String taskId) {
		return beanFactory.taskExists(taskId);
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
	
				task = getTask(nextTaskId);
	
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

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object getBean(String beanId) {
		return beanFactory.getBean(beanId);
	}

	@Override
	public boolean beanExists(String beanId) {
		return beanFactory.beanExists(beanId);
	}

}
