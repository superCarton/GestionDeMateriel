/*
 * 
 */
package borrowmanager.model.user;

import java.util.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import borrowmanager.UNUSED_user.UNUSED_User;


/**
 * The Class User.
 * @author Marina Delerce & Romain Guillot 
 * @version 1.0.0
 */
public abstract class User implements Comparable<User> {
	/** The ID **/
	protected Integer id;
	
	/** The lastname. */
	protected String lastname;
	
	/** The firstname. */
	protected String firstname;
	
	/** The login. */
	protected String login;
	
	/** The password. */
	protected String password;
	
	
	/**
	 * Instantiates a new user.
	 *
	 * @param name the name
	 * @param firstname the firstname
	 * @param login the login
	 * @param password the password
	 */
	public User(Integer id, String name, String firstname, String login, String password){
		this.id = id;
		this.lastname = name;
		this.firstname = firstname;
		this.login = login;
		this.password = password;
	}
	
	/**
	 * Instantiates a new user.
	 */
	public User() {}
	
	public User(JsonObject json) {
		fromJSON(json);
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName(){
		return firstname+" "+lastname;
	}
	
	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname(){
		return firstname;
	}
	
	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin(){
		return login;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Sets the object.
	 *
	 * @param description the description
	 */
	public void setObject(HashMap<String, Object> description){
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return lastname + " " + firstname;
	}
	
	public boolean canValidateBookings() {
		return false; // to be overriden by child classes
	}
	
	@Override
	public int compareTo(User arg0) {
		return arg0.id - this.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		User u = (User) obj;
		
		return u.id == this.id && u.getName() == this.getName();
	}

	public JsonElement toJSON() {
		JsonObject json = new JsonObject();
		json.addProperty("className", this.getClass().getSimpleName());
		json.addProperty("id", id);
		json.addProperty("lastname", lastname);
		json.addProperty("firstname", firstname);
		json.addProperty("login", login);
		json.addProperty("password", password);
		return json;
	}
	
	public void fromJSON(JsonObject json) {
		id = json.get("id").getAsInt();
		lastname = json.get("lastname").getAsString();
		firstname = json.get("firstname").getAsString();
		login = json.get("login").getAsString();
		password = json.get("password").getAsString();
	}
}
