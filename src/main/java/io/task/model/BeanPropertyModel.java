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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class BeanPropertyModel
{
	private String			beanId;
	private Map<String, Set<PropertyModel>> properties = new HashMap<String, Set<PropertyModel>>();
	private Map<String, Set<PropertyModel>> constructor = new HashMap<String, Set<PropertyModel>>();
	private Map<String, Set<PropertyModel>> virtualProperties = new HashMap<String, Set<PropertyModel>>();
	
	public String getBeanId()
	{
		return beanId;
	}


	public void setBeanId(String beanId)
	{
		this.beanId = beanId;
	}


	/**<pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 *
	 * @return - Returns the properties
	 * </pre>
	 */
	public Map<String, Set<PropertyModel>> getProperties()
	{
		return properties;
	}


	/**<pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 *
	 * @param properties - the properties to set
	 * </pre>
	 */
	public void setProperties(Map<String, Set<PropertyModel>> properties)
	{
		this.properties = properties;
	}


	public Map<String, Set<PropertyModel>> getConstructor() {
		return constructor;
	}


	public void setConstructor(Map<String, Set<PropertyModel>> constructor) {
		this.constructor = constructor;
	}


	public Map<String, Set<PropertyModel>> getVirtualProperties() {
		return virtualProperties;
	}


	public void setVirtualProperties(Map<String, Set<PropertyModel>> virtualProperties) {
		this.virtualProperties = virtualProperties;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beanId == null) ? 0 : beanId.hashCode());
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
		BeanPropertyModel other = (BeanPropertyModel) obj;
		if (beanId == null) {
			if (other.beanId != null)
				return false;
		} else if (!beanId.equals(other.beanId))
			return false;
		return true;
	}


	public static class PropertyModel {
		private BeanPropertyModel parent;
		private String			propertyName;
		private int propertyOrder;
		private List<String>	propertyValueTypes = new ArrayList<String>();
		private List<String>	propertyValues	= new ArrayList<String>();
		private List<Integer>   propertyValueOrder = new ArrayList<Integer>();


		public BeanPropertyModel getParent() {
			return parent;
		}


		public void setParent(BeanPropertyModel parent) {
			this.parent = parent;
		}


		/**<pre>
		 * 
		 * @author - Ahmed Mobasher Khan 
		 *
		 * @return - Returns the propertyType
		 * </pre>
		 */
		public List<String> getPropertyValueTypes()
		{
			return propertyValueTypes;
		}
	
	
		/**<pre>
		 * 
		 * @author - Ahmed Mobasher Khan 
		 *
		 * @param propertyType - the propertyType to set
		 * </pre>
		 */
		public void setPropertyValueTypes(List<String> propertyValueTypes)
		{
			this.propertyValueTypes = propertyValueTypes;
		}
	
	
		public String getPropertyName()
		{
			return propertyName;
		}
	
	
		public void setPropertyName(String propertyName)
		{
			this.propertyName = propertyName;
		}
	
	
		public int getPropertyOrder() {
			return propertyOrder;
		}


		public void setPropertyOrder(int propertyOrder) {
			this.propertyOrder = propertyOrder;
		}


		public List<String> getPropertyValues()
		{
			return propertyValues;
		}
	
	
		public void setPropertyValues(List<String> propertyValues)
		{
			this.propertyValues = propertyValues;
		}


		public List<Integer> getPropertyValueOrder() {
			return propertyValueOrder;
		}


		public void setPropertyValueOrder(List<Integer> propertyValueOrder) {
			this.propertyValueOrder = propertyValueOrder;
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((parent == null) ? 0 : parent.hashCode());
			result = prime * result + propertyOrder;
			result = prime
					* result
					+ ((propertyValueOrder == null) ? 0 : propertyValueOrder
							.hashCode());
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
			PropertyModel other = (PropertyModel) obj;
			if (parent == null) {
				if (other.parent != null)
					return false;
			} else if (!parent.equals(other.parent))
				return false;
			if (propertyOrder != other.propertyOrder)
				return false;
			if (propertyValueOrder == null) {
				if (other.propertyValueOrder != null)
					return false;
			} else if (!propertyValueOrder.equals(other.propertyValueOrder))
				return false;
			return true;
		}

	}
}
