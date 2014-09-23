package io.task.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SqlUtil.class);

	public static <T extends Connection> void close(T t)
	{
		try {
			if(t != null) {
				t.close();
			}
		} catch (SQLException e) {
			logger.error("{}", e);
		}
	}

	public static <T extends Statement> void close(T t)
	{
		try {
			if(t != null) {
				t.close();
			}
		} catch (SQLException e) {
			logger.error("{}", e);
		}
	}

	public static <T extends ResultSet> void close(T t)
	{
		try {
			if(t != null) {
				t.close();
			}
		} catch (SQLException e) {
			logger.error("{}", e);
		}
	}

}
