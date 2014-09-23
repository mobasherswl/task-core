package io.task.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DataSourceConnectionWrapper implements ConnectionWrapper {
	
	private DataSource dataSource;

	@Override
	public Connection get() throws SQLException {
		return dataSource.getConnection();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
