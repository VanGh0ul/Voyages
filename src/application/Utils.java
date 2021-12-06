package application;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;

public class Utils {

	private static final String USER = "root";
	private static final String PASSWORD = "6199";
	private static final String DB_NAME = "passenger_round";
	
	// Получение соединения с БД
	// throws Exception
	public static Connection getConnection() throws Exception {
		
		try {
		
			return  DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME, USER, PASSWORD);
			
		} catch (Exception ex) {
			
			throw new Exception("Произошла ошибка при подключении к БД - " + ex.getMessage());
		}
	}
	
	// throws Exception
	public static String getMd5(String str) throws Exception {
		
		byte[] digestedStr = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
		
		StringBuffer sb = new StringBuffer();
		for (byte b : digestedStr)
			sb.append(String.format("%02x", b & 0xff));
		
		return sb.toString();
	}
}
