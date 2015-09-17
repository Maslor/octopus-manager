package main.java.entities;

/**
 * Employee is a subClass of User that has more permissions.
 * @author Gabriel Freire
 *
 */
public class Manager extends User {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7399406606831174006L;

	/**
	 * 
	 * @param userID
	 * @param password
	 * @param name
	 * @param shl
	 */
	public Manager(String userID, String password, String name, Shell shl) {
		super(userID, password,"manager");
		shl.addManager(this);
		this.setName(name); 
	}
}
