/*
 * 
 */
package borrowmanager.model.user;

import java.util.*;

import com.google.gson.JsonObject;

/**
 * The Class Borrower.
 * @author Marina Delerce & Romain Guillot 
 * @version 1.0.0
 */
public abstract class Borrower extends User{

	/** The loan duration. */
	protected static int loanDuration;
	
	protected static int maxReservationLength;
	
	/**
	 * Instantiates a new borrower.
	 *
	 * @param name the name
	 * @param firstname the firstname
	 * @param login the login
	 * @param password the password
	 * @param loanDuration the loan duration
	 */
	public Borrower(Integer id, String name, String firstname, String login, String password){
		super(id, name, firstname, login, password);
		setLoanDuration(loanDuration);
	}

	/**
	 * Instantiates a new borrower.
	 */
	public Borrower(){super();}
	
	public Borrower(JsonObject json) {
		super(json);
	}
	/**
	 * Gets the loan duration.
	 *
	 * @return the loan duration
	 */
	public abstract Integer getMaxBookingLength();
	
	
	//protected abstract void setMaxBookingLength(Integer length);

	/**
	 * Sets the loan duration.
	 *
	 * @param loanDuration the new loan duration
	 */
	public static void setLoanDuration(int loanDuration) {
		Borrower.loanDuration = loanDuration;
	}
	
	public abstract Integer getMaxReservationLength() ;
	
	//protected abstract void setMaxReservationLength(Integer length);
	
	/* (non-Javadoc)
	 * @see model.user.User#toString()
	 */
	@Override
	public String toString(){
		return "User: " + this.getFirstname() + " " + this.getName() + "id: " + this.getLogin();
	}
}
