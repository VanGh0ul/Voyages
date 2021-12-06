package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
	
	public static final String MANAGER_STR = "Менеджер";
	public static final String USER_STR = "Пользователь";
	
	private static final String COL_ID = "id";
	private static final String COL_LOGIN = "login";
	private static final String COL_PASS = "pass";
	private static final String COL_NAME = "name";
	private static final String COL_ROLE_NAME = "role_name";

	private final int STRING_LENGTH_CONSTR_MAX = 255;
	private final int STRING_LENGTH_CONSTR_MIN = 3;
	
	private int id;
	private String name;
	private String login;
	private String password;
	private String role;

	public User(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}
	
	public User(String name, String login, String password) {
		super();
		this.name = name;
		this.login = login;
		this.password = password;
	}
	

	
	private void validateLogin() throws Exception {
	
		if (login.length() < STRING_LENGTH_CONSTR_MIN || login.length() > STRING_LENGTH_CONSTR_MAX) 
			throw new Exception("Логин должен содержать от 3 до 30 символов");
		
		if (password.length() < STRING_LENGTH_CONSTR_MIN || password.length() > STRING_LENGTH_CONSTR_MAX) 
			throw new Exception("Пароль должен содержать от 3 до 30 символов"); 
	}
	
	private void validateAll() throws Exception {
		
		validateLogin();
		
		if (name.length() < STRING_LENGTH_CONSTR_MIN || name.length() > STRING_LENGTH_CONSTR_MAX) 
			throw new Exception("Имя должно содержать от 3 до 30 символов");
	}
	
	private boolean exists() throws Exception {
		
		Connection conn = Utils.getConnection();
		
		try {
			
			PreparedStatement query = conn.prepareStatement(
				"select id from user where login = ?"
			);
			
			query.setString(1, login);
		
			return query.executeQuery().isBeforeFirst();
		
		} finally {
			conn.close();
		}
	}
	
	
	public void register() throws Exception { 
		
		validateAll();
		
		if (exists())
			throw new Exception("Пользователь с таким логином уже существует");
		
		Connection conn = Utils.getConnection();
		
		try {
			
			PreparedStatement query = conn.prepareStatement(
				"insert into user "
				+ "(name, login, pass, open_pass) "
				+ "values (?, ?, md5(?), ?)"
			);
			
			query.setString(1, name);
			query.setString(2, login);
			query.setString(3, password);
			query.setString(4, password);
			
			query.executeUpdate();
			
		} finally {
			
			conn.close();
		}
	}
	
	public boolean auth() throws Exception {
		
		try {
			
			validateLogin();
			
		} catch (Exception ex) {
			
			throw new Exception ("Неправильный логин или пароль"); 
		}
		
		Connection conn = Utils.getConnection();
		
		try {
			
			PreparedStatement query = conn.prepareStatement(
				"select u.id, u.name, u.login, u.pass, ur.name as role_name "
				+ "from user u inner join user_role ur on u.role_id = ur.id "
				+ "where u.login = ? and u.pass = ?"
			);
			
			query.setString(1, login);
			query.setString(2, Utils.getMd5(password));
			
			ResultSet result = query.executeQuery();
			
			if (result.next()) {
				
				id = result.getInt(COL_ID);
				name = result.getString(COL_NAME);
				login = result.getString(COL_LOGIN);
				password = result.getString(COL_PASS);
				role = result.getString(COL_ROLE_NAME);
				
			} else
				return false;
			
		} finally {
			
			conn.close();
		}

		return true;
	}

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
