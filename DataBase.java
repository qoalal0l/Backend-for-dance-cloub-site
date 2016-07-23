package ua.dp.renessans.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataBase {

	private static DataSource dataSource;

	public static Connection getConnection() throws NamingException, SQLException {
		if (dataSource == null) {
			dataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/renessansdb");
		}
		return dataSource.getConnection();
	}

}
