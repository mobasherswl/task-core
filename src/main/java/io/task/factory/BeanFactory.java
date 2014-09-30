package io.task.factory;

import io.task.loader.Loader;
import io.task.tasks.Task;

public interface BeanFactory extends Loader<Void> {

	Task getTask(String taskId);
	
	Object getBean(String beanId);
	
	boolean beanExists(String beanId);
	
	boolean taskExists(String taskId);
	
	
}
