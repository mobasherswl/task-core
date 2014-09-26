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

package io.task.loader.database;

import io.task.exception.BaseException;
import io.task.loader.Loader;
import io.task.model.BeanPropertyModel;
import io.task.model.BeanPropertyModel.PropertyModel;
import io.task.sql.RowMapper;
import io.task.sql.SqlWrapper;
import io.task.util.BeanUtil;
import io.task.util.TaskConstant;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import static io.task.util.SqlConstant.*;

/**<pre>
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
public class BeanPropertyDefinitionLoader implements Loader<Map<String, BeanPropertyModel>>
{	
	protected SqlWrapper				sqlWrapper	= null;
//	private static String			loadQuery	= "select bean_id,property_order,property_value_type,property_name,property_value,property_value_order from bean_property order by bean_id,property_order,property_value_order";
//	private static String			selectQuery	= 	"select bean_id, class_name, property_order, property_value_type, property_name, property_value, property_value_order from bean_property, bean where bean_id = id order by bean_id , property_order , property_value_order";
//	private static String			selectQuery	= 	StringUtil.mergeStrings("select ",COL_BEAN_ID, ",", COL_CLASS_NAME, ",", COL_PROPERTY_ORDER, ",", COL_PROPERTY_VALUE_TYPE, ",", COL_PROPERTY_NAME, ",", COL_PROPERTY_VALUE, ",", COL_PROPERTY_VALUE_ORDER, " from ", TBL_BEAN, ",", TBL_BEAN_PROPERTY, " where ", COL_BEAN_ID , "=", COL_ID, " order by ", COL_BEAN_ID, ",", COL_PROPERTY_ORDER, ",", COL_PROPERTY_VALUE_ORDER);
//	private static String			countQuery	= "select count(*) from bean_property";
//	private static String			countQuery	= "select count(*) from " + TABLE_BEAN_PROPERTY;

	@Override
	public Map<String, BeanPropertyModel> load()
	{
//		final Map<String, BeanPropertyModel> map = new HashMap<String, BeanPropertyModel>(/*(int)(getCount()/0.75 + 1)*/);

		final Map<String,Object> currentState = new HashMap<String, Object>();
		currentState.put("beanId", "");
		currentState.put("prevBeanId", "");
		currentState.put("propertyName", "");
		currentState.put("prevPropertyName", "");
		currentState.put("propertyOrder", -1);
		currentState.put("prevPropertyOrder", -1);
		currentState.put("bpm", new BeanPropertyModel());
		currentState.put("pm", new BeanPropertyModel.PropertyModel());
		
		return sqlWrapper.queryForMap(QRY_SEL_TBL_BEAN_PROPERTY, new RowMapper<Map.Entry<String, BeanPropertyModel>>() {

			@Override
			public Map.Entry<String, BeanPropertyModel> mapRow(ResultSet rs, int rowNum)
			{
				try
				{
					BeanPropertyModel bpm = (BeanPropertyModel) currentState.get("bpm");
					BeanPropertyModel.PropertyModel pm = (PropertyModel) currentState.get("pm");
					String beanId = (String) currentState.get("beanId"), prevBeanId = (String) currentState.get("prevBeanId");
					String propertyName = (String) currentState.get("propertyName"), prevPropertyName = (String) currentState.get("prevPropertyName");
					int propertyOrder = (Integer) currentState.get("propertyOrder"), prevPropertyOrder = (Integer) currentState.get("prevPropertyOrder");
					int propertyValueOrder = rs.getInt(COL_PROPERTY_VALUE_ORDER);
					String className = rs.getString(COL_CLASS_NAME);
					String beanType = rs.getString(COL_TYPE);

					beanId = rs.getString(COL_BEAN_ID);
					propertyName = rs.getString(COL_PROPERTY_NAME);
					propertyOrder = rs.getInt(COL_PROPERTY_ORDER);
					
					currentState.put("beanId", beanId);
					currentState.put("propertyName", propertyName);
					currentState.put("propertyOrder", propertyOrder);
	
					if(beanId.equals(prevBeanId) == false ) {
						bpm = new BeanPropertyModel();
						bpm.setBeanId(beanId);
//						map.put(bpm.getBeanId(), bpm);
					}

					@SuppressWarnings("unchecked")
					Map<PropertyModel, Object> propMap = (Map<PropertyModel, Object>) currentState.get("propMap");

					if(propertyName.equals(prevPropertyName) == false/* || propertyOrder != prevPropertyOrder */) {

						if(beanType.equals(TaskConstant.VIRTUAL) == false && BeanUtil.isConstructorMethod(className, propertyName))
						{
							if(bpm.getConstructor().size() == 1)
							{
								throw new BaseException("Multiple constructor / static methods found for bean ID: " + beanId);
							}
							propMap = new HashMap<BeanPropertyModel.PropertyModel, Object>();
							bpm.getConstructor().put(propertyName, propMap);
						}
						else if(beanType.equals(TaskConstant.VIRTUAL) == false && bpm.getConstructor().containsKey(TaskConstant.VIRTUAL_CONSTRUCTOR_METHOD) && bpm.getConstructor().get(TaskConstant.VIRTUAL_CONSTRUCTOR_METHOD).keySet().iterator().next().getPropertyName().equals(propertyName))
						{
							bpm.getConstructor().remove(TaskConstant.VIRTUAL_CONSTRUCTOR_METHOD);
//							propMap = new HashMap<BeanPropertyModel.PropertyModel, Object>();
//							bpm.getConstructor().put(propertyName, propMap);
						}
						else if(propertyName.startsWith(TaskConstant.VIRTUAL))
						{
							propMap = new HashMap<BeanPropertyModel.PropertyModel, Object>();
							bpm.getVirtualProperties().put(propertyName, propMap);
						}
						else
						{
							propMap = new HashMap<BeanPropertyModel.PropertyModel, Object>();
							bpm.getProperties().put(propertyName, propMap);
						}
						pm = new PropertyModel();

						pm.setPropertyName(propertyName);
						pm.setParent(bpm);
						pm.setPropertyOrder(propertyOrder);
						
						propMap.put(pm, null);
					}
					
					else if(propertyOrder != prevPropertyOrder)
					{
						pm = new PropertyModel();

						pm.setPropertyName(propertyName);
						pm.setParent(bpm);
						pm.setPropertyOrder(propertyOrder);
						
						propMap.put(pm, null);
					}

					pm.getPropertyValueTypes().add(rs.getString(COL_PROPERTY_VALUE_TYPE));
					pm.getPropertyValues().add(rs.getString(COL_PROPERTY_VALUE));
					pm.getPropertyValueOrder().add(propertyValueOrder);
	
					prevBeanId = beanId;
					prevPropertyName = propertyName;
					prevPropertyOrder = propertyOrder;
					currentState.put("prevBeanId", prevBeanId);
					currentState.put("prevPropertyName", prevPropertyName);
					currentState.put("bpm", bpm);
					currentState.put("pm", pm);
					currentState.put("prevPropertyOrder", prevPropertyOrder);
					currentState.put("propMap", propMap);
					
					final BeanPropertyModel ebpm = bpm;
	
					return new Map.Entry<String, BeanPropertyModel>() {
	
						@Override
						public String getKey() {
							return ebpm.getBeanId();
						}
	
						@Override
						public BeanPropertyModel getValue() {
							return ebpm;
						}
	
						@Override
						public BeanPropertyModel setValue(BeanPropertyModel value) {
							return ebpm;
						}
					};
				}
				
				catch(Exception e)
				{
					throw new BaseException(e);
				}
			}
			
		});
	}
	
	public int getCount()
	{
		return 0;
//		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		return jt.queryForObject(countQuery, Integer.class);
	}

	public void setSqlWrapper(SqlWrapper sqlWrapper) {
		this.sqlWrapper = sqlWrapper;
	}

}
