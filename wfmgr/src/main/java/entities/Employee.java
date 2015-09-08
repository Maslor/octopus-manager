package main.java.entities;

/**
 * Employee is a subClass of User that has restricted permissions.
 * @author Gabriel Freire
 * 
 */
public class Employee extends User {  //utilizador normal

	/**
	 * 
	 */
	private static final long serialVersionUID = -7127311221685438247L;

	/**
	 * 
	 * @param userID
	 * @param password
	 * @param name
	 * @param shl
	 */
	public Employee(String userID, String password, String name, Shell shl) {
		super(userID, password,"employee");
		shl.addEmployee(this);
		this.setName(name); 
	}
}
