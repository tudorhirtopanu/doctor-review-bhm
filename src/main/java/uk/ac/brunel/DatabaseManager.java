package uk.ac.brunel;
import java.sql.*;

public class DatabaseManager {

	String url = "jdbc:sqlite:databases/doctors.db";
	
	public Connection conn() throws SQLException {
		Connection conn =  DriverManager.getConnection(url);
		return conn;
	}
	
}
