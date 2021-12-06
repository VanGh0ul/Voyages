package application;

import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

public class revorkRoundController {
	@FXML
	private TextField timeInWayFIL;
	@FXML
	private TextField nameFIL;
	@FXML
	private TextField startLocationFIL;
	@FXML
	private TextField endLocationFIL;
	@FXML
	private JFXButton saveB;
	@FXML
	private JFXButton cleanB;
	@FXML
	private TextField idFIL;
	
	String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Round round = null;
    private boolean update;
    int RoundId;

	// Event Listener on JFXButton[#saveB].onMouseClicked
	@FXML
	public void save(MouseEvent event) {

		connection = DbConnect.getConnection();
		 
		try {
		
			String value1 = idFIL.getText();
			String value2 = nameFIL.getText();
			String value3 = startLocationFIL.getText();
			String value4 = endLocationFIL.getText();
			String value5 = timeInWayFIL.getText();

			
			if(!(value1.matches("^[0-9]+$")) ){
				   
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Passenger count incorrect!");
				alert.showAndWait();
				
				return;
							
			}

			if ( !(Pattern.matches("^[1-9]?[1-9]?\\d:[0-5]\\d$" , value5) ) ){
		 		   
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Time in round incorrect format: 999:59");
				alert.showAndWait();
				
				return;				
			}
			
			query = "update  round set id= '"+value1+"',name='"+value2+
													  "',start_location='"+value3+
													  "',end_location='"+value4+
													  "',time_in_way='"+value5+
										"' where id='"+value1+"' ";
			
			preparedStatement = connection.prepareStatement(query);		
			
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			
			Logger.getLogger(DriverController.class.getName()).log(Level.SEVERE,null,ex);
			
		}
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Save succsesful!");
		alert.showAndWait();
		
	}
	// Event Listener on JFXButton[#cleanB].onMouseClicked
	@FXML
	public void clean(MouseEvent event) {
		
		nameFIL.setText(null);
		startLocationFIL.setText(null);
		endLocationFIL.setText(null);
		timeInWayFIL.setText(null);

	}

	public void setUserData(Round selectedItem) {
		idFIL.setText(String.valueOf(selectedItem.getId()));
		nameFIL.setText(selectedItem.getName());
		startLocationFIL.setText(selectedItem.getStart_location());
		endLocationFIL.setText(selectedItem.getEnd_location());
		timeInWayFIL.setText(selectedItem.getTime_in_way());

	}

	
}
