package application;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import application.DbConnect;
import application.Drivers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParsePosition;

import com.jfoenix.controls.JFXButton;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

public class addDriversController  implements Initializable {
	@FXML
    private TextField addressFIL;

    @FXML
    private JFXButton cleanB;

    @FXML
    private TextField firstNameFIL;

    @FXML
    private TextField lastNameFIL;

    @FXML
    private TextField middleNameFIL;

    @FXML
    private TextField phoneFIL;

    @FXML
    private JFXButton saveB;
    
    
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Drivers drivers = null;
    private boolean update;
    int DriversId;
    
    
	// Event Listener on JFXButton.onMouseClicked
	@FXML
	public void save(MouseEvent event) {
	
		connection = DbConnect.getConnection();
		
		String firstName = firstNameFIL.getText();
		String middleName = middleNameFIL.getText();
		String lastName = lastNameFIL.getText();
		String address = addressFIL.getText();
		String phone = phoneFIL.getText();
		
		
		if(!Pattern.matches("^\\+7(\\s?\\d{3}){2}(\\s?\\d\\d){2}$", phone)) {
		 		   
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Time in round incorrect format: +7 999 999 99 99");
				alert.showAndWait();
				
				return;		

			}
		
	
		
		
		if(firstName.isEmpty() || middleName.isEmpty() || lastName.isEmpty() || address.isEmpty() || phone.isEmpty()) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All Data");
			alert.showAndWait();
			
		}else {
			
			    getQuery();
			    
			    insert();
			    
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Save succsesful!");
				alert.showAndWait();
			    
	            clean();
		}
		
	}
	
	  private void getQuery() {

	        if (update == false) {
	            
	            query = "INSERT INTO `driver`( `first_name`, `middle_name`, `last_name`, `address`, `phone`) VALUES (?,?,?,?,?)";

	        }else{
	            query = "UPDATE `driver` SET "
	                    + "`first_name`=?,"
	                    + "`middle_name`=?,"
	                    + "`last_name`=?,"
	                    + "`address`=?,"
	                    + "`phone`= ? WHERE id = '"+DriversId+"'";
	        }
	        
	        
	        

	    }
	  
	 
	void setUpdate(boolean b) {
	        this.update = b;

	 }
	

	
    private void insert() {

        try {
        	
        	
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstNameFIL.getText());
            preparedStatement.setString(2, middleNameFIL.getText());
            preparedStatement.setString(3, lastNameFIL.getText());
            preparedStatement.setString(4, addressFIL.getText());
            preparedStatement.setString(5, phoneFIL.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(addDriversController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
	
	// Event Listener on JFXButton.onMouseClicked
	@FXML
	public void clean() {
		
		firstNameFIL.setText(null);
		middleNameFIL.setText(null);
		lastNameFIL.setText(null);
		addressFIL.setText(null);
		phoneFIL.setText(null);
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
}
