/*
 * 
 */
package borrowmanager.model.user;

import java.util.*;

import com.google.gson.JsonObject;

/**
 * The Class Manager.
 * @author Marina Delerce & Romain Guillot 
 * @version 1.0.0
 */
public class StockManager extends User {

	/**
	 * Instantiates a new manager.
	 *
	 * @param name the name
	 * @param firstname the firstname
	 * @param login the login
	 * @param password the password
	 */
	public StockManager(Integer id, String name, String firstname, String login, String password) {
		super(id, name, firstname, login, password);
	}
	
	/**
	 * Instantiates a new manager.
	 */
	public StockManager(){super();}
	
	public StockManager(JsonObject json) {
		super(json);
	}

	/* (non-Javadoc)
	 * @see model.user.User#toString()
	 */
	@Override
	public String toString(){
		return "Admin: " + this.getFirstname() + " " + this.getName() + "id: " + this.getLogin();
	}
	
	
	/* (non-Javadoc)
	 * @see model.user.User#getSerializableDescription()
	 */
	@Override
	public HashMap<String, Object> getSerializableDescription(){
		
		HashMap<String, Object> userDescription = new HashMap<String, Object>();
		userDescription.put("name", getName());
		userDescription.put("firstname", getFirstname());
		userDescription.put("login", getLogin());
		userDescription.put("password", getPassword());
		userDescription.put("className", this.getClass().getName());
		
		return userDescription;
	}

	/* (non-Javadoc)
	 * @see model.user.User#setObject(java.util.HashMap)
	 */
	@Override
	public void setObject(HashMap<String, Object> description){
		this.lastname = (String) description.get("name");
		this.firstname = (String) description.get("firstname");
		this.login = (String) description.get("login");
		this.password = (String) description.get("password");
	}
	
	@Override
	public boolean canValidateBookings() {
		return true;
	}
}
