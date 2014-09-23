package io.task;

import io.task.loader.BeanLoader;
import io.task.model.BeanModel;
import io.task.model.BeanPropertyModel;
import io.task.sql.ConnectionWrapper;
import io.task.sql.SqlWrapper;

import java.util.Map;

public class Startup {
	
	private ConnectionWrapper connectionWrapper;
	private SqlWrapper sqlWrapper;
	private BeanLoader<Map<String, BeanModel>> beanDefLoader;
	private BeanLoader<Map<String, BeanPropertyModel>> beanPropertyDefLoader;
	private BeanLoader<Void> beanLoader;
	
	public void start()
	{
		sqlWrapper.setConnectionWrapper(connectionWrapper);
		
		
		beanLoader.load();
	}

	public ConnectionWrapper getConnectionWrapper() {
		return connectionWrapper;
	}
	public void setConnectionWrapper(ConnectionWrapper connectionWrapper) {
		this.connectionWrapper = connectionWrapper;
	}
	public SqlWrapper getSqlWrapper() {
		return sqlWrapper;
	}
	public void setSqlWrapper(SqlWrapper sqlWrapper) {
		this.sqlWrapper = sqlWrapper;
	}
	public BeanLoader<Map<String, BeanModel>> getBeanDefLoader() {
		return beanDefLoader;
	}
	public void setBeanDefLoader(BeanLoader<Map<String, BeanModel>> beanDefLoader) {
		this.beanDefLoader = beanDefLoader;
	}
	public BeanLoader<Map<String, BeanPropertyModel>> getBeanPropertyDefLoader() {
		return beanPropertyDefLoader;
	}
	public void setBeanPropertyDefLoader(
			BeanLoader<Map<String, BeanPropertyModel>> beanPropertyDefLoader) {
		this.beanPropertyDefLoader = beanPropertyDefLoader;
	}
	public BeanLoader<Void> getBeanLoader() {
		return beanLoader;
	}
	public void setBeanLoader(BeanLoader<Void> beanLoader) {
		this.beanLoader = beanLoader;
	}
	
}
