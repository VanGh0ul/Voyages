package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseConnectionUsers {
	
	public Connection databaseLink;

	

	public Connection getConnectionUser() {
		
		String  databaseName = "passenger_round";
		String  databaseUser = "root";
		String  databasePassword = "6199";
		String  url = "jdbc:mysql://localhost/" + databaseName;
		
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			
			databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
		
		}catch(Exception e) {
			
			e.printStackTrace();
			e.getCause();
			
		}
		
		return databaseLink;
	}
	
}
