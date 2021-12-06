package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnect {

 
 		 private static String  databaseHost = "localhost";
 		 private static int databasePort = 3306;
		 private static String  databaseName = "passenger_round";
		 private static	String  databaseUser = "root";
		 private static	String  databasePassword = "6199";

		 private static Connection connection;
		 
		 public static Connection getConnection() {
			 try {
				connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",databaseHost,databasePort,databaseName),databaseUser,databasePassword);
			} catch (SQLException ex) {
				
				Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE,null,ex);
			}
			 
			 return connection;
			 
		 }

}
