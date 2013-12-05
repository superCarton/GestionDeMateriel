package borrowmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import borrowmanager.booking.Booking;
import borrowmanager.booking.BookingCalendar;
import borrowmanager.booking.DateInterval;
import borrowmanager.element.BorrowableModel;
import borrowmanager.element.BorrowableStack;
import borrowmanager.user.User;

public class Manager {
	private Map<Integer, BookingCalendar> bookings;
	private User currentUser;
	private List<BorrowableStack> stock;
	/**
	 * Users of the Manager
	 */
	private List<User> users;

	public Manager() {
		this.currentUser = null;
		this.bookings = new HashMap<Integer, BookingCalendar>();
		this.stock = new ArrayList<BorrowableStack>();
		this.users = new LinkedList<User>();
	}
	
	public void setUser(User u){
		if(users.contains(u) && this.getUser(u.getId()) != u){
			throw new RuntimeException("This userid is already taken !");
		}
		if(!users.contains(u)){
			users.add(u);
			Collections.sort(users);
		}
		this.currentUser = u;
	}
	
	public User getUser(Integer id){
		for(User u : users){
			if(u.getId() == id){
				return u;
			}
		}
		return null;
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
		
		BookingCalendar calendar = bookings.get(borrowableId);
		
		if (calendar == null) {
			return false;
		}
		
		if(!isAvailable(borrowableId, start, end)){
			return false;
		}
		
		return calendar.book(borrowerId, stock.get(borrowableId), bookingInterval, reason);
	}

	public Boolean isAvailable(Integer borrowableId) {
		BorrowableStack b = getBorrowableById(borrowableId);
		if (b != null) {
			Date now = new Date();
			return isAvailable(borrowableId, now, now);
		}
		return false;
	}

	/**
	 * Returns true if a borrowable is available for a given date interval
	 * @param borrowableId The ID of the borrowable
	 * @param start The start date of the interval
	 * @param end The end date of the interval
	 * @return True if the borrowable is available, false otherwise.
	 */
	public Boolean isAvailable(Integer borrowableId, Date start, Date end) {
		BookingCalendar calendar = bookings.get(borrowableId);
		return calendar.isAvailable(start, end);
	}

	/**
	 * Give back a borrowable.
	 * @param borrowableId The ID of the borrowable to give back.
	 * @return True if the borrowable was given back too late.
	 */
	public Boolean giveBack(Integer borrowableId) {
		// Check that the ID matches some borrowable.
		BookingCalendar calendar = bookings.get(borrowableId);
		if (calendar == null) {
			return false;
		}
		
		// Get the current booking
		Booking b = calendar.getCurrentBooking();
		if (b == null) {
			return false;
		}
		
		// TODO : end() returns false if the borrowable was given back too late.
		return b.end();

	}

	/**
	 * 
	 * @return
	 */
	public List<Booking> getNotYetValidatedBookings() {
		if(!currentUser.canValidateBookings()){return null;}
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
		for (BorrowableStack borrowable : stock) {
			if (borrowable.hasFeature(feature)) {
				descriptions.put(borrowable.getId(), borrowable.getName());
			}
		}
		return descriptions;
	}

	public BorrowableStack getBorrowableById(Integer id) {
		for (BorrowableStack borrowable : stock) {
			if (borrowable.getId() == id) {
				return borrowable;
			}
		}
		return null;
	}
	
	public String getFullDescription(Integer id) {
		BorrowableStack borrowable = getBorrowableById(id);
		// TODO : more data
		return borrowable.getName();
	}
	
	// method to fill the stock with dummy elements for testing
	void fillTemporaryStock(){
		stock.add(new BorrowableStack(0, new BorrowableModel(0, "item0")));
		bookings.put(0, new BookingCalendar());
		
		stock.add(new BorrowableStack(1, new BorrowableModel(1, "item1")));
		bookings.put(1,  new BookingCalendar());
	}
}