package borrowmanager;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import borrowmanager.booking.BookingCalendar;
import borrowmanager.booking.DateInterval;
import borrowmanager.user.User;
import borrowmanager.element.BorrowableG;
import borrowmanager.booking.Booking;

public class Manager {
	private Map<Integer, BookingCalendar> bookings;
	private User currentUser;
	private List<BorrowableG> stock;

	public Manager(User user) {
		this.currentUser = user;
	}

	public Boolean book(Integer borrowableId, Integer borrowerId, Date start, Date end, String reason) {
		// Date verifications
		
		// Time before the beggining of the booking (reservation)
		DateInterval reservationStartInterval = new DateInterval(new Date(), start);
		if (reservationStartInterval.getLength() > currentUser.getMaxReservationLength()) {
			return false;
		}
		
		// Duration of the booking
		DateInterval bookingInterval= new DateInterval(start, end);
		if (bookingInterval.getLength() > currentUser.getMaxBookingLength()) {
			return false;
		}
		
		BookingCalendar calendar = bookings.get(borrowerId);
		
		if (calendar == null) {
			return false;
		}
		
		return calendar.book(borrowerId, currentUser.getId(), bookingInterval, reason);
	}

	public Boolean isAvailable(Integer borrowableId) {
		BorrowableG b = getBorrowableById(borrowableId);
		if (b != null) {
			Date now = new Date();
			return isAvailable(borrowableId, now, now);
		}
		return false;
	}

	public Boolean isAvailable(Integer borrowableId, Date start, Date end) {
		BookingCalendar calendar = bookings.get(borrowableId);
		return calendar.isAvailable(start, end);
	}

	public Boolean giveBack(Integer borrowableId) {
		BookingCalendar calendar = bookings.get(borrowableId);
		if (calendar == null) {
			return false;
		}
		
		Booking b = calendar.getCurrentBooking(); 
		if (b == null) {
			return false;
		}
		
		// TODO : end() returns false if the borrowable was given back too late.
		return b.end();
		
	}

	public List<Booking> getNotYetValidatedBookings() {
		List<Booking> notValidatedBookings = new LinkedList<Booking>();
		for (BookingCalendar calendar : bookings.values()) {
			for(Booking booking : calendar.getBookings()) {
				if (!booking.isValidated()) {
					notValidatedBookings.add(booking);
				}
			}
		}
		return notValidatedBookings;
		
	}

	public Map<Integer, String> getStockDescriptionForFeature(String feature) {
		Map<Integer, String> descriptions = new HashMap<Integer, String>();
		for (BorrowableG borrowable : stock) {
			if (borrowable.hasFeature(feature)) {
				descriptions.put(borrowable.getId(), borrowable.getName());
			}
		}
		return descriptions;
	}

	public BorrowableG getBorrowableById(Integer id) {
		for (BorrowableG borrowable : stock) {
			if (borrowable.getId() == id) {
				return borrowable;
			}
		}
		return null;
	}
	
	public String getFullDescription(Integer id) {
		BorrowableG borrowable = getBorrowableById(id);
		// TODO : more data
		return borrowable.getName();
	}
}