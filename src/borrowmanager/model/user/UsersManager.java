/*
 * 
 */
package borrowmanager.model.user;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import borrowmanager.util.DataXML;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The Class Users.
 * @author Marina Delerce & Romain Guillot 
 * @version 1.0.0
 */
public class UsersManager {

	/** The users. */
	private List<User> users;
	
	/** The save file path. */
	private final String SAVE_FILE_PATH = "users";
	
	/** The save file version. */
	private final String SAVE_FILE_VERSION = "1.0.0";
	
	/**
	 * Instantiates a new users.
	 */
	public UsersManager(){
		users = new LinkedList<User>();
	}
	
	public UsersManager(JsonObject json) {
		this();
		fromJSON(json);
	}

	/**
	 * Returns the list of all users.
	 * @return
	 */
	public List<User> getAllUsers() {
		return users;
	}
	
	/**
	 * Get user by their ID.
	 * @param id The user ID
	 * @return The user
	 */
	public User getUser(Integer id){
		for(User u : users){
			if(u.getId() == id){
				return u;
			}
		}
		return null;
	}
	
	/**
	 * Get user by their login
	 * @param s The user login
	 * @return
	 */
	public User getUserByLogin(String s) {
		for(User u : users) {
			if (u.getLogin().toLowerCase().equals(s.toLowerCase())) {
				return u;
			}
		}
		return null;
	}
	
	/**
	 * Adds a user
	 *
	 * @param user the user
	 */
	public void add(User user){
		users.add(user);
	}
	
	/**
	 * Removes a user
	 *
	 * @param user the user
	 */
	public void remove(User user){
		users.remove(user);
	}
	
	/**
	 * Check same user.
	 *
	 * @param login the login
	 * @return true, if successful
	 */
	public boolean checkSameUser(String login) {
		for (User user : users) {
			if (user.getLogin().equals(login))
				return true;
		}
		return false;
	}
	
	/**
	 * Check user password.
	 *
	 * @param login the login
	 * @param password the password
	 * @return the user
	 */
	public User checkUserPassword(String login, String password) {
		for (User user : users) {
			if ((user.getLogin().equals(login))
					&& (user.getPassword().equals(password))) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Delete user.
	 *
	 * @param lastname the lastname
	 * @param firstname the firstname
	 * @param login the login
	 * @return true, if successful
	 */
	public boolean deleteUser(String lastname, String firstname, String login) {
		User deletedUser = null;
		boolean isDeleted = true;
		for(User user : users){
			if (user.getName().equals(lastname) && user.getFirstname().equals(firstname) && user.getLogin().equals(login))
				deletedUser = user;
		}
		if (deletedUser != null) 
			this.users.remove(deletedUser);
		else  
			isDeleted = false;
		
		return isDeleted;
	}
	
	/**
	 * Load.
	 */
	public void load(){
		List<HashMap<String, Object>> listUsers = (List<HashMap<String, Object>>) DataXML.load(SAVE_FILE_PATH, SAVE_FILE_VERSION);
		for (HashMap<String, Object> descriptionUser : listUsers){
			User user = (User) createObject((String) descriptionUser.get("className"));
			user.setObject(descriptionUser);
			users.add(user);
		}
		System.out.println("users loaded");
	}
	
	/**
	 * Save.
	 */
	public void save(){
		
		// save users in the save file
		DataXML.store(getSerializableDescription(), SAVE_FILE_PATH, SAVE_FILE_VERSION);
	}
	
	/**
	 * Gets the serializable description.
	 *
	 * @return the serializable description
	 */
	public List<HashMap<String, Object>> getSerializableDescription(){
		
		List<HashMap<String, Object>> usersDescription = new LinkedList<HashMap<String, Object>>();
		
		for (User user : users){
			usersDescription.add(user.getSerializableDescription());
		}
		
		return usersDescription;
	}
	
	/**
	 * Creates the object.
	 *
	 * @param className the class name
	 * @return the object
	 */
	static Object createObject(String className) {
		 
		Object object = null;
		try {
		    Class<?> classDefinition = Class.forName(className);
		    object = classDefinition.newInstance();
	    } catch (InstantiationException e) {
		          System.out.println(e);
		} catch (IllegalAccessException e) {
		          System.out.println(e);
		} catch (ClassNotFoundException e) {
		          System.out.println(e);
		}
		return object;
	}

	/**
	 * Returns a unused auto incremented user ID
	 * @return
	 */
	public Integer getIDAutoIncrement() {
		int max = -1;
		for (User u : users) {
			max = Math.max(u.getId()+1, max);
		}
		return max;
	}

	public JsonElement toJSON() {
		JsonObject json = new JsonObject();
		JsonArray list = new JsonArray();
		json.add("list", list);
		for (User u : users) {
			list.add(u.toJSON());
		}
		return json;
	}
	
	public void fromJSON(JsonObject json) {
		for (JsonElement j : json.get("list").getAsJsonArray()) {
			// TODO
			User u;
			JsonObject jo = j.getAsJsonObject();
			String className = jo.get("className").getAsString();
			if (className.equals("Student")) {
				u = new Student(jo);
			}
			else if (className.equals("Teacher")) {
				u = new Teacher(jo);
			}
			else if (className.equals("StockManager")) {
				u = new StockManager(jo);
			}
			else {
				throw new RuntimeException("Class '"+className+"' not found!");
			}
			
			users.add(u);
		}
	}
}
