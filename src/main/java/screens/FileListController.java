package main.java.screens;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import main.java.entities.Archive;
import main.java.entities.Shell;

//screen 7
/**
 * FileListController outputs a javaFX screen that lists all of the selected task's files. Not functional right now.
 * @author Gabriel Freire
 *
 */
public class FileListController extends Archive implements ControlledScreen{
	
	private Window mainStage;
	
	@FXML
	private ListView<File> fileListView;
	
	ScreenController controller;

	protected File currentFile;
	
	/**
	 * 
	 */
	@Override
	public void setParentScreen(ScreenController parent) {
		
		controller = parent;
	}
	
	/**
	 * 
	 * @param event
	 */
	@FXML
	private void goToScreen2(ActionEvent event) {
		
		shl.getCurrentTab().resetSet();
		controller.setScreen(ScreenFramework.screen2ID);
	}
	
	/**
	 * opens a task's uploaded file in the default system's program for that extension.
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void downloadFile(ActionEvent event) throws IOException{
		
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Choose a Target Directory");
		File selectedDir = dirChooser.showDialog(mainStage);
		
		FileUtils.copyFileToDirectory(currentFile, new File(selectedDir.getAbsolutePath()));
	}
	
	@FXML
	private void openFile(ActionEvent event) throws IOException {
		

		
		/*
		File selectedDir = new File(super.getPath("Files-" +shl.getCurrentTab().getName()));
		FileUtils.copyFileToDirectory(shl.getCurrentTab().getFileN("File-" + currentFile.getName()), selectedDir);
		*/
		Desktop dt = Desktop.getDesktop();
		FileUtils.getFile(super.getPath("files" + "\\"+currentFile.getName())).setReadOnly();
		dt.open(FileUtils.getFile(super.getPath("files" + "\\"+currentFile.getName())));
		
	}
	
	
	
	@FXML
	private void deleteFile(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		shl.deleteFile(currentFile.getAbsoluteFile());
		FileUtils.forceDelete(FileUtils.getFile(super.getPath("files" + "\\"+currentFile.getName())));
		//fileListView.setItems(null);
		//fileListView.setItems(FXCollections.observableArrayList(FXCollections.observableSet(shl.getCurrentTab().fileMapToSet())));
		super.saveShell();

		super.saveLog(shl.getCurrentUser().getID() + " - " + shl.getCurrentUser().getName() +
									" - "+"DELETED FILE "+ fileListView.getSelectionModel().getSelectedItem().getName() + " FROM TASK " + shl.getCurrentTab().getName());
	
	}
	
	
	@FXML
	public void refreshFileList() throws ClassNotFoundException, SQLException, IOException {
		
		//super.loadShell();
	
		fileListView.setItems(FXCollections.observableArrayList(FXCollections.observableSet(shl.getCurrentTab().fileMapToSet())));
		//lstView.setCellFactory(ComboBoxListCell.forListView(FXCollections.observableArrayList(data)));   
        fileListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fileListView.setEditable(true);
   
        fileListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
        	@Override
            public void handle(MouseEvent event) {
        		currentFile = (File) fileListView.getSelectionModel().getSelectedItem();
                System.out.println("clicked on " + fileListView.getSelectionModel().getSelectedItem());
       
            }
        });
	}

}
