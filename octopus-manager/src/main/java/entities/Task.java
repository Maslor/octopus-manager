package main.java.entities;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
 * 
 * Task represents, as the name indicates, a specific task with an identifiable name, author and assignee.
 * @author Gabriel Freire
 */

public class Task implements Serializable{   
	
	private static final long serialVersionUID = -5416272475989116821L;
	private Map<String,File> _files = new HashMap<String,File>();
	private Map<String,Task> _subTasks = new HashMap<String,Task>();
	private Set<Task> _subTasksSet;
	private Set<File> _fileSet;
	private File _file;
	private User _assignee;
	private User _author;
	
	private boolean _status = false;
	private boolean _verified = false;
	
	private Shell _shl;
	private String _name;
	private String _description;
	private User _lastEditor;
	private String comment;
	protected String lastEditDate;
	protected String creationDate;
	protected String verificationDate;
	protected String completionDate;
	
	private boolean LOCKED = false;
	private boolean rejected = false;
	
	/**
	 * Constructor for Standard Tasks
	 * @param shl
	 * @param name
	 * @param description
	 * @param assignee
	 */
	public Task(Shell shl, String name, String description, String assignee){
		
		this._shl = shl;
		this._name = name;
		this._author = shl.getCurrentUser();
		this._description = description;
		this._assignee = shl.findUser(assignee);
		this._lastEditor = shl.getCurrentUser();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		creationDate = dateFormat.format(date);
		lastEditDate = dateFormat.format(date);
		
		shl.addTask(this);		
	}
	
	/**
	 * Constructor for subTasks.
	 * @param shl
	 * @param mainTask
	 * @param name
	 * @param description
	 * @param assignee
	 */
	public Task(Shell shl, Task mainTask, String name, String description, String assignee){
		
		this._shl = shl;
		this._name = name;
		this._author = shl.getCurrentUser();
		this._description = description;
		this._assignee = shl.findUser(assignee);
		this._lastEditor = shl.getCurrentUser();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		creationDate = dateFormat.format(date);
		lastEditDate = dateFormat.format(date);
		
		mainTask.addSubTask(this);		
	}

	/**
	 * Gets the task's Assignee.
	 * @return User assignee
	 */
	public User getAssigneeObject(){
		
		return this._assignee;
	}
	
	/**
	 * Gets the task's Author's ID.
	 * @return author's ID
	 */
	public String getAuthor() {
		
		return this._author.getID();
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		return _name+" - "+_assignee;
	}
	
	/**
	 * Sets the status of the task as LOCKED or UNLOCKED, depending on whether the param status is true or false, respectively.
	 * @param status
	 */
	public void setLock(boolean status) {
		
		setLOCKED(status);
	}
	
	/**
	 * Gets this task's Description
	 * @return description
	 */
	public String getDescription(){
		
		return this._description;
	}
	
	/**
	 * Sets this task's description.
	 * @param description
	 */
	public void setDescription(String description){
		
		this._description = description;
	}

	/**
	 * Deletes selected file.
	 * @param fileName
	 */
	public void deleteFile(String fileName){
		
		_shl.removeFile(_shl.getFile(fileName));
	}
	
	/**
	 * Sets this task's assignee .
	 * @param trabalhador
	 */
	public void setAssignee(User trabalhador) {
		
		this._assignee = trabalhador;
	}
	
	/**
	 * Changes status to the opposite. Locked to unlocked and vice-versa.
	 * @throws Exception
	 */
	public void changeStatus() throws Exception{
		
		if (_shl.getCurrentUser().equals(_assignee) || _shl.getCurrentUser().equals(_author)){
			
			if (this._status == true)
				this._status = false;
			
			else this._status = true;
		}
		
		else throw new Exception("You are neither the creator nor the assignee of this task.");	
	}
	
	/**
	 * Gets this task's assignee ID.
	 * @return assignee ID
	 */
	public String getAssignee(){
		
		return this._assignee.getID();
	}
	
	/**
	 * Gets this task's name.
	 * @return task name
	 */
	public String getName() {
		
		return _name;
	}
	
	/**
	 * Verifies this task.
	 */
	public void verify(){
		
		this._verified = true;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		verificationDate = dateFormat.format(date);	
	}
	
	/**
	 * Sets this task's name.
	 * @param _name
	 */
	public void setName(String _name) {
		
		this._name = _name;
	}
	
	/**
	 * Adds file to the _files map.
	 * @param name
	 * @param f
	 */
	public void addFile(String name, File f) {
		
		this._files.put(name,f);
	} 
	
	/**
	 * Gets last editor User.
	 * @return last editor
	 */
	public User getLastEditor() {
		
		return _lastEditor;
	}
	
	/**
	 * Gets this task's status.
	 * @return true if Completed, false if Ongoing
	 */
	public boolean getStatus(){
		
		return this._status;
	}
	
	/**
	 * Checks if this task is verified by a Manager.
	 * @return true if verified, false if unverified.
	 */
	public boolean getStatusV(){
		
		return this._verified;
	}
	
	/**
	 * Sets last editor.
	 * @param _lastEditor
	 */
	public void setLastEditor(User _lastEditor) {
		
		this._lastEditor = _lastEditor;
	}
	
	/**
	 * Checks if current User has write rights.
	 * @param shl
	 * @return true if he does, false if he doesn't.
	 */
	public boolean checkPermission(Shell shl) {
		
		if(shl.getCurrentUser().getID().equals(this._author.getID()) || shl.getCurrentUser().getID().equals(this._assignee.getID()) || shl.getCurrentUser() instanceof Manager){ 
			return true;
		}
		
		else return false;
	}
	
	/**
	 * Sets this task's comment.
	 * @param text
	 */
	public void setComment(String text) {
		
		this.comment = text;
	}
	
	/**
	 * Gets this task's comment.
	 * @return comment
	 */
	public String getComment() {
		
		return this.comment;
	}
	
	/**
	 * Sets this task as Complete.
	 */
	public void done() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		completionDate = dateFormat.format(date);
		
		this._status=true;
		
	}
	
	/**
	 * Gets this task's File.
	 * @return file
	 */
	public File getFileDemo() {
		
		return _file;
	}
	
	/**
	 * Sets this task's file.
	 * @param file
	 * @throws IOException
	 */
	public void setFileDemo(File file) throws IOException {
		
		this._file = file;
		this._files.put(FileUtils.readFileToString(file), file);
	}
	
	/**
	 * Adds subTask.
	 * @param t
	 */
	public void addSubTask(Task t){
		
		this._subTasks.put(t.getName(),t);
	}
	
	/**
	 * Gets subTask
	 * @param name
	 * @return subTask
	 */
	public Task getSubTask(String name) {
		
		return this._subTasks.get(name);
	}

	/**
	 * Gets this task's files map.
	 * @return file map
	 */
	public Map<String, File> getFiles() {
		
		return this._files;
	}
	
	/**
	 * Locks this task.
	 */
	public void lock() {
		
		this.setLOCKED(true);
	}
	
	/**
	 * Checks if this task is Locked
	 * @return true if locked, false if unlocked.
	 */
	public boolean isLOCKED() {
		
		return LOCKED;
	}
	
	/**
	 * Sets lock status.
	 * @param lOCKED
	 */
	public void setLOCKED(boolean lOCKED) {
		
		LOCKED = lOCKED;
	}
	
	/**
	 * Gets this task's creation date.
	 * @return creation date
	 */
	public String getCreationDate() {
		
		return creationDate;
	}
	
	/**
	 * Gets last this task's last edit date.
	 * @return last edit date.
	 */
	public String getLastEditDate() {
		
		return lastEditDate;
	}
	
	/**
	 * Sets last Edit Date.
	 */
	public void setLastEditDate() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		lastEditDate = dateFormat.format(date);
	}
	
	/**
	 * Converts subTask map to a Set.
	 * @return subTask set.
	 */
	public Set<Task> mapToSet() {
		
		this._subTasksSet = new HashSet<Task>();
		
		for(String key : _subTasks.keySet()) {
			this._subTasksSet.add(_subTasks.get(key));
		}
		
		return this._subTasksSet;
	}
	
	/**
	 * Converts subTask map to a Set.
	 * @return subTask set.
	 */
	public Set<File> fileMapToSet() {
		this._fileSet = null;
		this._fileSet = new HashSet<File>();
		
		for(String key : _files.keySet()) {
			this._fileSet.add(_files.get(key));
		}
		
		return this._fileSet;
	}
	
	public String getVerificationDate() {
		return verificationDate;
	}
	
	public String getCompletionDate() {
		return completionDate;
	}

	public void resetSet() {
		this._fileSet.clear();
		
	}

	public void setStatus(boolean b) {
		this._status = false;
		
	}

	public void setReject(boolean b) {
		this.rejected  = b;
		
	}

	public boolean getRejection() {
		
		return this.rejected;
	}

	public void deleteFile(File selectedItem) {
		//this._files.remove(selectedItem);
	
		this._files.remove(selectedItem);
	
	
	}

	public void clearSet() {
		
		this._fileSet.clear();
		
	} 

	public File getFileN(String n) {
		return this._files.get(n);
	}
	
}
