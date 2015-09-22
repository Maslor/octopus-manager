package main.java.screens;

import java.io.IOException;
import java.sql.SQLException;

import main.java.entities.Archive;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

//screen 4
/**
 * 
 * CreateTask outputs a screen that allows the current user to create a new task.
 * @author Gabriel Freire
 *
 */
public class CreateTask extends Archive implements ControlledScreen {
	
	private static final String[] MONTHS = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
	@FXML
	private TextField taskName;
	@FXML
	private TextArea taskDescription;
	@FXML
	private TextField taskAssignee;
	@FXML
	private Label assignError;
	@FXML
	private Label duplicateName;
	@FXML
	private RadioButton subt;
	@FXML
	private RadioButton month;
	@FXML
	private RadioButton trim;
	@FXML
	private Label errorMessage;
	
	ScreenController controller;
	
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
	 * Goes to Screen 3 (Screen 3 ID defined in the ScreenFramework class @see ScreenFramework).
	 * @param event
	 */
	@FXML
	private void goToScreen3(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen3ID);
	}
	
	/**
	 * Creates a new task.
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void createTask(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		
		if(!taskName.getText().equals("") && !taskDescription.getText().equals("") 
				&& !taskAssignee.getText().equals("")) {
			
			if(shl.findUser(taskAssignee.getText())!=null){
				
				if(shl.loadTask(taskName.getText())==null){
					
					if(subt.isSelected() && month.isSelected() || subt.isSelected() && trim.isSelected()) {
						errorMessage.setText("SubTasks can't have monthly/trimestral properties");
						errorMessage.getStyleClass().add("error");
						errorMessage.setVisible(true);
						
					} else if (trim.isSelected() && month.isSelected()) {
						errorMessage.setText("A task can't simultaneously be Trimestral and Monthly");
						errorMessage.getStyleClass().add("error");
						errorMessage.setVisible(true);
					}
					
					if (month.isSelected()){
						int i=0;
						
						while(i<12){
							shl.createTab(true, taskName.getText() + " - " + MONTHS[i], taskDescription.getText(), taskAssignee.getText());
							i++;
						}
						
					} else if (trim.isSelected()) {
						int i=2;
						
						while(i<12){
							shl.createTab(true, taskName.getText() + " - " + MONTHS[i], taskDescription.getText(), taskAssignee.getText());
							i+=3;
						}
						
					}
					
					else shl.createTab(subt.isSelected(), taskName.getText(), taskDescription.getText(), taskAssignee.getText());
					
					
					System.out.printf("taskassignee: %s\n", taskAssignee.getText());
					
					super.justUpdated = true;
					this.assignError.setVisible(false);
					this.duplicateName.setVisible(false);
					this.taskAssignee.getStyleClass().remove("error");
					this.taskName.getStyleClass().remove("error");
					
					super.saveShell();
					super.saveLog(shl.getCurrentUser().getID() + " - " + shl.getCurrentUser().getName() +
							" - " + "CREATED TASK "+taskName.getText());
					
					errorMessage.setVisible(false);
					errorMessage.getStyleClass().remove("error");
					
					goToScreen2(event);
					
					System.out.println("Task Successfully created!");
				
				} else {
					System.out.println("Task already exists!");
					
					this.taskName.getStyleClass().add("error");
					this.duplicateName.setVisible(true);
				}
			
			} else {
				System.out.println("User doesn't exist");
				
				this.taskAssignee.getStyleClass().add("error");
				this.assignError.setVisible(true);
			}
			
		} else System.out.println("Empty Fields");
		
		assignError.setTextFill(Color.RED);
		duplicateName.setTextFill(Color.RED);
	} 
	
	/**
	 * Deletes a task.
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void deleteTask(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		
		String taskName = shl.getCurrentTab().getName();
		
		System.out.printf("Deleting %s ...",taskName );	
		
		shl.deleteTask(shl.getCurrentTab());
		
		super.saveShell();
		super.saveLog(shl.getCurrentUser().getID() + " - " + shl.getCurrentUser().getName() +
				" - "+"DELETED TASK "+taskName);
		
		System.out.println("Deleted, going back");
		
		goToScreen2(event);
	}
}
