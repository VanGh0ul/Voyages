package application;

import javafx.fxml.FXML;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

public class addRoundController {
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
	
	
	String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Round round = null;
    private boolean update;
    int RoundId;
	

 // Event Listener on JFXButton.onMouseClicked
 	@FXML
 	public void save(MouseEvent event) {
 	
 		connection = DbConnect.getConnection();
 		
 		String name = nameFIL.getText();
 		String startLocation = startLocationFIL.getText();
 		String endLocation = endLocationFIL.getText();
 		String timeInWay = timeInWayFIL.getText();
 
 		if ( !(Pattern.matches("^[1-9]?[1-9]?\\d:[0-5]\\d$" , timeInWay) ) ){
 		   
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Time in round incorrect format: 999:59");
			alert.showAndWait();
			
			return;				
		}
 		
 		if(name.isEmpty() || startLocation.isEmpty() || endLocation.isEmpty() || timeInWay.isEmpty()){
 			
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
 	            
 	            query = "INSERT INTO `round`( `name`, `start_location`, `end_location`, `time_in_way`) VALUES (?,?,?,?)";

 	        }else{
 	            query = "UPDATE `round` SET "
 	                    + "`name`=?,"
 	                    + "`start_location`=?,"
 	                    + "`end_location`=?,"
 	                    + "`time_in_way`=? WHERE id = '"+RoundId+"'";
 	        }
 	        

 	    }
 	  
 	 
 	void setUpdate(boolean b) {
 	        this.update = b;

 	 }
 	

 	
     private void insert() {

         try {
         	
             preparedStatement = connection.prepareStatement(query);
             preparedStatement.setString(1, nameFIL.getText());
             preparedStatement.setString(2, startLocationFIL.getText());
             preparedStatement.setString(3, endLocationFIL.getText());
             preparedStatement.setString(4, timeInWayFIL.getText());

             preparedStatement.execute();

         } catch (SQLException ex) {
             Logger.getLogger(addDriversController.class.getName()).log(Level.SEVERE, null, ex);
         }

     }
 	
 	// Event Listener on JFXButton.onMouseClicked
 	@FXML
 	public void clean() {
 		
 		nameFIL.setText(null);
 		startLocationFIL.setText(null);
 		endLocationFIL.setText(null);
 		timeInWayFIL.setText(null);
 		
 	}
 	
}
