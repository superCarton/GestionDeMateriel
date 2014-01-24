/*
 * 
 */
package borrowmanager.model.user;

import java.util.*;

import com.google.gson.JsonObject;

/**
 * The Class Student.
 * @author Marina Delerce & Romain Guillot 
 * @version 1.0.0
 */
public class Student extends Borrower {
	private static final Integer maxBookingLength = 7;
	private static final Integer maxReservationLength = 7;
	
	/**
	 * Instantiates a new student.
	 *
	 * @param name the name
	 * @param firstname the firstname
	 * @param login the login
	 * @param password the password
	 */
	public Student(Integer id, String name, String firstname, String login, String password){
		super(id, name, firstname, login, password);
	}
	
	/**
	 * Instantiates a new student.
	 */
	public Student(){super();}
	
	public Student(JsonObject json) {
		super(json);
	}

	@Override
	public Integer getMaxReservationLength() {
		return maxReservationLength;
	}

	@Override
	public Integer getMaxBookingLength() {
		return maxBookingLength;
	}
}
