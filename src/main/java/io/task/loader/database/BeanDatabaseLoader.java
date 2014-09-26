package io.task.loader.database;

import io.task.exception.BaseException;
import io.task.exception.BeanMissingException;
import io.task.loader.Loader;
import io.task.model.BeanModel;
import io.task.model.BeanPropertyModel;
import io.task.util.MapUtil;

import java.util.Map;

public class BeanDatabaseLoader implements Loader<Map<String, BeanModel>> {
	
	private Loader<Map<String, BeanModel>> beanDefinitionLoader;
	private Loader<Map<String, BeanPropertyModel>> beanPropertyLoader;

	@Override
	public Map<String, BeanModel> load() throws BaseException {
		
		try
		{
			Map<String, BeanModel> beanModelMap = beanDefinitionLoader.load();
			Map<String, BeanPropertyModel> beanPropertyModelMap = beanPropertyLoader.load();
			
			for(BeanModel bm : beanModelMap.values())
			{
				BeanPropertyModel bpm = beanPropertyModelMap.get(bm.getId());
				
				if(bpm == null)
				{
					throw new BeanMissingException("No bean properties found against bean ID: " + bm.getId());
				}
				else
				{
					bm.setBeanProperties(bpm);
				}
			}
			
			MapUtil.clear(beanPropertyModelMap);
			
			return beanModelMap;
		}
		catch(BeanMissingException bme)
		{
			throw bme;
		}
		catch(Exception e)
		{
			throw new BaseException(e);
		}
	}

	public Loader<Map<String, BeanModel>> getBeanDefinitionLoader() {
		return beanDefinitionLoader;
	}

	public void setBeanDefinitionLoader(
			Loader<Map<String, BeanModel>> beanDefinitionLoader) {
		this.beanDefinitionLoader = beanDefinitionLoader;
	}

	public Loader<Map<String, BeanPropertyModel>> getBeanPropertyLoader() {
		return beanPropertyLoader;
	}

	public void setBeanPropertyLoader(
			Loader<Map<String, BeanPropertyModel>> beanPropertyLoader) {
		this.beanPropertyLoader = beanPropertyLoader;
	}

}
