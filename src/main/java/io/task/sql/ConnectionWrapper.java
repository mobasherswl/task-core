package io.task.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionWrapper {

	Connection get() throws SQLException;
}
