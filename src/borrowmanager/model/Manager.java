package borrowmanager.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import borrowmanager.model.booking.Booking;
import borrowmanager.model.booking.DateInterval;
import borrowmanager.model.element.BorrowableModel;
import borrowmanager.model.element.BorrowableStock;
import borrowmanager.model.user.Borrower;
import borrowmanager.model.user.User;
import borrowmanager.model.user.UsersManager;

public class Manager {
	//private Map<Integer, BookingCalendar> bookings;
	private User currentUser;
	private Map<Integer, BorrowableStock> stock;
	
	/**
	 * Users of the Manager
	 */
	private List<User> users;
	
	private UsersManager usersManager;

	public Manager() {
		this.usersManager = new UsersManager();
		this.currentUser = null;
		//this.bookings = new HashMap<Integer, BookingCalendar>();
		this.users = new LinkedList<User>();
		this.stock = new HashMap<Integer, BorrowableStock>();
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
	
	public Boolean book(Integer borrowableId, Integer quantity,
			Integer borrowerId, Date start, Date end, String reason) {
		if (! (currentUser instanceof Borrower)) {
			return false;
		}
		
		Borrower borrower = (Borrower) currentUser;
		
		// Date verifications
		
		// Time before the beggining of the booking (reservation)
		DateInterval reservationStartInterval = new DateInterval(new Date(), start);
		if (reservationStartInterval.getLength() > borrower.getMaxReservationLength()) {
			return false;
		}
		
		// Duration of the booking
		DateInterval bookingInterval= new DateInterval(start, end);
		if (bookingInterval.getLength() > borrower.getMaxBookingLength()) {
			return false;
		}
		
		BorrowableStock stock = this.stock.get(borrowableId);
		
		if (stock == null) {
			return false;
		}
		
		if (!stock.isAvailable(quantity, start, end)){
			return false;
		}
		
		return stock.getCalendar().book(borrowerId, quantity, bookingInterval, reason);
	}

	public Boolean isAvailable(Integer borrowableId, Integer quantity) {
		BorrowableStock b = this.stock.get(borrowableId);
		if (b != null) {
			Date now = new Date();
			return isAvailable(borrowableId, quantity, now, now);
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
	public Boolean isAvailable(Integer borrowableId, Integer quantity, Date start, Date end) {
		BorrowableStock stock = this.stock.get(borrowableId);
		System.out.println("stock.isAvailable (from Manager.isAvailable long signature)");
		if (stock != null) {
			return stock.isAvailable(quantity, start, end);
		}
		System.out.println("STOCK NULL!");
		return false;
	}
	
	/*
	// TODO : check
	public Boolean isAvailableInQuantity(Integer borrowableId, Integer quantity, Date start, Date end) {
		BookingCalendar calendar = bookings.get(borrowableId);
		BorrowableStack borrowableStack = getBorrowableById(borrowableId);
		return calendar.isAvailableInQuantity(borrowableStack.getQuantity(), quantity, start, end);
	}*/

	/**
	 * Give back a borrowable.
	 * @param borrowableId The ID of the borrowable to give back.
	 * @return True if the borrowable was given back too late.
	 */
	public Boolean giveBack(Booking booking) {
		return booking.end();
	}
	
	public void save() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Booking> getNotYetValidatedBookings() {
		if(!currentUser.canValidateBookings()){
			return null;
		}
		
		List<Booking> notValidatedBookings = new LinkedList<Booking>();
		for (BorrowableStock stock: this.stock.values()) {
			for(Booking booking : stock.getCalendar().getBookings()) {
				if (!booking.isValidated()) {
					notValidatedBookings.add(booking);
				}
			}
		}
		return notValidatedBookings;	
	}

	public Map<Integer, String> getStockDescriptionForFeature(String feature) {
		Map<Integer, String> descriptions = new HashMap<Integer, String>();
		for (BorrowableStock borrowable : this.stock.values()) {
			if (borrowable.hasFeature(feature)) {
				descriptions.put(borrowable.getId(), borrowable.getName());
			}
		}
		return descriptions;
	}

	public BorrowableStock getBorrowableStockById(Integer id) {
		return this.stock.get(id);
	}
	
	public String getFullDescription(Integer id) {
		BorrowableStock borrowableStock = getBorrowableStockById(id);
		// TODO : more data
		return borrowableStock.getName();
	}
	
	/**
	 * Returns the data representing the stock. The Integer is the ID of the BorrowableModel
	 * and the String is its name.
	 * @return The data representing the stock.
	 */
	// TODO : fix this
	/*
	public Map<Integer, String> getAvailableStockData() {
		Map<Integer, String> res = new HashMap<Integer, String>();
		for (BorrowableStack b : stock2) {
			res.put(b.getId(), b.getName());
		}
		return res;
	}*/
	
	public Map<Integer, BorrowableStock> getStock() {
		return stock;
	}
	
	public Collection<BorrowableStock> getStockList() {
		return stock.values();
	}
	
	// method to fill the stock with dummy elements for testing
	public void fillTemporaryStock() {
		BorrowableStock stockA = new BorrowableStock(new BorrowableModel(0, "item0"), 1); 
		stock.put(stockA.getId(), stockA);
		BorrowableStock stockB = new BorrowableStock(new BorrowableModel(1, "item1"), 2);
		stock.put(stockB.getId(), stockB);
	}
	
	public List<Booking> getBookings() {
		List<Booking> list = new LinkedList<Booking>();
		for(Integer borrowableID : this.stock.keySet()) {
			BorrowableStock stock = this.stock.get(borrowableID);
			list.addAll(stock.getCalendar().getBookings());
		}
		return list;
	}
	
	
	/**
	 * Returns the list of the bookings of a user.
	 * @param userId
	 * @return
	 */
	public List<Booking> getUserActiveBookings(Integer userId) {
		List<Booking> list = new LinkedList<Booking>();
		for(Integer borrowableID : this.stock.keySet()) {
			BorrowableStock stock = this.stock.get(borrowableID);
			for(Booking b : stock.getCalendar().getBookings()) {
				if (b.getBorrowerId() == userId && !b.isFinished()) {
					list.add(b);
				}
			}
		}
		return list;
	}

	public User getUserByName(String s) {
		for(User u : users) {
			if (u.getName().toLowerCase().equals(s.toLowerCase())) {
				return u;
			}
		}
		return null;
	}

	public Integer getIDAutoIncrement() {
		int max = -1;
		for (User u : users) {
			max = Math.max(u.getId()+1, max);
		}
		return max;
	}
	
	
}