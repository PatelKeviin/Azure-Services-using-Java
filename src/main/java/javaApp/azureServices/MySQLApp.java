package javaApp.azureServices;

import java.sql.*;
import java.util.Properties;

public class MySQLApp {
	private String host = null;
	private String database = null;
	private String user = null;
	private String password = null;
	
	public MySQLApp(String _host, String _database, String _user, String _password) {
		host = _host;
		database = _database;
		user = _user;
		password = _password;
	}
	
	public Connection initConnection() throws ClassNotFoundException, SQLException {
		// check that the driver is installed
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("MySQL JDBC driver NOT detected in library path.", e);
		}

		System.out.println("MySQL JDBC driver detected in library path.");

		Connection connection = null;

		// Initialize connection object
		try {
			String url = String.format("jdbc:mysql://%s/%s", host, database);

			// Set connection properties.
			Properties properties = new Properties();
			properties.setProperty("user", user);
			properties.setProperty("password", password);
			properties.setProperty("useSSL", "true");
			properties.setProperty("verifyServerCertificate", "true");
			properties.setProperty("requireSSL", "false");
			properties.setProperty("useTimezone", "true");
			properties.setProperty("useLegacyDatetimeCode", "false");
			properties.setProperty("serverTimezone", "UTC");

			// get connection
			connection = DriverManager.getConnection(url, properties);

		} catch (SQLException e) {
			throw new SQLException("Failed to create connection to database.", e);
		}

		return connection;
	}
	
	public void createTable(Connection connection) throws SQLException {
		if (connection != null) {

			// Perform some SQL queries over the connection.
			try {
				// Drop previous table of same name if one exists.
				Statement statement = connection.createStatement();
				statement.execute("DROP TABLE IF EXISTS wordcount;");
				System.out.println("Finished dropping table (if existed).");

				// Create table.
				statement
						.execute("CREATE TABLE wordcount (id serial PRIMARY KEY, word VARCHAR(50), count INTEGER);");
				System.out.println("Created table.");
			
			} catch (SQLException e) {
				throw new SQLException("Encountered an error when executing given sql statement.", e);
			}
		}
	}
	
	public void insertRow(Connection connection, String key, String value) throws SQLException {
		if (connection != null) {

			// Perform some SQL queries over the connection.
			try {
				System.out.println(key + " " + value);
				PreparedStatement preparedStatement = connection
						.prepareStatement("INSERT INTO wordcount (word, count) VALUES (?, ?);");
				preparedStatement.setString(1, key);
				preparedStatement.setInt(2, Integer.parseInt(value));
				preparedStatement.executeUpdate();
			
			} catch (SQLException e) {
				throw new SQLException("Encountered an error when executing given sql statement.", e);
			}
		}
	}
	
	public ResultSet readTable(Connection connection) throws SQLException {
		if (connection != null) {
			// Perform some SQL queries over the connection.
			try {
				Statement statement = connection.createStatement();
				ResultSet results = statement.executeQuery("SELECT * from wordcount;");
				
				return results;
			
			} catch (SQLException e) {
				throw new SQLException("Encountered an error when executing given sql statement.", e);
			}
		}
		return null;
	}
}
