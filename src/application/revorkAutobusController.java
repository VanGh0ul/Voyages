package application;

import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

public class revorkAutobusController {
	@FXML
	private TextField idFIL;
	@FXML
	private TextField gosFIL;
	@FXML
	private TextField brandFIL;
	@FXML
	private TextField passCountFIL;
	@FXML
	private JFXButton saveB;
	@FXML
	private JFXButton cleanB;
	
	
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Autobus autobus = null;

	// Event Listener on JFXButton[#saveB].onMouseClicked
	@FXML
	public void save(MouseEvent event) {
		
connection = DbConnect.getConnection();
		 
	    
		try {
		
			
			String value1 = idFIL.getText();
			String value2 = gosFIL.getText();
			String value3 = brandFIL.getText();
			String value4 = passCountFIL.getText();
			
			if(!(value1.matches("^[0-9]+$")) ){
				   
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("id incorrect!");
				alert.showAndWait();
				
				return;
							
			}
			
			if(!(value4.matches("^[0-9]+$")) ){
				   
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Passenger count incorrect!");
				alert.showAndWait();
				
				return;
							
			}
			
			
			query = "update  autobus set id= '"+value1+"',gov_id='"+value2+
													  "',brand='"+value3+
													  "',passenger_count='"+value4+

										"' where id='"+value1+"' ";
			
			preparedStatement = connection.prepareStatement(query);		
			
			preparedStatement.executeUpdate();
			
			
		

		} catch (SQLException ex) {
			
			Logger.getLogger(AutobusController.class.getName()).log(Level.SEVERE,null,ex);
			
		}
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Save succsesful!");
		alert.showAndWait();
		
	}
	
	public void setUserData(Autobus selectedItem) {
		idFIL.setText(String.valueOf(selectedItem.getId()));
		gosFIL.setText(selectedItem.getGov_id());
		brandFIL.setText(selectedItem.getBrand());
		passCountFIL.setText(String.valueOf(selectedItem.getPassenger_count()));

		
	}
	
	
	// Event Listener on JFXButton[#cleanB].onMouseClicked
	@FXML
	public void clean(MouseEvent event) {
		
		gosFIL.setText(null);
		brandFIL.setText(null);
		passCountFIL.setText(null);
		
	}
}
