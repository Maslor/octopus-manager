package main.java.screens;

import java.io.IOException;
import java.sql.SQLException;

import main.java.entities.Archive;
import main.java.entities.Shell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

//screen 3
/**
 * 
 * This class outputs a Screen that allows an user to create new accounts (User classes) in order to log in into the application.
 * @author Gabriel Freire
 *
 */
public class CreateMenuController extends Archive implements ControlledScreen {
	
	ScreenController controller;
	
	@FXML
	private TextField user;
	@FXML
	private TextField name;
	@FXML
	private TextField post;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField passconf;
	
	@FXML
	private Label roles;
	@FXML
	private Label duplicateID;
	@FXML
	private Label wrongPass;
	
	/**
	 * @see ControlledScreen interface
	 */
	@Override
	public void setParentScreen(ScreenController parent) {
		
		controller = parent;		
	}
	
	/**
	 * Goes to Screen 1 (Screen 1 ID defined in the ScreenFramework class).
	 * @param event
	 */
	@FXML
	private void goToScreen1(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen1ID);
	}
	
	/**
	 * Goes to Screen 2 (Screen 2 ID defined in the ScreenFramework class).
	 * @param event
	 */
	@FXML
	private void goToScreen2(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen2ID);
	}
	
	/**
	 * Goes to Screen 4 (Screen 4 ID defined in the ScreenFramework class).
	 * @param event
	 */
	@FXML
	private void goToScreen4(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen4ID);
	}
	
	/**
	 * Makes the UI's text field role let the user know which roles are available when creating
	 * an account. Unless the text field's text is one of the two supported roles, 
	 * it changes the field's background colour to red and displays a message. This happens in real-time.
	 */
	@FXML
	public void help() {
		
		roles.setTextFill(Color.RED);
		
		post.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>(){
			
			@Override
			public void handle(KeyEvent arg0) {
				TextField f = (TextField)arg0.getSource();
				post.getStyleClass().add("error");
				roles.setVisible(true);
	
				if(f.getText().equals("manager") || f.getText().equals("employee")) {
					post.getStyleClass().remove("error");
					roles.setVisible(false);
				}
			}
		});	
	}
	
	/**
	 * 
	 * Creates a new account (User)
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	public void createAccount(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		
		roles.setTextFill(Color.RED);
		wrongPass.setTextFill(Color.RED);
		duplicateID.setTextFill(Color.RED);
		
		if(super.loadShell() != null) {
			Shell loadShell = loadShell();
			shl.setEmployees(loadShell.getEmployees());
			shl.setManagers(loadShell.getManagers());
			shl.setTabs(loadShell.getTabs());
			shl.setUsers(loadShell.getUsers());
		}
		
		loadLog();
		
		if(!user.getText().equals("") && !password.getText().equals("") &&
			!passconf.getText().equals("") && !name.getText().equals("") &&
			!post.getText().equals("")){
	
			if(shl.findUser(user.getText())!=null){
				user.getStyleClass().add("error");
				duplicateID.setVisible(true);
				
				System.out.printf("[SHELL] User %s exists as %s %s\n ", user.getText(),shl.findUser(user.getText()),shl.findUser(user.getText()));
			}
			
			else if(password.getText().equals(passconf.getText())){
					shl.createUser(user.getText(), password.getText(), post.getText(), name.getText()); 
					
					System.out.printf("Created User %s\n",user.getText());
					
					user.getStyleClass().remove("error");
					password.getStyleClass().remove("error");
					passconf.getStyleClass().remove("error");
					roles.setVisible(false);
					duplicateID.setVisible(false);
					wrongPass.setVisible(false);
					
					super.saveShell();
					super.saveLog("USER "+user.getText()+" - "+ name.getText()+" HAS BEEN CREATED");
					
					goToScreen1(event); 
					
				} else {
					System.out.println("[SHELL] User creation failed - Passwords don't match");
					
					password.getStyleClass().add("error");
					passconf.getStyleClass().add("error");
					wrongPass.setVisible(true);
				}
			
		} else System.out.println("[SHELL] User creation failed - Missing fields");
	}
	
}
