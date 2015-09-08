package main.java.screens;

import main.java.entities.Archive;
import main.java.entities.Task;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;

//screen 2
/**
 * ToDoController is the most vital controller in all of the application since
 * it's this class that controls the application's main screen, which contains most
 * of the used UI objects and provides most of the application's interaction capabilities.
 * @author Gabriel Freire
 */
public class ToDoController extends Archive implements ControlledScreen {
	
	@FXML
	private Text actionStatus;
	@FXML
	private ListView<Task> lstView;
	@FXML
	private CheckBox verified;
	@FXML
	private CheckBox finished;
	@FXML
	private Text name;
	@FXML
	private TextArea description;
	@FXML
	private Button refresh;
	@FXML 
	private TextField tf;
	@FXML
	private TextField taskName;
	@FXML
	private TextField assignee;
	@FXML
	private TextArea comment;
	@FXML
	private Label status;
	@FXML
	private Label statusV;
	@FXML
	private TextField fileLabel;
	@FXML
	private Label download;
	@FXML
	private Text authorname;
	@FXML
	private Text lastEditor;
	@FXML
	private Label lastEditDate;
	@FXML
	private Label creationDate;
	@FXML
	private Label completionDate;
	@FXML
	private Label verificationDate;
	@FXML
	private CheckBox reject;
	
	
	ScreenController controller;
	
	private Window mainStage;
	
	
	/**
	 * Method implementation of the ControlledScreen interface. @see ControlledScreen
	 */
	@Override
	public void setParentScreen(ScreenController parent) {
		try {
			super.loadShell();
			refreshList();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controller = parent;
		
	}
	
	/**
	 * Goes to Screen 1, passing the ActionEvent
	 * @param event
	 * @throws IOException
	 */
	@FXML 
	private void goToScreen1(ActionEvent event) throws IOException { 
		
		controller.setScreen(ScreenFramework.screen1ID); 
	} 
	
	/**
	 * Goes to Screen 7, passing the ActionEvent
	 * @param event
	 */
	@FXML
	private void goToScreen7(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen7ID);
	}
	
	/**
	 * Logs the current user out of the application and returns to the Login scene.
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	public void logout(ActionEvent event) throws IOException, ClassNotFoundException {
		
		verified.setDisable(false);
		goToScreen1(event);
	}
	
	/**
	 * Goes to Screen 4, passing the ActionEvent
	 * @param event
	 */
	@FXML
	private void goToScreen4(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen4ID); //goto create Task	
	}
	
	/**
	 * Goes to Screen 5, passing the ActionEvent
	 * @param event
	 */
	@FXML
	private void goToScreen5(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen5ID); //goto confirmation -> createTask
	}
	
	/**
	 * Goes to Screen2 , passing the ActionEvent
	 * @param event
	 */
	@FXML
	private void goToScreen2(ActionEvent event) {
		
		controller.setScreen(ScreenFramework.screen2ID); //goto this
	}
	
	/**
	 * Goes to Screen 6, passing the ActionEvent
	 * @param event
	 */
	@FXML
	private void goToScreen6(ActionEvent event){
		
		controller.setScreen(ScreenFramework.screen6ID); //goto editTask
	}
	
	/**
	 * Assigns a user to the current tab when a button is clicked. Deprecated.
	 * @param event
	 */
	@FXML
	private void assignUser(MouseEvent event)  {
		
		if (shl.findUser(name.getText()) != null){
			shl.setAssignee(name.getText(), shl.getCurrentTab().getName());

			System.out.printf("Assigning %s to the task",name.getText());
		}
	}
	
	/**
	 * Gets the current task's name
	 * @return current task's name
	 */
	@FXML
	private String getTaskName() {
		
		return shl.getCurrentTab().getName();
	}
	
	/**
	 * Gets this task's current description.
	 * @return current Task's Description
	 */
	@FXML
	private String getTaskDescription() {
		
		return shl.getCurrentTaskDescription();
	}
	
	/**
	 * It's responsible for refreshing the application's task list when the "Refresh" button is pressed.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	@FXML
	public void refreshList() throws ClassNotFoundException, SQLException, IOException {
		
		super.loadShell();
		
		super.loadLog();
		
		lstView.setItems(FXCollections.observableArrayList(shl.mapToSet()));
		//lstView.setCellFactory(ComboBoxListCell.forListView(FXCollections.observableArrayList(data)));   
        //lstView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lstView.setEditable(true);
   
        lstView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
        	@Override
            public void handle(MouseEvent event) {

                System.out.println("clicked on " + lstView.getSelectionModel().getSelectedItem());
                
                shl.setCurrentTab((Task) lstView.getSelectionModel().getSelectedItem());
                whatever();
              
                taskName.setText(shl.getCurrentTab().getName());
                description.setText(shl.getCurrentTaskDescription());
                name.setText(shl.getCurrentTab().getAssignee());
                authorname.setText(shl.getCurrentTab().getAuthor());
                comment.setText(shl.getCurrentTab().getComment());      
                lastEditor.setText(shl.getCurrentTab().getLastEditor().getID());
                status.setText(statusCheck());
                statusV.setText(statusVCheck());
                lastEditDate.setText(shl.getCurrentTab().getLastEditDate());
                creationDate.setText(shl.getCurrentTab().getCreationDate());
                verificationDate.setText(shl.getCurrentTab().getVerificationDate());
                completionDate.setText(shl.getCurrentTab().getCompletionDate());
                //authorname.setText(shl.getCurrentTab().getAuthor().getName());
                
                if(shl.getCurrentTab().getFileDemo()!=null){
                	fileLabel.setText(shl.getCurrentTab().getFileDemo().toString());
                	
                } else fileLabel.setText("No file uploaded");
                
                if(shl.getCurrentTab().isLOCKED()){
                	comment.setDisable(true);
                	
                } else comment.setDisable(false);
                
                /*if(shl.getCurrentTab().getStatus()==true && shl.getCurrentTab().getStatusV()==true){
                	set background color green
                }*/
            }
        });
	}
	
	/**
	 * converts the current task's subtasks Map(String,Task) to a Set(Task) and injects it as a subListView component. 
	 */
	public void whatever(){
		
		if(shl.getCurrentTab().mapToSet()!=null){
	        lstView.setCellFactory(ComboBoxListCell.forListView(FXCollections.observableArrayList(shl.getCurrentTab().mapToSet())));
		}
	}
	
	/**
	 * 
	 * @return "COMPLETE" or "ONGOING" depending on whether the current task's status is true or false, respectively.
	 */
	protected String statusCheck() {
		
		boolean b = shl.getCurrentTab().getStatus();
		
		
		if (b==true){
			//status.getStyleClass().add("green");
			status.setTextFill(Color.GREEN);
			finished.setSelected(true);
			
			return "COMPLETE";
		}
		
		else {
			status.setTextFill(Color.RED);
			finished.setSelected(false);
			verified.setSelected(false);
			return "ONGOING";
		}
	}
	
	/**
	 * 
	 * @return "Verified" or "Unverified" whether the current task's statusV is true or false, respectively.
	 */
	protected String statusVCheck() {
		
		boolean b = shl.getCurrentTab().getStatusV();
		boolean r = shl.getCurrentTab().getRejection();
		
	 if(r==true) {
		statusV.setTextFill(Color.RED);
		reject.setSelected(true);
		
		return "REJECTED";
	}
	
	else if (b==true){
			statusV.setTextFill(Color.GREEN);
			verified.setSelected(true);
			
			return "Verified";
	}
	
		else {	
			verified.setSelected(false);					
			statusV.setTextFill(Color.BROWN);
			return "Unverified";
		} 
		
	}
	
	@FXML
	protected void statusVReject(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		
		shl.markAsRejected();
		
		super.saveShell();
		
		super.saveLog(shl.getCurrentUser().getID() + " - " + shl.getCurrentUser().getName() +
				" - " + "REJECTED TASK "+shl.getCurrentTab().getName());
		
	}

	/**
	 * marks the current Task as Complete.
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void markAsComplete(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		
		shl.markAsComplete();
		shl.getCurrentTab().setReject(false);
		
		super.saveShell();
		
		super.saveLog(shl.getCurrentUser().getID() + " - " + shl.getCurrentUser().getName() +
				" - " + "MARKED TASK "+shl.getCurrentTab().getName()+" AS COMPLETE");
	}
	
	/**
	 * marks the current Task as Verified by a Manager
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void markAsVerified(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		
		shl.markAsVerified();
		shl.getCurrentTab().setReject(false);
		
		super.saveShell();
		
		super.saveLog(shl.getCurrentUser().getID() + " - " + shl.getCurrentUser().getName() +
				" - " + "MARKED TASK "+shl.getCurrentTab().getName()+" AS VERIFIED");
	}
	
	/**
	 * uploads File into currently selected Task. *old version+
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	/*@FXML
	private void uploadFileOld(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(mainStage).getAbsoluteFile();
		
		shl.getCurrentTab().addFile(file.toString(),file);
		
		super.saveShell();
		super.saveLog(shl.getCurrentUser().getID() + " - " + shl.getCurrentUser().getName() +
				" - " + "UPLOADED "+file.getName());

		download.setText("Download done!");
		download.setTextFill(Color.GREEN);
		
		System.out.println("File uploaded as "+file.toString());
	}*/
	
	/**
	 * uploads File into currently selected Task.
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void uploadFile(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(mainStage).getAbsoluteFile();	
		
		//FileUtils.copyFileToDirectory(file, FileUtils.getFile(super.getPath(file.getName())), true);
		File selectedDir = new File(super.getPath("files"));
		FileUtils.copyFileToDirectory(file, new File(selectedDir.getAbsolutePath()));
		shl.getCurrentTab().addFile("File-"+file.getName(), file);
		
		download.setText("Upload done!");
		download.setTextFill(Color.GREEN);
		
		super.saveShell();
		super.saveLog(shl.getCurrentUser().getID() + " - " + shl.getCurrentUser().getName() +
				" - " + "UPLOADED "+file.getName());
	
		
		System.out.println("File uploaded as "+file.toString());
	}
	
	/**
	 * opens a task's uploaded file in the default system's program for that extension.
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	/*@FXML
	private void downloadFile(ActionEvent event) throws IOException{
		
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Choose a Target Directory");
		File selectedDir = dirChooser.showDialog(mainStage);
		
		FileUtils.copyFileToDirectory(shl.getCurrentTab().getFileDemo(), new File(selectedDir.getAbsolutePath()));
	
	}*/
	
	/*@FXML
	private void openFile(ActionEvent event) throws IOException {
		
		File f = shl.getCurrentTab().getFileDemo();
		f.setReadOnly();
		
		Desktop dt = Desktop.getDesktop();
		dt.open(f);
		
		download.setText("Download done!");
		download.setTextFill(Color.GREEN);
	}
	*/
	
	/**
	 * edits a Task by sending a User to Screen 6 (Edit Task)
	 * @param event
	 */
	@FXML
	private void editTask(ActionEvent event) {
		
		goToScreen6(event);
	} 
	
	/**
	 * sets the current Task's comment text field
	 * @param event
	 */
	@FXML
	private void comment(ActionEvent event) {
		
		shl.getCurrentTab().setComment(comment.getText());
		lstView.notify();
	}
	
	/**
	 * deletes a Task. *Not working*
	 * @param event
	 */
	@FXML
	private void deleteTask(ActionEvent event) {
		
		goToScreen5(event);
	}

	
}
