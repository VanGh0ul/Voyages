package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable{
	@FXML
	private TextField usernameTextField;
	@FXML
	private Button loginButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button registerButton;
	@FXML
	private Label loginMessageLabel;
	@FXML
	private PasswordField enterPasswordField;
	
    @FXML
    private ImageView carImageView;

    @FXML
    private ImageView lockImageView;

	
	
	
	// Event Listener on Button[#loginButton].onAction
	@FXML
	public void loginButton_click(ActionEvent event) throws ClassNotFoundException, SQLException {
		
	/*	
	try {
    		
    		Main.mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Scream.fxml"))));
    		
    		Main.mainStage.setFullScreenExitHint("");
    		

    		
			
    	} catch (Exception ex) { 
    		
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setHeaderText(ex.getMessage());
    		alert.show();
    	}
		
	 */
		
		String login = usernameTextField.getText().trim();
		String pass = enterPasswordField.getText().trim();
		
		boolean logged = false;
		User newUser = new User(login, pass);
		

    	loginMessageLabel.setText("Invalid login. Try again");
    	
    	// Вход в систему
		try {

			logged = newUser.auth();
			
		} catch (Exception ex) {
		
			loginMessageLabel.setText("Invalid login. Try again");
	    	return;
		}
		
		if (logged) {
			
			try {
				
				// Сохранение данных нынешнего пользователя
				Context.LoggedUser = newUser;
			
				// Переопределение формы
				FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWork.fxml"));
				Main.mainStage.setResizable(true);
				
				Main.mainStage.setScene(new Scene(loader.load()));
				
				MainWorkController controller = loader.getController();
				
				// Построение формы в соответствие с ролью пользователя
				if (Context.LoggedUser.getRole().equals(User.MANAGER_STR))
					controller.setManagerView();
				
				else
					controller.setUserView();
				
							
			} catch (Exception ex) { 
				
				ex.printStackTrace();
			}
		
		} else 
			loginMessageLabel.setText("Invalid login. Try again!");
		
	}
	
	// Event Listener on Button[#registerButton].onAction
	@FXML
	public void registerButton_click(ActionEvent event) {
		
		
			try {
    		
    		Main.mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("RegisterWindow.fxml"))));
			
    	} catch (Exception ex) { 
    		
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setHeaderText(ex.getMessage());
    		alert.show();
    	}
		
	}
	
	
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void cancelButton_click(ActionEvent event) {
		Stage stage =(Stage) cancelButton.getScene().getWindow();
		stage.close();
	}


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		File carFile = new File("images/car.png");
		Image carImage = new Image(carFile.toURI().toString());
		carImageView.setImage(carImage);
		
		File lockFile = new File("images/lock.png");
		Image lockImage = new Image(lockFile.toURI().toString());
		lockImageView.setImage(lockImage);
		
		usernameTextField.setText("ADM");
		enterPasswordField.setText("ADM");

	}
	

	
}
