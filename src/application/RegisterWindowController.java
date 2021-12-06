package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

public class RegisterWindowController {
	
	@FXML
	private Button bDone;
	@FXML
	private Button bBack;
	@FXML
	private TextField fName;
	@FXML
	private TextField fLogin;
	@FXML
	private PasswordField fPass;
	@FXML
	private PasswordField fRepeatPass;

	@FXML
	public void done(ActionEvent event) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		String name = fName.getText();
		String login = fLogin.getText();
		String pass = fPass.getText();
		String repPass = fRepeatPass.getText();
		
		Node source = (Node)event.getSource();
		Stage stage = (Stage)source.getScene().getWindow();
		
		if (pass.equals(repPass)) { 
			
			User newUser = new User(name, login, pass);
			
			try {
			
				newUser.register();
				
				if (!newUser.auth())
					throw new Exception("Не удалось войти в аккаунт");
				

				Context.LoggedUser = newUser;
				
	
				
				 BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("MainWork.fxml"));
		            Scene scene = new Scene(root);
		            stage.setScene(scene);
		            stage.setResizable(false);
		            stage.show();
				
				
				
			} catch (Exception ex) {
				
				alert.setHeaderText(ex.getMessage());
				alert.show();
				return;
			}
			
			
						
		} else {
			
			alert.setHeaderText("Пароли не совпадают");
			alert.show();
		}
	}
	
	@FXML
	public void back(ActionEvent event) {
		
		try {
			
			Main.mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login.fxml"))));
			
		} catch (Exception ex) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText(ex.getMessage());
			alert.show();
		}
	}
}
