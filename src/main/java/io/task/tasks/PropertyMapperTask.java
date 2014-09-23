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

package io.task.tasks;

import io.task.util.TaskConstant;

import java.util.HashMap;
import java.util.Map;

/**<pre>
 * Created By : Ahmed Mobasher Khan
 * Creation Date : Aug 17, 2014
 * 
 * Purpose : 
 * 
 * Updated By : 
 * Updated Date : 
 * Comments : 
 * </pre>
 */
public class PropertyMapperTask extends Task
{
	
	private Map<String,Map<String,String>> propNameMap = new HashMap<String, Map<String, String>>();
	private String nextTaskId;

	/* (non-Javadoc)
	 * @see com.ivr.javaagi.task.Task#process(java.util.Map)
	 */
	@Override
	public void process(Map<String, Object> dataMap)
	{
		if(logger.isDebugEnabled()) {
			logger.debug("Before: dataMap: {}, propertyMap: {}", dataMap.toString(), propNameMap.toString());
		}
		for(String propName : propNameMap.keySet())
		{
			Object obj = dataMap.get(propName);
			
			if(obj != null) {
				Map<String, String> map = propNameMap.get(propName);
				dataMap.put(map.get("newKey"), map.get(obj) == null ? obj : map.get(obj));
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug("After: dataMap: {}, propertyMap: {}", dataMap.toString(), propNameMap.toString());
		}
		dataMap.put(TaskConstant.NEXT_TASK_ID, nextTaskId);
	}
	
	public void setPropertyMap(String oldKey, String newKey, String oldValue, String newValue)
	{
		Map<String, String> map = propNameMap.get(oldKey);
		
		if(map == null) {
			map = new HashMap<String, String>();
			propNameMap.put(oldKey, map);
			map.put("newKey", newKey);
		}
		map.put(oldValue, newValue);
	}
	
	/**<pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 *
	 * @return - Returns the nextTaskId
	 * </pre>
	 */
	public String getNextTaskId()
	{
		return nextTaskId;
	}

	/**<pre>
	 * 
	 * @author - Ahmed Mobasher Khan 
	 *
	 * @param nextTaskId - the nextTaskId to set
	 * </pre>
	 */
	public void setNextTaskId(String nextTaskId)
	{
		this.nextTaskId = nextTaskId;
	}

	static class Entry<K,V,O> {
      final K k;
      V v;
      O o;

      /**
       * Creates new entry.
       */
      Entry(K k, V v, O o) {
          this.v = v;
          this.k = k;
          this.o = o;
      }

      public final K getKey() {
          return k;
      }

      public final V getValue() {
          return v;
      }
      
      public final O getObject() {
      	return o;
      }

      public final V setValue(V newValue) {
	    V oldValue = v;
          v = newValue;
          return oldValue;
      }

      public final String toString() {
          return getKey() + "=" + getValue();
      }

  }
}
