package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Autobus {

	public Autobus(int id, String gov_id, String brand, int passenger_count) {
		super();
		this.id = id;
		this.gov_id = gov_id;
		this.brand = brand;
		this.passenger_count = passenger_count;
	}
	

	int id;
	String gov_id;
	String brand;
	int passenger_count;
	
	
	
	public static ObservableList<Autobus> getAutobus() {
		
		ObservableList<Autobus> result = FXCollections.observableArrayList();
		
		Connection connection = DbConnect.getConnection();
		
		try {
			
			
			PreparedStatement preparedStatement = connection.prepareStatement("select * from autobus");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
				result.add(new Autobus(
	                     resultSet.getInt("id"),
	                     resultSet.getString("gov_id"),
	                     resultSet.getString("brand"),
	                     resultSet.getInt("passenger_count")));
			
			
		} catch (SQLException ex) {
	
			Logger.getLogger(DriverController.class.getName()).log(Level.SEVERE,null,ex);
		}
		
		return result;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGov_id() {
		return gov_id;
	}
	public void setGov_id(String gov_id) {
		this.gov_id = gov_id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getPassenger_count() {
		return passenger_count;
	}
	public void setPassenger_count(int passenger_count) {
		this.passenger_count = passenger_count;
	}

	
}
