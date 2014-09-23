/*
 * Usage rights pending...
 * 
 * 
 * 
 * 
 * 
 * 
 * ****************************************************************************
 */

package io.task.model;

import java.util.List;

/**
 * <pre>
 * Created By : Ahmed Mobasher Khan
 * Creation Date : Aug 10, 2014
 * 
 * Purpose : 
 * 
 * Updated By : 
 * Updated Date : 
 * Comments : 
 * </pre>
 */
public class BeanModel
{
	private String					id;
	private String					className;
	private String 					type;
	private List<BeanPropertyModel>	properties;


	public String getId()
	{
		return id;
	}


	public void setId(String id)
	{
		this.id = id;
	}


	public String getClassName()
	{
		return className;
	}


	public void setClassName(String className)
	{
		this.className = className;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public List<BeanPropertyModel> getProperties()
	{
		return properties;
	}


	public void setProperties(List<BeanPropertyModel> properties)
	{
		this.properties = properties;
	}
}