package io.task.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionWrapperImpl implements ConnectionWrapper {
	
	private String url;
	private String user;
	private String password;

	@Override
	public Connection get() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
