package application;

import java.net.URL;
import java.sql.Connection;
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

public class RoundController implements Initializable{
	@FXML
	private TableView<Round> roundTable;
	@FXML
	private TableColumn<Round, String> idCOL;
	@FXML
	private TableColumn<Round, String> startLocationCOL;
	@FXML
	private TableColumn<Round, String> nameCOL;
	@FXML
	private TableColumn<Round, String> endLocationCOL;
	@FXML
	private TableColumn<Round, String> timeInWayCOL;
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
	    Round round = null;

	    ObservableList<Round>  RoundList = FXCollections.observableArrayList();
	    
	// Event Listener on Label[#add].onMouseClicked
	@FXML
	public void addClick(MouseEvent event) {
		
		 System.out.println("add form!");
		 try {	
			 
			    AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("addRound.fxml"));
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
			
		 
		 Round Round = roundTable.getSelectionModel().getSelectedItem();
		
		 
		 connection = DbConnect.getConnection();
		 
		    
		 
			try {
				
				if (round == null) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Select the line you want to delete!	");
						alert.showAndWait();
					return;
				} 
				
				
				query = "delete from round where id = ?";
				
				preparedStatement = connection.prepareStatement(query);
			

				preparedStatement.setInt(1, round.getId());
				
				preparedStatement.executeUpdate();
				
				updateClick();
							

			} catch (SQLException ex) {

				Logger.getLogger(RoundController.class.getName()).log(Level.SEVERE,null,ex);
				
			}		
			
	}
	// Event Listener on Label[#update].onMouseClicked
	@FXML
	public void updateClick() {
		
		
		
	try {
			
			RoundList.clear();
			
			query = "select * from round";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				RoundList.add(new Round(
	                     resultSet.getInt("id"),
	                     resultSet.getString("name"),
	                     resultSet.getString("start_location"),
	                     resultSet.getString("end_location"),
	                     resultSet.getString("time_in_way")));
					roundTable.setItems(RoundList);
						
			}
			
			
		} catch (SQLException ex) {
	
			Logger.getLogger(RoundController.class.getName()).log(Level.SEVERE,null,ex);
			
		}
	
		 System.out.println("upd!");
		
		
		
	}
	// Event Listener on Label[#revork].onMouseClicked
	@FXML
	public void revorkClick(MouseEvent event) {
		

	 	
	 	 System.out.println("revork Click!");
	 	
		 Round round = roundTable.getSelectionModel().getSelectedItem();
				
				 try {	
					 
					 
					 
						if (round == null) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setHeaderText(null);
							alert.setContentText("Select the line you want to edit!	");
							alert.showAndWait();
						return;
					} 
					 	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("revorkRound.fxml"));
				        Parent root = fxmlLoader.load();
				        
				        // ???????? ????????? ???????????
				        
				        revorkRoundController controller = fxmlLoader.getController();
				        
				        // ???????? ??? ?????????? ???????
				        
				        controller.setUserData(roundTable.getSelectionModel().getSelectedItem());

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
	
	
	private void loadDate() {
		
		connection = DbConnect.getConnection();
		
		updateClick();
		
		idCOL.setCellValueFactory(new PropertyValueFactory<Round, String>("id"));
		nameCOL.setCellValueFactory(new PropertyValueFactory<Round, String>("name"));
		startLocationCOL.setCellValueFactory(new PropertyValueFactory<Round, String>("start_location"));
		endLocationCOL.setCellValueFactory(new PropertyValueFactory<Round, String>("end_location"));
		timeInWayCOL.setCellValueFactory(new PropertyValueFactory<Round, String>("time_in_way"));

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
