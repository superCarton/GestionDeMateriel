package borrowmanager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import borrowmanager.booking.BookingCalendar;
import borrowmanager.user.User;
import borrowmanager.element.BorrowableG;
import borrowmanager.booking.Booking;

public class Manager {
	private Map<Integer, BookingCalendar> bookings;
	private User currentUser;
	private List<BorrowableG> stock;

	public Manager(User user) {
		throw new UnsupportedOperationException();
	}

	public Boolean book(Integer borrowableId, Integer borrowerId, Date start, Date end) {
		throw new UnsupportedOperationException();
	}

	public Boolean isAvailable(Integer borrowableId) {
		throw new UnsupportedOperationException();
	}

	public void isAvailable(Integer borrowableId, Date start, Date end) {
		throw new UnsupportedOperationException();
	}

	public Boolean giveBack(Integer borrowableId) {
		throw new UnsupportedOperationException();
	}

	public List<Booking> getNotYetValidatedBookings() {
		throw new UnsupportedOperationException();
	}

	public Map<Integer, String> getStockDescriptionForFeature(String feature) {
		throw new UnsupportedOperationException();
	}

	public void getFullDescription(Integer id) {
		throw new UnsupportedOperationException();
	}
}