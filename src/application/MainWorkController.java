package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;

public class MainWorkController implements Initializable {
	
	@FXML
	private Button round;
	@FXML
	private Button driver;
	@FXML
	private Button autobus;
	@FXML
	private Button voyage;
    @FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	@FXML
	private Circle c1;
	@FXML
	private Circle c2;
	@FXML
	private Circle c3;
    
	@FXML
	private Label exit;
	
	@FXML
	private StackPane contentArea;
	
	@Override
	
	public void initialize(URL url, ResourceBundle rb) {
	
		exit.setOnMouseClicked(e->{System.exit(0);});
		
		setRotate(c1,true,360,10);
		setRotate(c2,true,180,18);
		setRotate(c3,true,145,24);
		
	}
	
	 @FXML
	 void exitL(MouseEvent event) {
		 
			Node source = (Node)event.getSource();
			Stage stage = (Stage)source.getScene().getWindow();
			
			stage.close();	
		 
	    }
	
	@FXML
    void autobusClick(MouseEvent event) {

		loadPage("Autobus");
		
    }

    @FXML
    void driverClick(MouseEvent event) {

    	loadPage("Driver");

    	
    }

    @FXML
    void roundClick(MouseEvent event) {
    	
    	loadPage("Round");
    	
    }

    @FXML
    void voyageClick(MouseEvent event) {

    	loadPage("Voyage");
    }
    //загрузка поля
    private void loadPage(String page) {
    	
    	Parent root = null;
    	
    	try {
    
			root =FXMLLoader.load(getClass().getResource(page+".fxml"));
			
			
		} catch (IOException ex) {
			
			Logger.getLogger(MainWorkController.class.getName()).log(Level.SEVERE,null,ex);
			
		}
    	
    	bp.setCenter(root);
    	
    }
    
    //анимация колеса
    
    private void setRotate(Circle c, boolean reverse,int angle,int duration) {
    	RotateTransition rt = new RotateTransition(Duration.seconds(duration),c);
    	rt.setAutoReverse(reverse);
    	
    	rt.setByAngle(angle);
    	rt.setDelay(Duration.seconds(0));
    	rt.setRate(3);
    	rt.setCycleCount(18);
    	rt.play();
    	
    }
    
    public void setUserView() {
    	
    	try {
    	
    		FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("MainWork.fxml"));

    	} catch (Exception ex) {
    		
    		
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setHeaderText(ex.getMessage());
    		alert.show();
    	}
    }
 
    public void setManagerView() {
 	
 	try {
 	
 		FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("MainWork.fxml"));
 		

 		
 	} catch (Exception ex) {
 		
 		
 		Alert alert = new Alert(AlertType.CONFIRMATION);
 		alert.setHeaderText(ex.getMessage());
 		alert.show();
 	}
 }
    
	
}
