package main.java.screens;

import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * ScreenController is a class that manages all the screens.
 * @author Gabriel Freire
 *
 */
public class ScreenController extends StackPane{

	private HashMap<String, Node> screens = new HashMap<>();
	
	/**
	 * @see ScreenController
	 */
	public ScreenController() {
		
		super();
	}
	
	/**
	 * Loads screen according to a defined .fxml file.
	 * @param name
	 * @param resource
	 * @return
	 */
	public boolean loadScreen(String name, String resource) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) loader.load();
			
			if(loader.getController() instanceof ToDoController) {
				ControlledScreen screenControler = ((ControlledScreen) loader.getController());
				screenControler.setParentScreen(this);
			}
			
			ControlledScreen screenControler = ((ControlledScreen) loader.getController());
			screenControler.setParentScreen(this);
			addScreen(name, loadScreen);
			
			return true;
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			
			return false;
		}
	}
	
	/**
	 * Adds screen to the screens map.
	 * @param name
	 * @param screen
	 */
	private void addScreen(String name, Node screen) {
		
		screens.put(name, screen);
	}	
	
	/**
	 * Gets screen from the screens map.
	 * @param name
	 * @return
	 */
	public Node getScreen(String name) {
		
		return screens.get(name);
	}
	
	/**
	 * Sets screen named as the specified String on Stage. If there's already a scene on stage, clears it.
	 * @param name
	 * @return true if screen exists and has been loaded, false if screen doesn't exist or couldn't be loaded.
	 */
	public boolean setScreen(final String name) {
		
		if(screens.get(name)!=null) {
			
			if(!getChildren().isEmpty()) { //in case there are more than one screen
				getChildren().remove(0);
				getChildren().add(screens.get(name));   
				
			} else {
				getChildren().add(screens.get(name));       //no one else been displayed, then just show
			}
			
			return true;
			
		} else { 
			System.out.println("[screen hasn't been loaded]");
			
			return false;
		}
	}
	
	/**
	 * Unloads screen.
	 * @param name
	 * @return
	 */
	public boolean unloadScreen(String name) {
		
		if(screens.remove(name)!=null) {
			System.out.println("Screen does not exist");
			
			return false;
			
		} else {
			return true;
		}	
	}
	
}
