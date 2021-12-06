package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class AutobusController  implements Initializable{
	@FXML
	private TableView<Autobus> autobusTable;
	@FXML
	private TableColumn<Autobus, String> idCOL;
	@FXML
	private TableColumn<Autobus, String> gosCOL;
	@FXML
	private TableColumn<Autobus, String> brandCOL;
	@FXML
	private TableColumn<Autobus, String> passengerCOL;
	@FXML
	private Label add;
	@FXML
	private Label rem;
	@FXML
	private Label update;
	@FXML
	private Label revork;

	    String query = null;
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Autobus autobus = null;
	    
	    int index = -1;
	    
	    
	
	    ObservableList<Autobus>  AutobusList = FXCollections.observableArrayList();
	    

	// Event Listener on Label[#add].onMouseClicked
	@FXML
	public void addClick(MouseEvent event) {
		
		 
		 System.out.println("add form!");
			
			
			 try {	
				 
				    AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("addAutobus.fxml"));
		            Scene scene = new Scene(root);
		            Stage stage = new Stage();
		            stage.setScene(scene);
		            stage.initStyle(StageStyle.UTILITY);
		            stage.setResizable(true);
		            stage.show(); 
		            


	            } catch (Exception ex) { 
	                ex.printStackTrace();
	            } 
		
	}
	// Event Listener on Label[#rem].onMouseClicked
	@FXML
	public void remClick(MouseEvent event) {
		
		 System.out.println("rem!");
			
		 
		 Autobus autobus = autobusTable.getSelectionModel().getSelectedItem();
		
		 
		 connection = DbConnect.getConnection();
		 
		    
		 
			try {
				
				if (autobus == null) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Select the line you want to delete!	");
						alert.showAndWait();
					return;
				} 
				
				
				query = "delete from autobus where id = ?";
				
				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(1, autobus.getId());
				
				preparedStatement.executeUpdate();
				
				updateClick();
							

			} catch (SQLException ex) {

				Logger.getLogger(AutobusController.class.getName()).log(Level.SEVERE,null,ex);
				
			}		
	
	}
	
	
 @FXML
 void revorkClick(MouseEvent event) {
 	
 	
 	 System.out.println("revork Click!");
 	
	 Autobus autobus = autobusTable.getSelectionModel().getSelectedItem();
			
			 try {	
				 
				 
				 
					if (autobus == null) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Select the line you want to edit!	");
						alert.showAndWait();
					return;
				} 
				 	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("revorkAutobus.fxml"));
			        Parent root = fxmlLoader.load();
			        
			        // получаем экземпл€р контроллера
			        
			        revorkAutobusController controller = fxmlLoader.getController();
			        
			        // передаем ему выделенный элемент
			        
			        controller.setUserData(autobusTable.getSelectionModel().getSelectedItem());

			        Stage stage = new Stage();
			        stage.initModality(Modality.APPLICATION_MODAL);
			        stage.initStyle(StageStyle.UNIFIED);
			        stage.setTitle("Edit");
			        stage.setScene(new Scene(root));
			        stage.show();
		            

	            } catch (Exception ex) { 
	                ex.printStackTrace();
	            } 
			
 }
 
	// Event Listener on Label[#update].onMouseClicked
	@FXML
	private void updateClick() {
		
		try {
			
			AutobusList.clear();
			
			query = "select * from autobus";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				AutobusList.add(new Autobus(
	                     resultSet.getInt("id"),
	                     resultSet.getString("gov_id"),
	                     resultSet.getString("brand"),
	                     resultSet.getInt("passenger_count")));
					autobusTable.setItems(AutobusList);
				
					
			}
			
			
		} catch (SQLException ex) {
	
			Logger.getLogger(AutobusController.class.getName()).log(Level.SEVERE,null,ex);
			
		}
	
		 System.out.println("upd!");
		
	}	
	
	private void loadDate() {
		
		connection = DbConnect.getConnection();
		
		updateClick();
		
		idCOL.setCellValueFactory(new PropertyValueFactory<Autobus, String>("id"));
		gosCOL.setCellValueFactory(new PropertyValueFactory<Autobus, String>("gov_id"));
		brandCOL.setCellValueFactory(new PropertyValueFactory<Autobus, String>("brand"));
		passengerCOL.setCellValueFactory(new PropertyValueFactory<Autobus, String>("passenger_count"));
		

	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Context.LoggedUser.getRole();
		
		if (Context.LoggedUser.getRole().equals(User.MANAGER_STR))
			
			setManagerViewFunctional();
		
		else
			setUserViewFunctional();

		
		loadDate();
		
	}
	
	   public void setUserViewFunctional() {

	    		
	    		add.setVisible(false);
	    		rem.setVisible(false);
	    		revork.setVisible(false);
	   }
	   
	   public void setManagerViewFunctional() {

		
		   		add.setVisible(true);
		   		rem.setVisible(true);
		   		revork.setVisible(true);
	   }
}
