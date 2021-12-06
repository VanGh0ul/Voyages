package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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

public class VoyageController  implements Initializable{
	@FXML
	private TableView<Voyage> voyageTable;
	@FXML
	private TableColumn<Voyage, String> idCOL;
	@FXML
	private TableColumn<Voyage, String> dataCOL;
	@FXML
	private TableColumn<Voyage, String> startCOL;
	@FXML
	private TableColumn<Voyage, String> endCOL;
	@FXML
	private TableColumn<Voyage, String> roundCOL;
	@FXML
	private TableColumn<Voyage, String> driverCOL;
	@FXML
	private TableColumn<Voyage, String> autobusCOL;
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
	    Voyage voyage = null;
	 
	    
	    ObservableList<Voyage>  VoyageList = FXCollections.observableArrayList();

		// Event Listener on Label[#update].onMouseClicked
		@FXML
		private void updateClick() {
			
			try {
				
				VoyageList.clear();
				
				query = (
						"select "
						+ "	v.id, "
						+ " v.voyage_date, "
						+ " v.round_id, "
						+ " v.driver_id, "
						+ " v.autobus_id, "
						+ " v.start_time, "
						+ " v.end_time, "
						+ " r.name as round_name, "
						+ " concat_ws(' ', d.first_name, d.middle_name, d.last_name) as driver_name, "
						+ " concat_ws(' - ', a.brand, a.gov_id) as autobus_name "
						+ "from "
						+ "	voyage v "
						+ "  inner join driver d on v.driver_id = d.id "
						+ "  inner join round r on v.round_id = r.id "
						+ "  inner join autobus a on v.autobus_id = a.id ");
				
				
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					VoyageList.add(new Voyage(
	                        resultSet.getInt("id"),
	                        
	                        resultSet.getDate("voyage_date"),
	                        resultSet.getTime("start_time"),
	                        resultSet.getTime("end_time"),
	                        
	                        resultSet.getInt("round_id"),
	                        resultSet.getString("round_name"),
	                        
	                        resultSet.getInt("driver_id"),
	                        resultSet.getString("driver_name"),
	                        
	                        resultSet.getInt("autobus_id"),
	                        resultSet.getString("autobus_name")));
				}	
				
				voyageTable.setItems(VoyageList);
				
			} catch (SQLException ex) {
		
				Logger.getLogger(DriverController.class.getName()).log(Level.SEVERE,null,ex);
				
			}
		
			 System.out.println("upd!");
			
		}
		
		private void loadDate() {
			
			connection = DbConnect.getConnection();
			
			updateClick();
			
			idCOL.setCellValueFactory(new PropertyValueFactory<Voyage, String>("id"));
			dataCOL.setCellValueFactory(new PropertyValueFactory<Voyage, String>("date"));
			startCOL.setCellValueFactory(new PropertyValueFactory<Voyage, String>("startTime"));
			endCOL.setCellValueFactory(new PropertyValueFactory<Voyage, String>("endTime"));
			roundCOL.setCellValueFactory(new PropertyValueFactory<Voyage, String>("round"));
			driverCOL.setCellValueFactory(new PropertyValueFactory<Voyage, String>("driver"));
			autobusCOL.setCellValueFactory(new PropertyValueFactory<Voyage, String>("autobus"));

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
	
	// Event Listener on Label[#add].onMouseClicked
	@FXML
	public void addClick(MouseEvent event) {
			
			 try {	
				 
				    AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("addVoyage.fxml"));
		            Scene scene = new Scene(root);
		            Stage stage = new Stage();
		            stage.setScene(scene);
		            stage.initStyle(StageStyle.UTILITY);
		            stage.setResizable(true);
		            stage.show(); 
		            
		            System.out.println("add form!");
		            
	            } catch (Exception ex) { 
	                ex.printStackTrace();
	            } 
		
	}
	
	// Event Listener on Label[#rem].onMouseClicked
	@FXML
	public void remClick(MouseEvent event) {
		

		 System.out.println("rem!");
			
		 
		Voyage voyage = voyageTable.getSelectionModel().getSelectedItem();
		
		 
		 connection = DbConnect.getConnection();
		 
			try {
				
				if (voyage == null) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Select the line you want to delete!	");
						alert.showAndWait();
					return;
				} 
			
				query = "delete from voyage where id = ?";
				
				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(1, voyage.getId());
				
				preparedStatement.executeUpdate();
				
				updateClick();
							

			} catch (SQLException ex) {

				Logger.getLogger(DriverController.class.getName()).log(Level.SEVERE,null,ex);
				
			}	
        
		
		
	}
	
	// Event Listener on Label[#update].onMouseClicked
	@FXML
	
	public void updateClick(MouseEvent event) {
		
		updateClick();
		
	}
	
	// Event Listener on Label[#revork].onMouseClicked
	@FXML
	public void revorkClick(MouseEvent event) {
		
   	 System.out.println("revork Click!");
			
   		Voyage voyage = voyageTable.getSelectionModel().getSelectedItem();
			
			 try {	
					if (voyage == null) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Select the line you want to edit!	");
						alert.showAndWait();
					return;
				} 
				 	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("revorkVoyage.fxml"));
			        Parent root = fxmlLoader.load();
			        
			        // получаем экземпл€р контроллера
			        
			        revorkVoyage controller = fxmlLoader.getController();
			        
			        // передаем ему выделенный элемент
			        
			        controller.setUserData(voyageTable.getSelectionModel().getSelectedItem());

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
}
