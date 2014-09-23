package io.task.sql;

import io.task.util.SqlUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlWrapperImpl implements SqlWrapper {
	
	private Logger logger = LoggerFactory.getLogger(SqlWrapperImpl.class);
	
	private ConnectionWrapper connectionWrapper;

	@Override
	public <K, V> Map<K, V> queryForMap(String sql,
			final RowMapper<Entry<K, V>> rowMapper) {
		
		final Map<K, V> map = new HashMap<K, V>();

		executeQuery(sql, new RowMapper<Object>() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) {
				Entry<K, V> entry = rowMapper.mapRow(rs, rowNum);
				map.put(entry.getKey(), entry.getValue());
				return null;
			}
		});

		return map;
	}

	@Override
	public <T> List<T> queryForList(String sql, final RowMapper<T> rowMapper) {
		
		final List<T> list = new ArrayList<T>();
		
		executeQuery(sql, new RowMapper<Object>() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) {
				
				list.add(rowMapper.mapRow(rs, rowNum));
				
				return null;
			}
		});
		
		return list;
	}
	
	public void executeQuery(String sql, RowMapper<?> rowMapper)
	{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = connectionWrapper.get();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			for(int rowNum=0; rs.next(); rowNum++)
			{
				rowMapper.mapRow(rs, rowNum);
			}
			
		} catch (SQLException e) {
			logger.error("{}", e);
		} finally {
			SqlUtil.close(rs);
			SqlUtil.close(st);
			SqlUtil.close(conn);			
		}
	}

	public ConnectionWrapper getConnectionWrapper() {
		return connectionWrapper;
	}

	public void setConnectionWrapper(ConnectionWrapper connectionWrapper) {
		this.connectionWrapper = connectionWrapper;
	}

}
