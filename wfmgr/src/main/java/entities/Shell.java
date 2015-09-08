package main.java.entities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/** 
*
* Shell class serves as a barrier that encapsulates the application's specific classes, entities and methods. All requests from the interface come through this class.
* @author Gabriel Freire
*/
public class Shell implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9218756509845849562L;

	private Map<String,Manager> _managers = new HashMap<String,Manager>();
	private Map<String,Employee> _employees = new HashMap<String,Employee>();
	private Map<String, Task> _tabs = new HashMap<String,Task>();
	private Map<String, User> _usrs = new HashMap<String,User>();
	private Set<Task> _tsks;
	private User _currentUser;	
	private List<File> _files = new ArrayList<File>();
	FileOutputStream out1 = null;
	private Task _currentTab;
	
	/**
	 * refreshes the user list
	 */
	public void refreshUserList() {
		
		this._usrs.clear();
		this._usrs.putAll(_employees);
		this._usrs.putAll(_managers);	
	}
	
	/**
	 * loads the task that has tabName for name.
	 * @param tabName
	 * @return
	 */
	public Task loadTask(String tabName) {
		
		return this._tabs.get(tabName);
	}
	
	/**
	 * makes the loaded task the current task.
	 * @param tabName
	 */
	public void openTab(String tabName) {
		
		Task t = loadTask(tabName);
		
		if(t!= null) {
			this._currentTab = t;		
		}
	}
	
	/**
	 * sets the User (whose id is username) as the task's (which name is tabName) assignee. 
	 * @param username
	 * @param tabName
	 */
	public void setAssignee(String username, String tabName ) {
		
		refreshUserList();
		
		User u = findUser(username);
		loadTask(tabName).setAssignee(u);	
	}
	
	/**
	 * logs a user in if the login parameters are correct.
	 * @param userId
	 * @param password
	 * @return true or false depending on whether the login parameters are correct or not, respectively.
	 */
	public boolean login(String userId, String password) {
		
		User u = findUser(userId);
		
		if(u!= null) {
			
			if(u.checkPass(password)) {
				this._currentUser = u; 
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}
	}
	
	/**
	 * finds the User instance which ID is userID
	 * @param userID
	 * @return User or null, if not found
	 */
	public User findUser(String userID){
		
		refreshUserList();
		
		User u = this._usrs.get(userID);
		
		if (u != null) {
			return this._usrs.get(userID);
			
		} else {
			u = findUserByName(userID);
			if (u!=null){
				return u;
				
			} else {
				return null;
			}
		}
	}
	
	/**
	 * finds the User instance which name attribute is name.
	 * @param name
	 * @return User or null, if not found
	 */
	public User findUserByName(String name){
		
		for(Entry<String,User> entry : this._usrs.entrySet()) {
		
			User value = entry.getValue();
			
			if (value.getName().equals(name)){
				return value;
			}
		}
		return null;
	}
	
	/**
	 * Gets the manager list
	 * @return manager list
	 */
	public Map<String, Manager> getManagers() {
		
		return _managers;
	}
	
	/**
	 * Gets the Employees list.
	 * @return employee list
	 */
	public Map<String, Employee> getEmployees(){
		
		return _employees;
	}
	
	/**
	 * Adds an Employee instance to the employee list
	 * @param ze
	 */
	public void addEmployee(Employee ze){
		
		this._employees.put(ze.getID(),ze);
	}
	
	/**
	 * Removes an Employee instance to the employee list
	 * @param ze
	 */
	public void removeEmployee(Employee ze){
		
		this._employees.remove(ze);
	}
	
	/**
	 * Changes a user's post to the opposite one (manager to emplyee or vice-versa)
	 * @param ze
	 */
	public void changeStatus(User ze){
		
		if (ze.getDepartment().equals("employee")){
			this._employees.remove(ze);
			this._managers.put(ze.getID(),(Manager)ze);
			
		}else{
			this._managers.remove(ze);
			this._employees.put(ze.getID(),(Employee)ze);
		}
	}
	
	/**
	 * Adds File to the current task and to the files list
	 * @param f
	 * @throws IOException
	 */
	public void addFile(File f) throws IOException{
		
		this.getCurrentTab().addFile(FileUtils.readFileToString(f), f);
		
	}
	
	/**
	 * Removes file from the files list
	 * @param f
	 */
	public void removeFile(File f){
		
		_files.remove(f);
	}
	
	/**
	 * Gets the file which name is fileName
	 * @param fileName
	 * @return file or null, if not found
	 */
	public File getFile(String fileName){
		
		int i;
		File f = null;
		
		for(i=0; i<_files.size();i++){
			if (_files.size() == 0) break;
			if (_files.get(i).getName()==fileName) f = _files.get(i);		
		}
		
		return f;
	}
	
	/**
	 * Gets the current User's instance.
	 * @return User
	 */
	public User getCurrentUser(){
		
		return this._currentUser;
	}
	
	/**
	 * Creates a new Task.
	 * @param subTask
	 * @param name
	 * @param description
	 * @param assignee
	 */
	public void createTab(boolean subTask, String name, String description, String assignee){
		
		if(subTask){
			createSubTask(name,description,assignee);
			
			
		} else {
			new Task(this, name, description, assignee);
		}
	}
	
	/**
	 * Creates a subTask to the currently selected Task.
	 * @param name
	 * @param description
	 * @param assignee
	 */
	public void createSubTask(String name, String description, String assignee){
		
		new Task(this, this.getCurrentTab(), name, description, assignee);
	}
	
	/**
	 * Creates a new User.
	 * @param uID
	 * @param password
	 * @param role
	 * @param name
	 * @throws IOException
	 */
	public void createUser(String uID, String password, String role, String name) throws IOException {
		
		if (role.equals("manager")){
			System.out.println("starting to create a manager");
			createManager(uID,password, name);	
		}
		else if (role.equals("employee")){
			System.out.println("Starting to create an employee");
			createEmployee(uID,password, name);
		}
	}	
	
	/**
	 * Sets up the current shell's attributes as the loaded Shell's attributes
	 * @param load
	 */
	public void setUp(Shell load) {
		
		this._currentTab = load._currentTab;
		this._currentUser = load._currentUser;
		this._employees = load._employees;
		this._managers = load._managers;
		this._tabs = load._tabs;
		this._usrs = load._usrs;
	}

	/**
	 * Creates a new Manager.
	 * @param String userID
	 * @param String pass
	 */
	private void createManager(String uID, String password, String name) {
		
		System.out.println("[SHELL] creating manager...");
		
		new Manager(uID, password,name,this);	
	}
	
	/**
	 * Creates a new Employee.
	 * @param uID
	 * @param password
	 * @param name
	 */
	private void createEmployee(String uID, String password, String name) {
		
		System.out.println("[SHELL] creating employee...");
		
		new Employee(uID,password,name,this);	
	}
	
	/**
	 * Adds a Manager to the managers list.
	 * @param mgr
	 */
	public void addManager(Manager mgr){
		
		this._managers.put(mgr.getID(),mgr);
	}

	
	/**
	 * Changes the password of the current User if the old password is right.
	 * @param pass
	 * @param newPass
	 */
	public void changePassword(String pass, String newPass) {
		
		if(this._currentUser.checkPass(pass)) this._currentUser.setPass(newPass);	
	}
	
	/**
	 * Changes current User's name attribute to name.
	 * @param pass
	 * @param name
	 */
	public void changeName(String pass, String name) {
		
		if(this._currentUser.checkPass(pass)) this._currentUser.changeName(name);	
	}

	/**
	 * Deletes the User whose ID is id.
	 * @param id
	 */
	public void deleteUser(String id) {
		
		if(this._currentUser.getID().equals(id)){
			this._usrs.remove(this.findUser(id));
		}	
	}
	
	/**
	 * Deletes Task readTask.
	 * @param readTask
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void deleteTask(String readTask) throws ClassNotFoundException, IOException {
		
		this._tabs.remove(loadTask(readTask));
	}
	
	/**
	 * Gets Current Task.
	 * @return current Task.
	 */
	public Task getCurrentTab(){
		
		return this._currentTab;
	}
	
	/**
	 * Sets current Task as tab.
	 * @param tab
	 */
	public void setCurrentTab(Task tab){
		
		this._currentTab = tab;
	}

	/**
	 * Adds Task task to the task list.
	 * @param task
	 */
	public void addTask(Task task) {
		
		this._tabs.put(task.getName(),task);
	}
	
	/**
	 * Changes a task's description.
	 * @param readTask
	 */
	public void changeTaskDescription(String readTask) {
		
		this._currentTab.setDescription(readTask);		
	}
	
	/**
	 * Prints current task's description to the console.
	 */
	public void printDescription() {
		
		System.out.println(this._currentTab.getDescription());
	}
	
	/**
	 * Gets current task's decription.
	 * @return current task's description
	 */
	public String getCurrentTaskDescription() {
		
		return this._currentTab.getDescription();
	}
	
	/**
	 * Gets current task's assignee.
	 * @return current tab's assignee
	 */
	public String getAssignee() {
		
		return this._currentTab.getAssignee();
	}
	
	/**
	 * Gets the user list as a String.
	 * @return user list string
	 */
	public String printUsers() {
		
		refreshUserList();
		return ""+this._usrs;
	}
	
	/**
	 * Gets the managers list as a String
	 * @return manager list string
	 */
	public String printManagers() {
		
		return ""+this._managers;
	}
	
	/**
	 * Gets the employees list as a String
	 * @return employee list string
	 */
	public String printEmployees() {
		
		return ""+this._employees;
	}
	
	/**
	 * Gets current task's name.
	 * @return current task's name
	 */
	public String getTitle() {
		
		return this._currentTab.getName();
	}
	
	/**
	 * Sets the managers list as the _managers list.
	 * @param _managers
	 */
	public void setManagers(Map<String, Manager> _managers) {
		
		this._managers = _managers;
	}
	
	/**
	 * Sets the employees list as the _employees list.
	 * @param _employees
	 */
	public void setEmployees(Map<String, Employee> _employees) {
		
		this._employees = _employees;
	}
	
	/**
	 * Gets the tasks Map.
	 * @return
	 */
	public Map<String, Task> getTabs() {
		
		return _tabs;
	}
	
	/**
	 * Sets the tabs list as _tabs.
	 * @param _tabs
	 */
	public void setTabs(Map<String, Task> _tabs) {
		
		this._tabs = _tabs;
	}
	
	/**
	 * returns the users map.
	 * @return user list
	 */
	public Map<String, User> getUsers() {
		
		return _usrs;
	}
	
	/**
	 * sets the users map as the map _usrs
	 * @param _usrs
	 */
	public void setUsers(Map<String, User> _usrs) {
		
		this._usrs = _usrs;
	}
	
	/**
	 * Gets task Set
	 * @return task set
	 */
	public Set<Task> getTaskArray() {
		
		return _tsks;
	}
	
	/**
	 * Sets the tasks set as the _tsks set.
	 * @param _tsks
	 */
	public void setTaskArray(Set<Task> _tsks) {
		
		this._tsks = _tsks;
	}
	
	/**
	 * Converts the tasks Map into a Set.
	 * @return tasks set
	 */
	public Set<Task> mapToSet() {
		
		this._tsks = new HashSet<Task>();
		
		for(String key : _tabs.keySet()) {
			this._tsks.add(_tabs.get(key));
		}
		
		return this._tsks;
	}
	
	/**
	 * Converts the files map into a files list
	 * @return files List
	 */
	public List<File> mapToArray() {
		
		this._files = new ArrayList<File>();
		
		for(String key : this.getCurrentTab().getFiles().keySet()) {
			this._files.add(this.getCurrentTab().getFiles().get(key));
		}
		
		return this._files;
	}
	
	/**
	 * clears the tasks set.
	 */
	public void refreshTasks() {
		
		this._tsks.clear();
	}
	
	/**
	 * Deletes the Task which name is currentTab.
	 * @param currentTab
	 */
	public void deleteTask(Task currentTab) {
		
		this._tabs.remove(loadTask(currentTab.getName()));
		this._tsks.remove(loadTask(currentTab.getName()));
		
		System.out.println("Task '"+ currentTab.getName() + "' deleted");
	}
	
	/**
	 * Edits current Task.
	 * @param name
	 * @param description
	 * @param assignee
	 */
	public void editTask(String name, String description, String assignee) {
		
		this.getCurrentTab().setName(name);
		this.getCurrentTab().setDescription(description);
		this.getCurrentTab().setAssignee(findUser(assignee));
		this.getCurrentTab().setLastEditor(this.getCurrentUser());
		this.getCurrentTab().setLastEditDate();
	}
	
	/**
	 * Gets last edition's date
	 * @return last edit date
	 */
	public String getLastEditDate() {
		
		return this.getCurrentTab().getLastEditDate();
	}
	
	/**
	 * Gets the creation's date
	 * @return creation date
	 */
	public String getCreationDate() {
		
		return this.getCurrentTab().getCreationDate();
	}
	
	/**
	 * Checks permissions in the current Task.
	 * @return true or false, depending on whether the user has write permissions on this task or not, respectively.
	 */
	public boolean checkPermission() {
		
		return this._currentTab.checkPermission(this);
	}
	
	/**
	 * Marks current Task as verified by a Manager.
	 */
	public void markAsVerified() {
		
		if (this.getCurrentUser() instanceof Manager){
			this._currentTab.verify();
			this._currentTab.lock();
			
		} else System.out.println("User " + this.getCurrentUser().getID() + "doesn't have the permission necessary" );
	}
	
	/**
	 * Marks current Task as complete.
	 */
	public void markAsComplete() {
		
		if (this.getCurrentUser().getID().equals(this.getCurrentTab().getAuthor()) || this.getCurrentUser().getID().equals(this.getCurrentTab().getAssignee())){
			this._currentTab.done();
			
		}else System.out.println("User " + this.getCurrentUser().getID() + "doesn't have the permission necessary" );
	}

	public void markAsRejected() {
		if (this.getCurrentUser() instanceof Manager){
			this._currentTab.setReject(true);
			this.getCurrentTab().setStatus(false);
			
		} else System.out.println("User " + this.getCurrentUser().getID() + "doesn't have the permission necessary" );
		
	}

	public void deleteFile(File selectedItem) {
		this.getCurrentTab().deleteFile(selectedItem);
		
	}
}
