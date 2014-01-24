/*
 * 
 */
package borrowmanager.model.user;

import java.util.*;

import com.google.gson.JsonObject;

/**
 * The Class Teacher.
 * @author Marina Delerce & Romain Guillot 
 * @version 1.0.0
 */
public class Teacher extends Borrower{
	
	/** The Constant loanDuration. */
	private final static Integer maxBookingLength = 15;
	private static final Integer maxReservationLength = 7;
	
	/**
	 * Instantiates a new teacher.
	 *
	 * @param name the name
	 * @param firstname the firstname
	 * @param login the login
	 * @param password the password
	 */
	public Teacher(Integer id, String name, String firstname, String login, String password){
		super(id, name, firstname, login,password);
	}
	
	/**
	 * Instantiates a new teacher.
	 */
	public Teacher(){super();}
	
	public Teacher(JsonObject json) {
		super(json);
	}

	@Override
	public Integer getMaxBookingLength() {
		return maxBookingLength;
	}
	
	@Override
	public Integer getMaxReservationLength() {
		return maxReservationLength;
	}
}
