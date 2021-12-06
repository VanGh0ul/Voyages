package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Round {
	
	public Round(int id, String name, String start_location, String end_location, String time_in_way) {
		super();
		this.id = id;
		this.name = name;
		this.start_location = start_location;
		this.end_location = end_location;
		this.time_in_way = time_in_way;
	}
	int id;
	String name;
	String start_location;
	String end_location;
	String time_in_way;
	
	
	
	public static ObservableList<Round> getRounds() {
		
		ObservableList<Round> result = FXCollections.observableArrayList();
		
		Connection connection = DbConnect.getConnection();
		
		try {
			
			
			PreparedStatement preparedStatement = connection.prepareStatement("select * from round");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
				result.add(new Round(
	                     resultSet.getInt("id"),
	                     resultSet.getString("name"),
	                     resultSet.getString("start_location"),
	                     resultSet.getString("end_location"),
	                     resultSet.getString("time_in_way"))
						);
			
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStart_location() {
		return start_location;
	}
	public void setStart_location(String start_location) {
		this.start_location = start_location;
	}
	public String getEnd_location() {
		return end_location;
	}
	public void setEnd_location(String end_location) {
		this.end_location = end_location;
	}
	public String getTime_in_way() {
		return time_in_way;
	}
	public void setTime_in_way(String time_in_way) {
		this.time_in_way = time_in_way;
	}

}
