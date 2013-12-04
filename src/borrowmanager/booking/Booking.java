package borrowmanager.booking;

import java.util.Date;

import borrowmanager.util.StringConfig;

/**
 * The Booking class represents a booking made by a user for a certain item.
 * @author Franck Dechavanne
 *
 */
public class Booking implements Comparable<Booking> {
	/**
	 * ID of the user borrowing the item
	 */
	private Integer borrowerId;
	
	/**
	 * ID of the borrowed item
	 */
	private Integer borrowableId;
	
	/**
	 * Why is the item booked? (typically a class name)
	 */
	private String reason;
	
	/**
	 * Is the booking validated by a manager?
	 */
	private Boolean isValidated;
	
	/**
	 * Is the booking finished (item was returned)
	 */
	private Boolean isFinished;
	
	/**
	 * Boundaries of the booking
	 */
	public DateInterval interval;
	
	/**
	 * Constructs a booking
	 * @param borrower	The ID of the user borrowing the item
	 * @param borrowable	The ID of the borrowed item
	 * @param interval	The interval (boundaries) of the booking
	 * @param reason	Why is the booking made
	 */
	public Booking(Integer borrower, Integer borrowable, DateInterval interval, String reason){
		// Check the validity of the IDs
		//
		if(borrower == null || borrowable == null || interval == null){
			throw new IllegalArgumentException(StringConfig.ERROR_BOOKING_INVALID);
		}
		
		// Store the properties
		//
		this.borrowerId = borrower;
		this.borrowableId = borrowable;
		this.reason = reason;
		this.interval = interval;
		this.isFinished = false;
		
		this.isValidated = false;
	}

	/**
	 * Checks if an interval overlaps with this booking
	 * @param interval	The interval to compare to
	 * @return	Is the interval overlapping this booking
	 */
	public boolean overlaps(DateInterval interval) {
		return this.interval.overlaps(interval);
	}

	/**
	 * Is the booking validated by a manager of the booking system
	 * @return	Is the booking validated
	 */
	public boolean isValidated() {
		return isValidated;
	}
	
	/**
	 * Validate the booking
	 */
	public void validate(){
		isValidated = true;
	}
	
	/**
	 * Checks the end date of the booking against today's date and returns true<br/>
	 * if the booking reached its end
	 * @return	Is the booking late?
	 */
	public boolean isLate(){
		Date now = new Date();
		return !isFinished && interval.isLate(now);
	}
	
	/**
	 * Checks if the booking is current (contains today's date)
	 * @return	Does the booking contain today?
	 */
	public boolean isCurrent(){
		Date now = new Date();
		return interval.contains(now);
	}
	
	/**
	 * Finishes the booking (sets the end date to today) if this booking isn't late
	 * @return	Is the booking late?
	 */
	public boolean end(){
		if(!isLate()){
			this.interval.end();
			this.isFinished = true;
			return false;
		}
		return true;
	}
	
	/**
	 * Is the booking over (item was returned)
	 */
	public boolean isFinished(){
		return this.isFinished();
	}

	/**
	 * Returns the ID of the user borrowing the item
	 * @return	The ID of the user borrowing the item
	 */
	public Integer getBorrowerId() {
		return borrowerId;
	}

	/**
	 * Returns the ID of the borrowed item
	 * @return	The ID of the borrowed item
	 */
	public Integer getBorrowableId() {
		return borrowableId;
	}

	/**
	 * Returns the justification of the booking
	 * @return	The reason why this item has been booked
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Returns the time interval
	 * @return	The DateInterval for this booking
	 */
	public DateInterval getInterval() {
		return interval;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Booking(borrowerId, borrowableId, interval, reason);
	}
	
	@Override
	public boolean equals(Object obj) {
		Booking b = (Booking) obj;
		return this.borrowerId.equals(b.borrowerId) && this.interval.equals(b.interval);
	}
	
	@Override
	public String toString() {
		return "Booking for item n°" + borrowableId 
				+ "\nThis item has been booked by user n°" + borrowerId
				+ "\nFor: " + reason + "\nThis booking has " + ((isValidated)?"":"not ")
				+ "been validated by a manager\n" + interval.toString();
	}

	@Override
	public int compareTo(Booking o) {
		return this.interval.compareTo(o.interval);
	}
}