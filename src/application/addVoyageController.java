package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import application.Drivers;
import application.Round;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class addVoyageController {


	    @FXML
	    private JFXComboBox<Autobus> autobusCB;

	    @FXML
	    private JFXButton cleanB;

	    @FXML
	    private DatePicker dataFIL;

	    @FXML
	    private JFXComboBox<Drivers> driverCB;

	    @FXML
	    private TextField endTime;

	    @FXML
	    private JFXComboBox<Round> roundCB;

	    @FXML
	    private JFXButton saveB;

	    @FXML
	    private TextField startLocationFIL;


	    String query = null;
	    Connection connection = null;
	    ResultSet resultSet = null;
	    PreparedStatement preparedStatement;
	    Voyage voyage = null;
	    private boolean update;
	    int VoyageId;
	    
	    // Списки комбобоксов
	    ObservableList<Drivers> drivers;
	    ObservableList<Autobus> autobuses;
	    ObservableList<Round> round;
	    
	    @FXML
	    public void initialize() {
	    	
	    	try {
	    		
	    		startLocationFIL.setText("00:00:00");
	    		endTime.setText("00:00:00");
	    		
	    		// Заполнение списков данными из БД
		    	drivers = Drivers.getDrivers();
		    	autobuses = Autobus.getAutobus();
		    	round = Round.getRounds();
		    	
		    	// Привязка списков к комбобоксам
		    	driverCB.setItems(drivers);
		    	autobusCB.setItems(autobuses);
		    	roundCB.setItems(round);
		    	
		    	
		    	// Фабрика отображения на основе объекта
		    	Callback<ListView<Drivers>, ListCell<Drivers>> driverFactory = new Callback<ListView<Drivers>, ListCell<Drivers>>() {
		    		
					@Override
					public ListCell<Drivers> call(ListView<Drivers> arg) {
						
						return new ListCell<Drivers>() {
							
							@Override
							protected void updateItem(Drivers driver, boolean empty) {
								
								super.updateItem(driver, empty);
								
								if (driver == null || empty)
									setText("");
								
								else
									setText(String.format("%s %s %s %s", driver.getFirstName(), driver.getMiddleName(), driver.getLastName(), driver.getPhone()));
							}
						};
					}
		    	};
		    	
		    	// Строки в расскрывающемся списке
		    	driverCB.setCellFactory(driverFactory);
		    	
		    	// Строка при свернутом комбобоксе
		    	driverCB.setButtonCell(driverFactory.call(null));
		    	
		    	
		    	Callback<ListView<Autobus>, ListCell<Autobus>> autobusFactory = new Callback<ListView<Autobus>, ListCell<Autobus>>() {

					@Override
					public ListCell<Autobus> call(ListView<Autobus> arg) {
						
						return new ListCell<Autobus>() {
							
							@Override
							protected void updateItem(Autobus autobus, boolean empty) {
								
								super.updateItem(autobus, empty);
								
								if (autobus == null || empty)
									setText("");
								
								else
									setText(String.format("%s - %s", autobus.getBrand(), autobus.getGov_id()));
							}
						};
					}
		    	};
		    	
		    	autobusCB.setCellFactory(autobusFactory);
		    	autobusCB.setButtonCell(autobusFactory.call(null));
		    	
		    	Callback<ListView<Round>, ListCell<Round>> routeFactory = new Callback<ListView<Round>, ListCell<Round>>() {

					@Override
					public ListCell<Round> call(ListView<Round> arg) {
						
						return new ListCell<Round>() {
							
							@Override
							protected void updateItem(Round route, boolean empty) {
							
								super.updateItem(route, empty);
								
								if (route == null || empty)
									setText("");
								else
									setText(String.format("%s : %s -> %s", route.getName(), route.getStart_location(), route.getEnd_location()));
							}
						};
					}
		    	};
		    	
		    	roundCB.setCellFactory(routeFactory);
		    	roundCB.setButtonCell(routeFactory.call(null));
	    	
	    	} catch (Exception ex) {
	    		
	    		Alert alert = new Alert(AlertType.CONFIRMATION);
	    		alert.setHeaderText("Wrong load list");
	    		alert.show();
	    	}
	    	
	    }
	    
	    
	    @FXML
	    void clean() {
	    	
	    	startLocationFIL.setText(null);
			endTime.setText(null);
			
	    	dataFIL.getEditor().clear();
	    	
			autobusCB.valueProperty().set(null);
			driverCB.valueProperty().set(null);
			roundCB.valueProperty().set(null);
	    	
	    }
	    
	    @FXML
	    void save(MouseEvent event) {

			connection = DbConnect.getConnection();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			
			LocalDate localDate = null;
			Date date = null;
			
			try {
				
				 localDate = dataFIL.getValue();
				
			} catch (Exception ex) {
				
				alert.setHeaderText(null);
				alert.setContentText("Invalid date");
				alert.showAndWait();
			}
			
			if (localDate == null) {
				
				alert.setHeaderText(null);
				alert.setContentText("Date is null");
				alert.showAndWait();
			} else
				date = Date.valueOf(localDate);
			
			String phone = startLocationFIL.getText();
			
			String endtime = endTime.getText();
		
			Round round = roundCB.getSelectionModel().getSelectedItem();
			
			Drivers driver = driverCB.getSelectionModel().getSelectedItem();
			
			Autobus autobus = autobusCB.getSelectionModel().getSelectedItem();

			if( driver == null || autobus == null || round == null) {
				
				alert.setHeaderText(null);
				alert.setContentText("Please Fill All Data");
				alert.showAndWait();
				return;
				
			}else {
				
				    getQuery();
				    
				    insert();
				    
				    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
					alert1.setHeaderText(null);
					alert1.setContentText("Save succsesful!");
					alert1.showAndWait();
				    
		            clean();
			}
			
		}
	    
		  private void getQuery() {

		        if (update == false) {
		            
		            query = "insert into voyage (voyage_date, start_time, end_time, round_id, driver_id, autobus_id)  VALUES (?, ?, ?, ?, ?, ?)";
		        }
		    }
		  
			void setUpdate(boolean b) {
		        this.update = b;

		 }
		    private void insert() {

		        try {
		        	
		            preparedStatement = connection.prepareStatement(query);
		            
		            preparedStatement.setDate(1, Date.valueOf(dataFIL.getValue()));
		            
		            try {
		            preparedStatement.setTime(2, Time.valueOf(endTime.getText()));
		            
		            
		            } catch (SQLException ex) {
		            	
		            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Error insert!");
						alert.showAndWait();
						
			            Logger.getLogger(addDriversController.class.getName()).log(Level.SEVERE, null, ex);
			            return;
			        }

		            try {
			            preparedStatement.setTime(3, Time.valueOf(startLocationFIL.getText()));
			            
			            } catch (SQLException ex) {
			            	
			            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setHeaderText(null);
							alert.setContentText("Error insert!");
							alert.showAndWait();
							
				            Logger.getLogger(addDriversController.class.getName()).log(Level.SEVERE, null, ex);
				            return;
				        }
		            
		            preparedStatement.setInt(4, roundCB.getSelectionModel().getSelectedItem().getId());
		            
		            preparedStatement.setInt(5, driverCB.getSelectionModel().getSelectedItem().getId());
		            
		            preparedStatement.setInt(6, autobusCB.getSelectionModel().getSelectedItem().getId());
		            
		            preparedStatement.execute();

		        } catch (SQLException ex) {
		            Logger.getLogger(addDriversController.class.getName()).log(Level.SEVERE, null, ex);
		        }

		    }

  }
	    
	    
	    


