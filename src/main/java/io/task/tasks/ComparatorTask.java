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

import io.task.tasks.PropertyMapperTask.Entry;
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
public class ComparatorTask extends Task
{
	private Map<String, Entry<String, String, String>> compareKeyMap = new HashMap<String, Entry<String, String, String>>();


	/* (non-Javadoc)
	 * @see com.ivr.javaagi.task.Task#process(java.util.Map)
	 */
	@Override
	public void process(Map<String, Object> dataMap)
	{
		for(String key1 : compareKeyMap.keySet())
		{
			Entry<String, String, String> entry = compareKeyMap.get(key1);
			
			if(dataMap.get(key1).equals(dataMap.get(entry.getKey())))
			{
				dataMap.put(TaskConstant.NEXT_TASK_ID, entry.getValue());
			}
			else
			{
				dataMap.put(TaskConstant.NEXT_TASK_ID, entry.getObject());
			}
		}
	}
	
	public void setCompareKeys(String key1, String key2, String equalNextTaskId, String notEqualNextTaskId)
	{
		compareKeyMap.put(key1, new Entry<String, String, String>(key2, equalNextTaskId, notEqualNextTaskId));
	}

}
