package main.java.screens;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class encapsulates all the resource fxml files and controller IDs in a simple framework. It is also responsible
 * for encapsulating a ScreenController object that controls all the interface screens.
 * @author Gabriel Freire
 *
 */
public class ScreenFramework extends Application{
		
		public static String screen1ID = "Login";
		public static String screen2ID = "ToDo";
		public static String screen3ID = "Create";
		public static String screen4ID = "CreateTask";
		public static String screen5ID = "Confirmation";
		public static String screen6ID = "EditTask";
		public static String screen7ID = "FileList";
		
		public static String screen1File = "LoginMenu.fxml";
		public static String screen2File = "Todo.fxml";
		public static String screen3File = "CreateMenu.fxml";
		public static String screen4File = "CreateTask.fxml";
		public static String screen5File = "Confirmation.fxml";
		public static String screen6File = "EditTask.fxml";
		public static String screen7File = "FileList.fxml";
		
		/**
		 * 
		 */
		@Override
		public void start(Stage primaryStage) throws Exception {
			
			ScreenController container = new ScreenController();
			
			container.loadScreen(ScreenFramework.screen1ID, ScreenFramework.screen1File);
			container.loadScreen(ScreenFramework.screen2ID, ScreenFramework.screen2File);
			container.loadScreen(ScreenFramework.screen3ID, ScreenFramework.screen3File);
			container.loadScreen(ScreenFramework.screen4ID, ScreenFramework.screen4File);
			container.loadScreen(ScreenFramework.screen5ID, ScreenFramework.screen5File);
			container.loadScreen(ScreenFramework.screen6ID, ScreenFramework.screen6File);
			container.loadScreen(ScreenFramework.screen7ID, ScreenFramework.screen7File);
			
			container.setScreen(ScreenFramework.screen1ID);
			
			Group root = new Group();
			root.getChildren().addAll(container);
			Scene scene = new Scene(root);
			
			String style = main.java.screens.ScreenFramework.class.getResource("error.css").toExternalForm(); //stylesheet from resources
		
			scene.getStylesheets().add(style);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		
		/**
		 * Static main method that launches the application. NetBeans doesn't seem to need it.
		 * @param args
		 */
		public static void main(String[] args){
			
			launch(args);
		}	
		
}
