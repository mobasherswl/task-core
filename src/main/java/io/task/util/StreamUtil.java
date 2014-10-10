package io.task.util;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamUtil {
	
	private static Logger logger = LoggerFactory.getLogger(StreamUtil.class);
	
	public static <T extends Closeable> void close(T stream)
	{
		if(stream != null)
		{
			try
			{
				stream.close();
			}
			catch(IOException e)
			{
				logger.error("{}", e);
			}
		}
	}

}
