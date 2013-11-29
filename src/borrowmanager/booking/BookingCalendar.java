package borrowmanager.booking;

import java.util.Date;
import java.util.List;

public class BookingCalendar {
	/**
	 * Integer: borrower id
	 */
	private List<Booking> bookings;

	public Boolean isAvailable(Object date_start, Object date_end) {
		throw new UnsupportedOperationException();
	}

	public Boolean book(Integer borrowerId, Date start, Date end) {
		throw new UnsupportedOperationException();
	}
}