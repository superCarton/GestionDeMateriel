package borrowmanager.model.booking;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.element.BorrowableModel;
import borrowmanager.model.element.BorrowableStack;
import borrowmanager.model.material.Material;
import borrowmanager.model.material.MaterialType;

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

	private MaterialType materialType;

	/**
	 * Default constructor, initializes an empty bookings list
	 */
	public BookingCalendar(MaterialType type) {
		this.bookings = new LinkedList<Booking>();
		this.materialType = type;
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
	 * Returns the maximum number of booked items in a specified date interval.
	 * 
	 * @param dateStart
	 *            The start date
	 * @param dateEnd
	 *            The end date.
	 * @return The maximum number of booked items in a specified date interval.
	 */
	/*
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
	}*/

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
	/*
	public boolean isAvailableInQuantity(Integer stock, Integer quantity,
			Date start, Date end) {
		Integer maxBooked = getMaximumBookedNumberInInterval(start, end);
		System.out.println("Stock = " + stock);
		System.out.println("Quantity = " + quantity);
		System.out.println("maxBooked= " + maxBooked);
		return stock - maxBooked >= quantity;
	}*/
	
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
	public Booking book(Integer borrowerId, List<Material> materials,
			DateInterval interval, String reason) {
		
		//BorrowableStack stack = new BorrowableStack(materialType, quantity);
		Date now = Manager.now;
		// Only create future bookings
		if (!Manager.DEBUG && interval.getEnd().compareTo(now) < 0) {
			return null;
		}
		
		try {
			Booking b = new Booking(borrowerId, materials, interval,reason);
			bookings.add(b);
			Collections.sort(bookings);
			return b;
		} catch (IllegalArgumentException e) {
			return null;
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