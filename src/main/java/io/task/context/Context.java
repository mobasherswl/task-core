package io.task.context;

import java.util.Map;

import io.task.tasks.Task;

public interface Context {
	
	void start();
	
	Task getTask(String taskId);
	
	boolean taskExists(String taskId);
	
	void startFlow(Map<String, Object> dataMap);

	void setTaskContext(Map<String, Task> taskContext);
}
