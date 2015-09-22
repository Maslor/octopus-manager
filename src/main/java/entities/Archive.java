package main.java.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import main.java.screens.ControlledScreen;

/**
 * Archive is the class that contains most, if not all, of the methods related to serialization and data storage.
 * @author Gabriel Freire
 *
 */
public abstract class Archive implements ControlledScreen{
	
	private String classForName = "org.sqlite.JDBC";
	private String connectionPath ="jdbc:sqlite:"+getPath("shell.db");
	private String fileOutputPath = getPath("shell.txt");
	private String fileOutputPath2 = getPath("audit.log");
	public String shellPath = getPath("shell.txt");
	
	public boolean justUpdated = false;
	
	private static final Logger logger = LogManager.getLogger(Archive.class.getName());
	
	/**
	 * gets relative Path of the directory where the jar file's being run.
	 * @param name
	 * @return
	 */
	public String getPath(String name) {

		 String basePath = new File("").getAbsolutePath();
		 System.out.printf("Base path: %s - ",basePath); //this is printing a lot of times in a row, don't really see why...

		 String path = new File("./"+name).getAbsolutePath();
		 return path;
	}
	
	/**
	 * saves the Shell object in a text (.txt) file.
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void saveShell() throws IOException, ClassNotFoundException {
		
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(getPath("shell.txt")));
		
		objectOutputStream.writeObject(new Date());
		objectOutputStream.writeBoolean(true);
		objectOutputStream.writeFloat(1.0f);
		
		objectOutputStream.writeObject(shl);
		objectOutputStream.flush();
		objectOutputStream.close();
		System.out.println("Successfully saved");
		
		saveShellDB();	
	}
	
	/**
	 * logs a message into the database containing information of an action.
	 * @param context
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void saveLog(String context) throws ClassNotFoundException, IOException, SQLException {
		
		Class.forName(classForName);
		Connection connection = null;
		logger.info(context);
		
		try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection(connectionPath);
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      
	     File file = new File("audit.log");
	     FileInputStream fis = new FileInputStream(file);

	 
	      PreparedStatement ps = connection.prepareStatement("UPDATE OR REPLACE shell SET audit=? WHERE name='shell.txt'");	
	      
	      ps.setBinaryStream(1, fis, (int)file.length());
	     
	      ps.executeUpdate();
	      ps.close();
	      fis.close();
	
	      System.out.println("SQL save done");
	      
	    } catch(SQLException e) {
	    	
	      // if the error message is "out of memory", 
	      // it probably means no database file is found
	      System.err.println(e.getMessage());
	  
	    } finally {
	    	
	      try
	      {  
	        if(connection != null)
	          connection.close();
	        
	      } catch(SQLException e) {  
	        // connection close failed.
	        System.err.println(e);
	      }
	    }
	}
	
	/**
	 * loads the Log file in order to guarantee the previous logs aren't lost.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void loadLog() throws ClassNotFoundException, SQLException, IOException {
		
		Class.forName(classForName);
	    Connection conn = DriverManager.getConnection(connectionPath);

	    String sql = "SELECT audit FROM shell";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet resultSet = stmt.executeQuery();
	    
	    while (resultSet.next()) {
	      File audit = new File(fileOutputPath2);
	      FileOutputStream fos = new FileOutputStream(audit);

	      byte[] buffer = new byte[1];
	      InputStream is = resultSet.getBinaryStream(1); 

	      while (is.read(buffer) > 0) {
	        fos.write(buffer);
	      }
	      
	      fos.close();
	    }
	    conn.close();
	    
	    System.out.println("SQL Load Done");
	}
	
	/**
	 * saves the shell file in the database as BLOB binary data
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void saveShellDB() throws ClassNotFoundException, IOException {
		
		Class.forName(classForName);
	    Connection connection = null;
	    
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection(connectionPath);
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      
	      File file = new File("shell.txt");
	      FileInputStream fis = new FileInputStream(file);
	      
	      PreparedStatement ps = connection.prepareStatement("INSERT OR REPLACE INTO shell (name,shl) VALUES (?,?)");	
	      ps.setString(1, file.getName());
	      ps.setBinaryStream(2, fis, (int)file.length());
	      ps.executeUpdate();
	      
	      ps.close();
	      fis.close();
	      
	      System.out.println("SQL save done");
	      
	    } catch(SQLException e) {
	    
	      // if the error message is "out of memory", 
	      // it probably means no database file is found
	      System.err.println(e.getMessage());
	    }
	    
	    finally
	    {
	      try
	      {
	        if(connection != null)
	          connection.close();
	        
	      } catch(SQLException e) {
	        // connection close failed.
	        System.err.println(e);
	      }
	    }
	    
	  }
	
	/**
	 * loads the shell object from the database into a file.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void loadShellDB() throws ClassNotFoundException, SQLException, IOException {
		
		Class.forName(classForName);
	    Connection conn = DriverManager.getConnection(connectionPath);

	    String sql = "SELECT name, shl FROM shell";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet resultSet = stmt.executeQuery();
	    
	    while (resultSet.next()) {
	      File shl = new File(fileOutputPath);
	      FileOutputStream fos = new FileOutputStream(shl);

	      byte[] buffer = new byte[1]; 	
	      InputStream is = resultSet.getBinaryStream(2);
	      
	      while (is.read(buffer) > 0) {
	        fos.write(buffer);
	      }
	      fos.close();
	    }
	    conn.close();
	    
	    System.out.println("SQL Load Done");
	  }	
	
	/**
	 * loads the shell object from a specific file into the application
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Shell loadShell() throws  ClassNotFoundException, SQLException, IOException {
		
		loadShellDB();
		ObjectInputStream objectInputStream;
		
		try {
			objectInputStream = new ObjectInputStream(
			        new FileInputStream(getPath("shell.txt")));
			Date date = (Date) objectInputStream.readObject();
			System.out.println(date);
			System.out.println(objectInputStream.readBoolean());
			System.out.println(objectInputStream.readFloat());
			 
			Shell readShell = (Shell) objectInputStream.readObject();
			System.out.println("Shell Loaded");
			
			objectInputStream.close();
			System.out.println("Object output stream closed");
			shl.setEmployees(readShell.getEmployees());
			shl.setManagers(readShell.getManagers());
			shl.setTabs(readShell.getTabs());
			shl.setUsers(readShell.getUsers());
			
			System.out.println("All Attributes Loaded");
			return readShell;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Shell();	
	}
	
}
