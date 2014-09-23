package io.task.spring;

import io.task.exception.SpringException;

import java.lang.reflect.Method;

public class SpringApplicationContextWrapper {

	private static Object context;
	private static Method getBean;
	
	public static void setContext(Object ctx)
	{
		try 
		{
			getBean = ctx.getClass().getMethod("getBean", String.class);
			context = ctx;
		} catch (Exception e) {
			throw new SpringException(e);
		}
	}
	
	public static Object getBean(String beanId)
	{
		try 
		{
			return getBean.invoke(context, beanId);
		} catch (Exception e) {
			throw new SpringException(e);
		}
	}

}
