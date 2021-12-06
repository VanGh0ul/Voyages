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
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class DriverController implements Initializable{
	
	  

	    @FXML
	    public TableView<Drivers> driversTable;

	    @FXML
	    public TableColumn<Drivers, String> idCOL;
	    
	    @FXML
	    public TableColumn<Drivers, String> firstNameCOL;

	    @FXML
	    public TableColumn<Drivers, String> lastNameCOL;

	    @FXML
	    public TableColumn<Drivers, String> middleNameCOL;
	    
	    @FXML
	    public TableColumn<Drivers, String> addresCOL;

	    @FXML
	    public TableColumn<Drivers, String> phoneCOL;

	    @FXML
	    private Label rem;
	    
		@FXML
	    private Label add;
	    
	    @FXML
	    private Label revork;

	    @FXML
	    private Label update;
	    
	    String query = null;
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Driver driver = null;
	    
	    int index = -1;
	   
	    
	
	    ObservableList<Drivers>  DriverList = FXCollections.observableArrayList();
	    

	// Event Listener on Label[#add].onMouseClicked
	@FXML
	public void addClick(MouseEvent event) {
		
		 
		 System.out.println("add form!");
			
			//Node source = (Node)event.getSource();
			//Stage stage = (Stage)source.getScene().getWindow();
			
			 try {	
				 
				    AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("addDrivers.fxml"));
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
			
		 
		Drivers driver = driversTable.getSelectionModel().getSelectedItem();
		
		 
		 connection = DbConnect.getConnection();
		 
		    
		
			try {
				
				if (driver == null) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Select the line you want to delete!	");
						alert.showAndWait();
					return;
				} 
				
				
				query = "delete from driver where id = ?";
				
				preparedStatement = connection.prepareStatement(query);
			

				preparedStatement.setInt(1, driver.getId());
				
				preparedStatement.executeUpdate();
				
				updateClick();
							

			} catch (SQLException ex) {

				Logger.getLogger(DriverController.class.getName()).log(Level.SEVERE,null,ex);
				
			}		
	
	}
	
	
    @FXML
    void revorkClick(MouseEvent event) {
    	
    	
    	 System.out.println("revork Click!");
			
    		Drivers driver = driversTable.getSelectionModel().getSelectedItem();
			
			 try {	
				 
				 
				 
					if (driver == null) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Select the line you want to edit!	");
						alert.showAndWait();
					return;
				} 
				 	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("revorkDrivers.fxml"));
			        Parent root = fxmlLoader.load();
			        
			        // получаем экземпл€р контроллера
			        
			        revorkDriversController controller = fxmlLoader.getController();
			        
			        // передаем ему выделенный элемент
			        
			        controller.setUserData(driversTable.getSelectionModel().getSelectedItem());

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
			
			DriverList.clear();
			
			query = "select * from driver";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				DriverList.add(new Drivers(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone")));
				driversTable.setItems(DriverList);
			
						
			}

			
		} catch (SQLException ex) {
	
			Logger.getLogger(DriverController.class.getName()).log(Level.SEVERE,null,ex);
			
		}
	
		 System.out.println("upd!");
		
	}
	

	
	private void loadDate() {
		
		connection = DbConnect.getConnection();
		
		updateClick();
		
		idCOL.setCellValueFactory(new PropertyValueFactory<Drivers, String>("id"));
		firstNameCOL.setCellValueFactory(new PropertyValueFactory<Drivers, String>("firstName"));
		middleNameCOL.setCellValueFactory(new PropertyValueFactory<Drivers, String>("middleName"));
		lastNameCOL.setCellValueFactory(new PropertyValueFactory<Drivers, String>("lastName"));
		addresCOL.setCellValueFactory(new PropertyValueFactory<Drivers, String>("addres"));
		phoneCOL.setCellValueFactory(new PropertyValueFactory<Drivers, String>("phone"));
		
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
