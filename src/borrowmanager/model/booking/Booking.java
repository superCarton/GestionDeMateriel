package borrowmanager.model.booking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.element.BorrowableModel;
import borrowmanager.model.element.BorrowableStack;
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
	
	private BorrowableStack borrowableStack;
	
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
	private Boolean isReturned;
	
	/**
	 * Boundaries of the booking
	 */
	public DateInterval interval;
	
	private List<Reminder> reminders;
	
	/**
	 * Constructs a booking
	 * @param borrower	The ID of the user borrowing the item
	 * @param borrowable	The ID of the borrowed item
	 * @param interval	The interval (boundaries) of the booking
	 * @param reason	Why is the booking made
	 */
	public Booking(Integer borrower, BorrowableStack stack, DateInterval interval, String reason){
		// Check the validity of the IDs
		//
		if(borrower == null ||  interval == null){
			throw new IllegalArgumentException(StringConfig.ERROR_BOOKING_INVALID);
		}
		
		// Store the properties
		//
		this.borrowerId = borrower;
		this.borrowableStack = stack;
		this.reason = reason;
		this.interval = interval;
		this.isReturned = false;
		this.reminders = new LinkedList<Reminder>();
		
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
		Date now = Manager.now ;
		return !isReturned && interval.isLate(now);
	}
	
	/**
	 * Checks if the booking is current (contains today's date)
	 * @return	Does the booking contain today?
	 */
	public boolean isCurrent(){
		Date now = Manager.now;
		return interval.contains(now);
	}
	
	/**
	 * Finishes the booking (sets the end date to today) if this booking isn't late
	 * @return	Is the booking late?
	 */
	public void end(){
		this.interval.end();
		this.isReturned = true;
		
		//return isLate();
	}
	
	public boolean wasAReminderSendToday() {
		Reminder last = getLastReminder();
		if (last == null) return false;
		// Calculate the time difference (in ms) since the last reminder
		long diff = Manager.now.getTime() - last.getDate().getTime();
		// Convert from ms to hours. The threshold is 24
		return diff / 1000 / 60 < 24;
	}
	
	/**
	 * Adds a reminder to the booking.
	 */
	public void addReminder() {
		// Error if a reminder was already sent in the last 24h hours.
		if (wasAReminderSendToday()) {
			throw new RuntimeException("A reminde has been already sent to that user. Wait tomorrow.");
		}
		
		reminders.add(new Reminder(Manager.now));
	}
	
	/**
	 * Returns the list of the reminders sent for this booking.
	 * @return
	 */
	public List<Reminder> getReminders() {
		return reminders;
	}
	
	/**
	 * Returns the last reminder sent for this booking.
	 * @return
	 */
	public Reminder getLastReminder() {
		if (reminders.size() == 0) return null;
		return reminders.get(reminders.size()-1);
	}
	
	/**
	 * Returns true if the booking is over (item was returned)
	 */
	public boolean isReturned(){
		return isReturned;
	}
	
	public boolean isActive() {
		// In the date interval and not returned
		if (isCurrent() && !isReturned()) {
			return true;
		}
		// The end date is passed but the item was not returned
		else if (isLate()) {
			return true;
		}
		return false;
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
	/*
	public BorrowableStack getBorrowableStack() {
		return borrowables;
	}*/

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
	
	public BorrowableStack getBorrowableStack() {
		return borrowableStack;
	}
	
	public Integer getQuantity() {
		return borrowableStack.getQuantity();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Booking(borrowerId, borrowableStack, interval, reason);
	}
	
	@Override
	public boolean equals(Object obj) {
		Booking b = (Booking) obj;
		return this.borrowerId.equals(b.borrowerId) && this.interval.equals(b.interval);
	}
	
	@Override
	public String toString() {
		return "Booking for item #" + borrowableStack.getId() 
				+ "\nThis item has been booked by user n#" + borrowerId
				+ "\nFor: " + reason + "\nThis booking has " + ((isValidated)?"":"not ")
				+ "been validated by a manager\n" + interval.toString();
	}
	
	public String toListString(SimpleDateFormat format) {
		String startString = format.format(getInterval().getStart());
		String endString = format.format(getInterval().getEnd());
		String notValidatedStr = !isValidated ? "[NOT VALIDATED] " : "";
		String late = isLate() ? "[LATE !] ":"";
		return notValidatedStr+late+borrowableStack.getName()+" x"+getQuantity()+" ["+startString+" - "+endString+"] | Details: "+getReason();
	}

	@Override
	public int compareTo(Booking o) {
		return this.interval.compareTo(o.interval);
	}

	/**
	 * Returns true if the booking is in the future
	 * @return
	 */
	public boolean isFuture() {
		return interval.startsAfter(Manager.now);
	}
	
	/**
	 * Returns true if the booking is a reservation
	 * @return
	 */
	public boolean isReservation() {
		return isFuture();
	}
}