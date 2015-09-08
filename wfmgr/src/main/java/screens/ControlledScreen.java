package main.java.screens;

import main.java.entities.Shell;

/**
 * ControlledScreen is an interface that has some attributes and methods that must be common in all classes that implement it.
 * @author Gabriel Freire
 *
 */
public interface ControlledScreen {
	
	public String shellFilename = "shell.txt";
	public Shell shl = new Shell();
	
	/**
	 * Sets this controller's ScreenController as the parent implemented by the ControlledScreen interface.
	 * This allows various scenes to be presented in the same stage, instead of launching a stage for each
	 * scene.
	 * @param parent
	 */
	public void setParentScreen(ScreenController parent);
	
}
