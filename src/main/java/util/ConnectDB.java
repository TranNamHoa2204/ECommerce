package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private static final String username = "sa";
	private static final String password = "sapassword";
	private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHangOnline;encrypt=true;trustServerCertificate=true";
	private static Connection connection = null;
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	// Sau này chỉ cần: Connection con = DBConnection.getConnection();
}
