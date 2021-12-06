  package application;

import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

public class revorkDriversController {
	@FXML
	private TextField phoneFIL;
	@FXML
	private TextField addressFIL;
	@FXML
	private TextField firstNameFIL;
	@FXML
	private TextField middleNameFIL;
	@FXML
	private TextField lastNameFIL;
	@FXML
	private JFXButton saveB;
	@FXML
	private JFXButton cleanB;
	@FXML
	private TextField idFIL;


	    String query = null;
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Driver driver = null;
	    
	    int index = -1;
	

	// Event Listener on JFXButton[#saveB].onMouseClicked
	@FXML
	public void save(MouseEvent event) {
	
		connection = DbConnect.getConnection();
		 
	    
		try {
		
			
			String value1 = idFIL.getText();
			String value2 = firstNameFIL.getText();
			String value3 = middleNameFIL.getText();
			String value4 = lastNameFIL.getText();
			String value5 = addressFIL.getText();
			String value6 = phoneFIL.getText();
			
			
			if(!(value1.matches("^[0-9]+$")) ){
				   
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("ID incorrect!");
				alert.showAndWait();
				
				return;
							
			}
			
			query = "update  driver set id= '"+value1+"',first_name='"+value2+
													  "',middle_name='"+value3+
													  "',last_name='"+value4+
													  "',address='"+value5+
													  "',phone='"+value6+
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
	
	
	public void setUserData(Drivers selectedItem) {
		idFIL.setText(String.valueOf(selectedItem.getId()));
		firstNameFIL.setText(selectedItem.getFirstName());
		middleNameFIL.setText(selectedItem.getMiddleName());
		lastNameFIL.setText(selectedItem.getLastName());
		addressFIL.setText(selectedItem.getAddres());
		phoneFIL.setText(selectedItem.getPhone());

	}
	
	
	
	// Event Listener on JFXButton[#cleanB].onMouseClicked
	@FXML
	public void clean(MouseEvent event) {
		
		
		firstNameFIL.setText(null);
		middleNameFIL.setText(null);
		lastNameFIL.setText(null);
		addressFIL.setText(null);
		phoneFIL.setText(null);
		
	}


	
}
