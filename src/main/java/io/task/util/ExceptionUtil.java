package io.task.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

	public static String getStackTrace(Throwable throwable)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);

		try
		{
			throwable.printStackTrace(pw);
		}
		finally
		{
			StreamUtil.close(pw);
			StreamUtil.close(sw);
		}
		
		return sw.toString();
	}
}
