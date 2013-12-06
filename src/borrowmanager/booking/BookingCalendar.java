package borrowmanager.booking;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import borrowmanager.element.BorrowableStack;

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
	 * 
	 * @return The List containing all the bookings currently in the calendar
	 */
	public List<Booking> getBookings() {
		return bookings;
	}

	/**
	 * Check if an interval is available for booking.
	 * 
	 * @param dateStart
	 *            Start of the interval
	 * @param dateEnd
	 *            End of the interval
	 * @return Is this interval available?
	 */
	public Boolean isAvailable(Integer quantity, Date dateStart, Date dateEnd) {
		// Create a temporary date interval
		//
		DateInterval w = new DateInterval(dateStart, dateEnd);

		// Go through the list of bookings
		//
		for (Booking b : bookings) {
			// Return false if the two interval overlaps
			//
			if (b.overlaps(w)) {
				return false;
			}
		}
		// No overlaps, interval is available
		//
		return true;
	}

	/**
	 * Returns the maximum number of booked items in a specified date interval.
	 * 
	 * @param dateStart
	 *            The start date
	 * @param dateEnd
	 *            The end date.
	 * @return The maximum number of booked items in a specified date interval.
	 */
	private Integer getMaximumBookedNumberInInterval(Date dateStart,
			Date dateEnd) {
		// Create a temporary date interval
		//
		DateInterval w = new DateInterval(dateStart, dateEnd);

		Integer max = 0;
		// Go through the list of bookings
		//
		for (Booking b : bookings) {
			// Return false if the two interval overlaps
			//
			if (b.overlaps(w)) {
				max = Math.max(max, b.getBorrowableStack().getQuantity());
			}
		}
		return max;
	}

	/**
	 * Returns true if a given quantity of the item is available for a given
	 * date interval and from a stock quantity.
	 * 
	 * @param stock
	 *            The quantity available in stock
	 * @param quantity
	 *            The quantity to book
	 * @param start
	 *            The start date
	 * @param end
	 *            The end date
	 * @return The availability in quantity
	 */
	public boolean isAvailableInQuantity(Integer stock, Integer quantity,
			Date start, Date end) {
		Integer maxBooked = getMaximumBookedNumberInInterval(start, end);
		System.out.println("Stock = " + stock);
		System.out.println("Quantity = " + quantity);
		System.out.println("maxBooked= " + maxBooked);
		return stock - maxBooked >= quantity;
	}

	/**
	 * Book a new interval in the calendar.
	 * 
	 * @param borrowerId
	 *            The id of the user borrowing the item
	 * @param borrowableId
	 *            The id of the borrowed item
	 * @param interval
	 *            The booking interval
	 * @return Is the booking possible?
	 */
	public Boolean book(Integer borrowerId, BorrowableStack borrowableStack,
			DateInterval interval, String reason) {
		try {
			Booking b = new Booking(borrowerId, borrowableStack, interval,
					reason);
			bookings.add(b);
			Collections.sort(bookings);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Returns the current booking for that item (and the associated
	 * BookingCalendar)
	 * 
	 * @return The current booking
	 */
	public Booking getCurrentBooking() {
		// Go through the Booking list
		//
		for (Booking b : bookings) {
			// Return the interval if it's current
			//
			if (b.isCurrent()) {
				return b;
			}
		}

		// No booking found
		//
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		return bookings.equals(((BookingCalendar) obj).getBookings());
	}

	@Override
	public String toString() {
		String s = "Booking calendar (" + bookings.size() + " items):\n";
		for (Booking b : bookings) {
			s += b.toString();
		}
		return s;
	}
}