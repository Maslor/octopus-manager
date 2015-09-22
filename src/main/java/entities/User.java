package main.java.entities;

import java.io.Serializable;

/**
 * User is an abstract class that defines methods and attributes common to both manager and employee types.
 * @author Gabriel Freire
 * 
 */
public abstract class User implements Serializable{       
	
	/**
	 * 
	 * @author Gabriel Freire
	 */
	private static final long serialVersionUID = 7772792393270288349L;
	private String _userID;
	private String _password;
	private String _dpt;
	private String _name; 
	private String _role;
	
	/**
	 * 
	 * @param userID
	 * @param password
	 * @param post
	 */
	public User(String userID, String password, String post){
		
		this._userID = userID;
		this._password = password;
		this._role = post;
		
		System.out.println(post + " created");
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		
		return " "+ this._name ;
	}
	
	/**
	 * Gets this user's ID.
	 * @return user id
	 */
	public String getID(){
		
		return this._userID;
	}
	
	/**
	 * Changes password to newPass if user knows oldPass
	 * @param newPass
	 * @param oldPass
	 */
	public void changePassword(String newPass, String oldPass){
		
		if (oldPass.equals(this._password)==true){
			this._password= newPass;
		}
	}
	
	/**
	 * Gets this user's password.
	 * @return user's password
	 */
	public String getPassword(){
		
		return this._password;
	}
	
	/**
	 * Gets this user's department.
	 * @return department
	 */
	public String getDepartment(){
		
		return this._dpt;
	}
	
	/**
	 * Sets this user's department.
	 * @param dpt
	 */
	public void setDepartment(String dpt){
		
		this._dpt = dpt;
	}
	
	/**
	 * Gets this user's name.
	 * @return name
	 */
	public String getName(){
		
		return this._name;
	}

	
	/**
	 * Sets this user's name.
	 * @param newName
	 */
	public void setName(String newName){
		
		this._name = newName;
	}
	
	/**
	 * Check's if string is this user's pass
	 * @param pass
	 * @return true if it is, false if it isn't
	 */
	public boolean checkPass(String pass){
		
		if(this._password.equals(pass)) return true;
		
		return false;
	}
	
	/**
	 * Sets this user's password.
	 * @param newPass
	 */
	public void setPass(String newPass) {
		
		this._password = newPass;	
	}
	
	/**
	 * Changes this user's name to specified string
	 * @param name
	 */
	public void changeName(String name) {
		
		this._name = name;
	}
	
	/**
	 * Gets this user's role/function.
	 * @return role
	 */
	public String getRole() {
		
		return _role;
	}
	
	/**
	 * Sets this user's role/function
	 * @param _role
	 */
	public void setRole(String _role) {
		
		this._role = _role;
	}	
}
