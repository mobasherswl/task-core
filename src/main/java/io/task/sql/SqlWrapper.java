package io.task.sql;

import java.util.List;
import java.util.Map;

public interface SqlWrapper {

	<K, V> Map<K, V> queryForMap(String sql, RowMapper<Map.Entry<K, V>> rowMapper);
	
	<T> List<T> queryForList(String sql, RowMapper<T> rowMapper);
}
