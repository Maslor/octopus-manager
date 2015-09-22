package main.java.screens;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import main.java.entities.Archive;
import main.java.entities.Shell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

//screen 1
/**
 * 
 * LoginController provides the first javaFX screen for interaction with the User. From here, the user may log into an existing account or create a new Account.
 * @author Gabriel Freire
 *
 */
public class LoginController extends Archive implements ControlledScreen{
	
	@FXML
	private PasswordField pass;
	@FXML
	private TextField user;
	@FXML
	private Label error;
	
	ScreenController controller;
	
	/**
	 * @see main.java.screens.ControlledScreen
	 */
	@Override
	public void setParentScreen(ScreenController parent) {
		
		controller = parent;
	}
	
	/**
	 * Goes to Screen 2 (Screen 2 ID defined in the ScreenFramework class - @see main.screens.ScreenFramework).
	 * @param event
	 */
	@FXML
	private void goToScreen2(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen2ID);
	}
	
	/**
	 * Goes to Screen 3 (Screen 3 ID defined in the ScreenFramework class - @see main.screens.ScreenFramework).
	 * @param event
	 */
	@FXML
	private void goToScreen3(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen3ID);
	}
	
	/**
	 * Goes to Screen 4 (Screen 4 ID defined in the ScreenFramework class - @see main.screens.ScreenFramework).
	 * @param event
	 */
	@FXML
	private void goToScreen4(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen4ID);
	}
	
	/**
	 * Logs user in if username and password are the same of an existing account.
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML 
	private void login(ActionEvent event) throws ClassNotFoundException, FileNotFoundException, IOException, SQLException { 
		
		super.loadShell();
		
		super.loadLog();
		
	
		
		if(user.getText()!=null && pass.getText()!=null){
			
			if(shl.login(user.getText(),pass.getText())){ 
				error.getStyleClass().remove("error");
				
			 System.out.printf("%s\n",shl.getCurrentUser()); 
			 
			 user.getStyleClass().remove("error"); 
			 pass.getStyleClass().remove("error");
			 
			 System.out.println("Valid login");
			 
			 saveLog(shl.getCurrentUser().getID() + " - " + shl.getCurrentUser().getName() +
						" - "+" LOGGED IN");
			 
			 goToScreen2(event);
			
			} else {
				user.getStyleClass().add("error"); 
				pass.getStyleClass().add("error");
				error.setText("User and/or password incorrect");
				error.setVisible(true);
				
				System.out.println("Invalid login");
			}
		} 
		error.setTextFill(Color.RED);	
	}

	
}
	
	

