package borrowmanager.booking;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * The BookingCalendar holds the list of all the Bookings.
 * 
 * @author Franck Dechavanne
 * 
 */
public class BookingCalendar {
	/**
	 * Current bookings
	 */
	private List<Booking> bookings;

	/**
	 * Default constructor, initializes an empty bookings list
	 */
	public BookingCalendar() {
		bookings = new LinkedList<Booking>();
	}

	/**
	 * Returns the list of bookings
	 * @return	The List containing all the bookings currently in the calendar
	 */
	public List<Booking> getBookings() {
		return bookings;
	}

	/**
	 * Check if an interval is available for booking.
	 * @param dateStart	Start of the interval
	 * @param dateEnd	End of the interval
	 * @return	Is this interval available?
	 */
	public Boolean isAvailable(Date dateStart, Date dateEnd) {
		// Create a temporary date interval
		//
		DateInterval w = new DateInterval(dateStart, dateEnd);
		
		// Go through the list of bookings
		//
		for(Booking b : bookings){
			// Return false if the two interval overlaps
			//
			if(b.overlaps(w)){
				return false;
			}
		}
		// No overlaps, interval is available
		//
		return true;
	}

	/**
	 * Book a new interval in the calendar.
	 * @param borrowerId	The id of the user borrowing the item
	 * @param borrowableId	The id of the borrowed item
	 * @param start	The start of the booking
	 * @param end	The end of the booking
	 * @return	Is the booking possible?
	 */
	public Boolean book(Integer borrowerId, Integer borrowableId, Date start, Date end) {
		throw new UnsupportedOperationException();
	}
}