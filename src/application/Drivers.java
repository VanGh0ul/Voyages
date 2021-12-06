package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Drivers {
	
	int id;
	String firstName;
	String middleName;
	String lastName;
	String addres;
	String phone;
	
	public Drivers(int id, String firstName, String middleName, String lastName, String addres, String phone) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.addres = addres;
		this.phone = phone;
	}

	public static ObservableList<Drivers> getDrivers() {
	
		ObservableList<Drivers> result = FXCollections.observableArrayList();
		
		Connection connection = DbConnect.getConnection();
		
		try {
			
			
			PreparedStatement preparedStatement = connection.prepareStatement("select * from driver");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
				result.add(new Drivers(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("middle_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("address"),
                    resultSet.getString("phone"))
				);
			
		} catch (SQLException ex) {
	
			Logger.getLogger(DriverController.class.getName()).log(Level.SEVERE,null,ex);
		}
		
		return result;
	}
	

	public int getId(){
	    return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddres() {
		return addres;
	}
	
	public void setAddres(String addres) {
		this.addres = addres;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
