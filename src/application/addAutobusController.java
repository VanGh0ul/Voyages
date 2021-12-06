package application;

import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

public class addAutobusController {
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
	    ResultSet resultSet = null;
	    PreparedStatement preparedStatement;
	    Autobus autobus = null;
	    private boolean update;
	    int AutobusId;
	
	
	// Event Listener on JFXButton[#saveB].onMouseClicked
	@FXML
	public void save(MouseEvent event) {
		
	connection = DbConnect.getConnection();
		
		String gos = gosFIL.getText();
		String brand = brandFIL.getText();
		String passCount = passCountFIL.getText();
		
		//int passCount = Integer.parseInt(passCountFIL.getText());
		
		if(!(passCount.matches("^[0-9]+$")) ){
		  
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Passenger count incorrect!");
			alert.showAndWait();
			
			return;
						
		}
        
		if(gos.isEmpty() || brand.isEmpty() || passCount.isEmpty() ) {
			
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
	            
	            query = "INSERT INTO `autobus`( `gov_id`, `brand`, `passenger_count`) VALUES (?,?,?)";

	        }else{
	            query = "UPDATE `autobus` SET "
	                    + "`gov_id`=?,"
	                    + "`brand`=?,"
	                    + "`passenger_count`=? WHERE id = '"+AutobusId+"'";
	        }

	    }
	  
	  void setUpdate(boolean b) {
	        this.update = b;

	 }
	  
	  private void insert() {

	        try {
	        	
	            preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setString(1, gosFIL.getText());
	            preparedStatement.setString(2, brandFIL.getText());
	            preparedStatement.setString(3, passCountFIL.getText());

	            preparedStatement.execute();

	        } catch (SQLException ex) {
	            Logger.getLogger(addAutobusController.class.getName()).log(Level.SEVERE, null, ex);
	        }

	    }
	
	
	
	// Event Listener on JFXButton[#cleanB].onMouseClicked
	@FXML
	public void clean() {
		
		gosFIL.setText(null);
		brandFIL.setText(null);
		passCountFIL.setText(null);
		
	}
}
