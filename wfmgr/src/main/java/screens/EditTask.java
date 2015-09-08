package main.java.screens;

import java.io.IOException;
import java.sql.SQLException;

import main.java.entities.Archive;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

//screen 6
/**
 * EditTask outputs a javaFX screen that interacts with the user, allowing the latter to edit a selected task.
 * @author Gabriel Freire
 *
 */
public class EditTask extends Archive implements ControlledScreen {
	
	@FXML
	private TextField taskName;
	@FXML
	private TextArea taskDescription;
	@FXML
	private TextField taskAssignee;
	@FXML
	private Label noRights;
	
	ScreenController controller;
	
	/**
	 * @see main.java.screens.ControlledScreen
	 */
	@Override
	public void setParentScreen(ScreenController parent) {
		
		controller = parent;		
	}
	
	/**
	 * Goes to Screen 1 (Screen 1 ID defined in the ScreenFramework class - @see main.screens.ScreenFramework).
	 * @param event
	 */
	@FXML
	private void goToScreen1(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen1ID);
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
	 * Sets up EditTask UI's text fields with the selected task for edition's attributes.
	 * @param event
	 */
	@FXML
	private void setUpEdit(MouseEvent event){
		
		noRights.setVisible(false);
		taskAssignee.getStyleClass().remove("error");
		taskName.getStyleClass().remove("error");
		taskName.setText(shl.getCurrentTab().getName());
		taskDescription.setText(shl.getCurrentTaskDescription());
		taskAssignee.setText(shl.getCurrentTab().getAssignee());
	}
	
	/**
	 * Edits task.
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	private void editTask(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		
		
		if(!shl.getCurrentTab().isLOCKED()){
			
			if(!taskName.getText().equals("") && !taskDescription.getText().equals("") 
					&& !taskAssignee.getText().equals("")) {
				
				if(shl.findUser(taskAssignee.getText())!=null){
					
					if(shl.loadTask(taskName.getText())==null || shl.loadTask(taskName.getText()).equals(shl.getCurrentTab())){
						
						if(shl.checkPermission()){				
							shl.editTask(taskName.getText(), taskDescription.getText(), taskAssignee.getText());
							
							System.out.printf("Task %s edited!\n",taskName.getText());
							
							super.saveShell();
							super.saveLog(shl.getCurrentUser().getID() + " - " + shl.getCurrentUser().getName() +
									" - "+"EDITED TASK "+taskName.getText());
						
							goToScreen2(event);
							
						} else {
							noRights.setVisible(false);
							noRights.setText("You have to be either the author or assignee of this task to edit it.");
							noRights.setVisible(true);
							noRights.setTextFill(Color.RED);
							
							System.out.println("You have no permission");
						}
						
					} else {
						noRights.setVisible(false);
						noRights.setText("Task name already exists.");
						noRights.setTextFill(Color.RED);
						noRights.setVisible(true);
						taskName.getStyleClass().add("error");
						
						System.out.println("TaskName Already exists");
					}
					
				} else {
					noRights.setVisible(false);
					noRights.setText("Assigned user doesn't exist.");
					noRights.setTextFill(Color.RED);
					noRights.setVisible(true);
					taskAssignee.getStyleClass().add("error");
					
					System.out.println("User doesn't exist");
				}
				
			} else {
				noRights.setVisible(false);
				noRights.setText("There can't be empty fields.");
				noRights.setTextFill(Color.RED);
				noRights.setVisible(true);
				
				System.out.println("Empty Fields");
			}
			
		} else {
			noRights.setVisible(false);
			noRights.setText("This task is locked");
			noRights.setVisible(true);
			noRights.setTextFill(Color.RED);
			
			System.out.println("Locked Task");
		}
	} 
	
	/**
	 * Deletes task.
	 * @param event
	 */
	@FXML
	private void deleteTask(ActionEvent event) {
		
		shl.deleteTask(shl.getCurrentTab());
	}
	
}
