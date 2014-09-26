package io.task.loader;

import io.task.context.Context;
import io.task.exception.BaseException;
import io.task.exception.BeanCircularDependencyException;
import io.task.exception.BeanSetterDelayedException;
import io.task.exception.BeanMissingException;
import io.task.exception.VirtualBeanInstantiationException;
import io.task.model.BeanModel;
import io.task.model.BeanPropertyModel;
import io.task.model.BeanPropertyModel.PropertyModel;
import io.task.spring.SpringApplicationContextWrapper;
import io.task.tasks.Task;
import io.task.util.BeanUtil;
import io.task.util.ClassUtil;
import io.task.util.MapUtil;
import io.task.util.TaskConstant;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BeanInstanceLoader implements Loader<Void>
{
	private static final Logger	logger	= LoggerFactory.getLogger(BeanInstanceLoader.class);

	private Map<String, Task>		taskContext;	
	private Map<String, Object> beanMap = new HashMap<String, Object>();

	private Loader<Map<String, BeanModel>> beanLoader;
	
	private Map<String, BeanModel> beanModelMap;
	
	private Map<String, BeanModel> constrCirDepMap = new HashMap<String, BeanModel>();
	private Map<PropertyObject, PropertyObject> delayedObjectMap = new HashMap<PropertyObject, BeanInstanceLoader.PropertyObject>();

	private Map<String, Object> virtualPropCirDepMap = new HashMap<String, Object>();

	private Context context;

	@Override
	public Void load()
	{
		if (logger.isInfoEnabled() == true) {
			logger.info(TaskConstant.START_STRING);
		}

		try {
			beanModelMap = beanLoader.load();
			taskContext = new HashMap<String, Task>(MapUtil.getOptimumMapSize(beanModelMap.size()));

			loadBeans();
	
			context.setTaskContext(Collections.unmodifiableMap(taskContext));

		} catch (Exception e) {
			logger.error("{}",e);
			MapUtil.clear(taskContext);
			throw new BaseException(e);
		} finally {
			MapUtil.clear(beanMap);
			MapUtil.clear(beanModelMap);
			MapUtil.clear(constrCirDepMap);
			MapUtil.clear(delayedObjectMap);
			MapUtil.clear(virtualPropCirDepMap);
		}
		if (logger.isInfoEnabled() == true) {
			logger.info(TaskConstant.END_STRING);
		}
		return null;
	}


	/**<pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 * 
	 * @param beanModelMap
	 * @param beanPropModelMap - 
	 * </pre>
	 * @throws Exception 
	 */
	private void loadBeans() throws Exception
	{
		List<String> beanIdLst = sortNoConstructorBeansToStart();
		
		for(String beanId : beanIdLst)
		{
			try
			{
				loadBean(beanId);
				processDelayedBeanQueue();
			}
			catch(VirtualBeanInstantiationException e)
			{
				logger.debug("Friendly exception in this case: ", e);
			}

		}
	}

	private List<String> sortNoConstructorBeansToStart() throws Exception
	{
		List<String> beanIdLst = new ArrayList<String>(beanModelMap.size());
		List<String> constrBeanLst = new ArrayList<String>(beanModelMap.size());
		Iterator<Entry<String, BeanModel>> itrBean = beanModelMap.entrySet().iterator();

		while(itrBean.hasNext())
		{
			BeanModel bm = itrBean.next().getValue();

			if(bm.getBeanProperties().getConstructor().isEmpty() == false)
			{
				constrBeanLst.add(bm.getId());
			}
			else
			{
				beanIdLst.add(bm.getId());
			}
		}		

		beanIdLst.addAll(constrBeanLst);
		
		return beanIdLst;
	}

	private void processDelayedBeanQueue() throws Exception
	{
		if (delayedObjectMap.isEmpty() == false)
		{
			Map<PropertyObject,PropertyObject> map = new HashMap<PropertyObject, BeanInstanceLoader.PropertyObject>(delayedObjectMap);

			for(Iterator<PropertyObject> itr = map.keySet().iterator(); itr.hasNext();)
			{
				PropertyObject po = itr.next();
				itr.remove();
				delayedObjectMap.remove(po);

				try
				{
					invokeMethod(po.objInstance.getClass(), po.objInstance, po.pm);
				}
				catch(BeanSetterDelayedException e)
				{
					logger.warn("{}",e.getMessage());
				}
			}
		}
	}
	
	private void loadBean(String beanId) throws Exception
	{
		BeanModel bean = beanModelMap.get(beanId);

		if(bean == null)
		{
			throw new BeanMissingException("Definition of bean ID: " + beanId + " is not found");
		}

		if(bean.getType().equals(TaskConstant.SPRING))
		{
			beanMap.put(beanId, SpringApplicationContextWrapper.getBean(beanId));
		}
		else
		{
			Object objInstance = beanMap.get(bean.getId());

			if(objInstance == null)
			{
				BeanPropertyModel bpm = bean.getBeanProperties();
				
				if(bpm == null)
				{
					throw new BeanMissingException("Definition of bean ID: " + beanId + " is not found");
				}
				
				Class<?> clazz = bean.getType().equals(TaskConstant.VIRTUAL) ? null : Class.forName(bean.getClassName());
				Map<String, Map<PropertyModel, Object>> virtualProperties = bpm.getVirtualProperties();
	
				if(virtualProperties.containsKey(TaskConstant.VIRTUAL_INHERIT_PROPERTY))
				{
					resolveVirtualPropInheritance(bpm);
				}
	
				if(clazz == null)//bean.getClassName().equals(TaskConstant.VIRTUAL))
				{
	//					logger.info("Bean ID: {} is abstract and cannot be instantiated", bean.getId());
	//					return;
					throw new VirtualBeanInstantiationException("Bean ID: " + bean.getId() + " is virtual and cannot be instantiated");
				}
	
				try
				{
	
					objInstance = createInstance(clazz,bpm);
	
					if(objInstance instanceof Task) 
					{
						taskContext.put(bean.getId(), (Task) objInstance);
						((Task) objInstance).setContext(context);
					}
					else
					{
						beanMap.put(bean.getId(), objInstance);
					}
	
					Iterator<Map<PropertyModel, Object>> itrPmMap = bpm.getProperties().values().iterator();
			
					while(itrPmMap.hasNext())
					{
						Iterator<PropertyModel> itrPm = itrPmMap.next().keySet().iterator();
						
						while(itrPm.hasNext())
						{
							PropertyModel pm = itrPm.next();
							try 
							{
								invokeMethod(clazz, objInstance, pm);
							}
							catch(BeanSetterDelayedException e)
							{
								logger.warn("{}",e.getMessage());
							}
						}
					}
				}
				catch(VirtualBeanInstantiationException e)
				{
					throw new BaseException(e);
				}
			}
		}
	}
	
	private void resolveVirtualPropInheritance(BeanPropertyModel bpm) {

		if(virtualPropCirDepMap.containsKey(bpm.getBeanId()) == false)
		{
			if(bpm.getVirtualProperties().containsKey(TaskConstant.VIRTUAL_INHERIT_PROPERTY))
			{
				virtualPropCirDepMap.put(bpm.getBeanId(), null);
	
				List<String> inheritBeanIds = bpm.getVirtualProperties().remove(TaskConstant.VIRTUAL_INHERIT_PROPERTY).keySet().iterator().next().getPropertyValues();

				for(String inheritBeanId : inheritBeanIds)
				{
					BeanModel bm = beanModelMap.get(inheritBeanId);

					if(bm == null)
					{
						throw new BeanMissingException("No bean definition found against inherited bean ID: " + inheritBeanId);
					}

					BeanPropertyModel ibpm = bm.getBeanProperties();

					if(ibpm == null)
					{
						throw new BeanMissingException("No bean properties found against inherited bean ID: " + inheritBeanId);
					}

					try
					{
						resolveVirtualPropInheritance(ibpm);

						Map<String, Map<PropertyModel,Object>> newMap = new HashMap<String, Map<PropertyModel,Object>>(ibpm.getProperties());

						newMap.putAll(bpm.getProperties());
						bpm.setProperties(newMap);
					}
					catch(BeanCircularDependencyException e)
					{
						logger.warn("For bean ID: " + bpm.getBeanId(), e);
					}
				}
	
				virtualPropCirDepMap.remove(bpm.getBeanId());			
			}
		}
		else
		{
			throw new BeanCircularDependencyException("Circular dependency detected in bean inheritance of bean ID: " + bpm.getBeanId());
		}
	}


	private Object invokeMethod(Class<?> clazz, Object objInstance, PropertyModel pm) throws Exception
	{
		Object[][] paramArr = parseProperty(pm, objInstance);
		
		Method method = clazz.getMethod(pm.getPropertyName(), (Class<?>[]) paramArr[0]);

		return method.invoke(objInstance, paramArr[1]);

	}

	private Object invokeConstructor(Class<?> clazz, PropertyModel pm) throws Exception
	{
		Object[][] paramArr;
		
		String beanId = pm.getParent().getBeanId();
		
		constrCirDepMap.put(beanId, null);
		
		paramArr = parseProperty(pm, null);
		
		constrCirDepMap.remove(beanId);
		
		Constructor<?> constr = clazz.getConstructor((Class<?>[]) paramArr[0]);
		
		return constr.newInstance(paramArr[1]);

	}
	
	private Object[][] parseProperty(PropertyModel pm, Object objInstance) throws Exception
	{
		Object[][] arr = {null,null};
		List<Class<?>> paramTypeList = new ArrayList<Class<?>>();
		List<Object> paramValueList = new ArrayList<Object>();
		Class<?> []dummyClassArray = new Class<?>[0];
		Object []dummyObjectArray = new Object[0];
		
		Iterator<String> itrType = pm.getPropertyValueTypes().iterator();
		Iterator<String> itrValue = pm.getPropertyValues().iterator();
		String value;
		
		while(itrType.hasNext())
		{
			Class<?> clazz = ClassUtil.getClass(itrType.next());

			paramTypeList.add(clazz);
			value=itrValue.next();

			if(ClassUtil.isBuiltInDataType(clazz))
			{
				paramValueList.add(ClassUtil.toJavaTypes(clazz, value));
			}
			else
			{
				Object obj = beanMap.get(value);
				
				if(obj == null)
				{
					if(constrCirDepMap.containsKey(value))
					{
						if(constrCirDepMap.containsKey(pm.getParent().getBeanId()))
						{
							throw new BeanCircularDependencyException("Circular dependency in object creation found for bean IDs " + constrCirDepMap.keySet());
						}
						else if(objInstance != null)
						{
							PropertyObject po = new PropertyObject(objInstance, pm);

							if(delayedObjectMap.containsKey(po) == false)
							{
								delayedObjectMap.put(po, null);
							}
							throw new BeanSetterDelayedException("Bean ID: " + pm.getParent().getBeanId() + " method " + pm.getPropertyName() + " call postponed to resolve circular dependency with bean ID " + value);
						}
					}
					loadBean(value);
					obj = beanMap.get(value);
				}
				paramValueList.add(obj);
			}
		}
		
		arr[0] = paramTypeList.toArray(dummyClassArray);
		arr[1] = paramValueList.toArray(dummyObjectArray);
		
		return arr;
	}

//	private boolean isInitMethod(String methodName, Class<?> clazz, BeanPropertyModel bpm)
//	{
//		Map<String, PropertyModel> map = bpm.getProperties();
//		PropertyModel pm = map.get(TaskConstant.STATIC_METHOD);
//		
//		if(pm != null)
//		{
//			pm = map.get(pm.getPropertyValues().get(0));
//		}
//		
//		return (methodName.equals(clazz.getSimpleName()) || (pm != null && pm.equals(methodName))) || methodName.equals(TaskConstant.STATIC_METHOD);
//	}

	private Object createInstance(Class<?> clazz, BeanPropertyModel bpm) throws Exception {
		
		Object obj = null;
		
		Map<String, Map<PropertyModel, Object>> pmMap = bpm.getConstructor();
		
		if(pmMap.isEmpty())
		{
			obj = clazz.newInstance();			
		}
		else
		{
			PropertyModel pm = pmMap.values().iterator().next().keySet().iterator().next();

			if(BeanUtil.isConstructorMethod(clazz, pm.getPropertyName()))
			{
				obj = invokeConstructor(clazz, pm);
			}
			else //if( pmMap.containsKey(TaskConstant.STATIC_METHOD))
			{
//				pm = pmMap.get(TaskConstant.STATIC_METHOD);
//				obj = invokeMethod(clazz, null, pmMap.get(pm.getPropertyValues().get(0)));
				obj = invokeMethod(clazz, null, pm);
			}
		}

		return obj;

	}

	/**
	 * @param beanLoader the beanModelLoader to set
	 */
	public void setBeanLoader(Loader<Map<String, BeanModel>> beanLoader) {
		this.beanLoader = beanLoader;
	}


	public Context getContext() {
		return context;
	}


	public void setContext(Context context) {
		this.context = context;
	}

	private static class PropertyObject
	{
		Object objInstance;
		PropertyModel pm;
		
		PropertyObject(Object objInstance, PropertyModel pm)
		{
			this.objInstance = objInstance;
			this.pm = pm;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((objInstance == null) ? 0 : objInstance.hashCode());
			result = prime * result + ((pm == null) ? 0 : pm.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PropertyObject other = (PropertyObject) obj;
			if (objInstance == null) {
				if (other.objInstance != null)
					return false;
			} else if (!objInstance.equals(other.objInstance))
				return false;
			if (pm == null) {
				if (other.pm != null)
					return false;
			} else if (!pm.equals(other.pm))
				return false;
			return true;
		}
	}
}
