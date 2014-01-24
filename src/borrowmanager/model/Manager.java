package borrowmanager.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import borrowmanager.model.booking.Booking;
import borrowmanager.model.booking.DateInterval;
import borrowmanager.model.element.BorrowableStock;
import borrowmanager.model.element.State;
import borrowmanager.model.material.Material;
import borrowmanager.model.material.OS;
import borrowmanager.model.material.SmartPhone;
import borrowmanager.model.user.Borrower;
import borrowmanager.model.user.StockManager;
import borrowmanager.model.user.Student;
import borrowmanager.model.user.User;
import borrowmanager.model.user.UsersManager;
import borrowmanager.util.FileUtils;

public class Manager {
	private String filePath;
	public static final boolean DEBUG = true;
	public static Date now = new Date();
	private User activeUser;
	private Map<Integer, BorrowableStock> stock;
	
	/**
	 * Users Manager
	 */
	private UsersManager usersManager;

	public Manager() {
		this("data.json");
	}
	
	public Manager(String path) {
		boolean DEBUG_LoadFile = false;
		this.filePath = path;
		this.usersManager = new UsersManager(this);
		this.activeUser = null;
		this.stock = new HashMap<Integer, BorrowableStock>();
		
		File f = new File(filePath);
		if (DEBUG_LoadFile && f.exists()) {
			load();
		}
		else {
			fillTemporaryStock();
			save();
		}
	}
	
	public UsersManager getUsersManager() {
		return usersManager;
	}
	
	/**
	 * Sets the current user of the app
	 * @param u The user
	 */
	public void setActiveUser(User u){
		/*if(usersManager.getUser(u.getId()) != u){
			throw new RuntimeException("This userid is already taken !");
		}*/
		
		/*if(!users.contains(u)){
			users.add(u);
			Collections.sort(users);
		}*/
		
		this.activeUser = u;
	}
	
	public User getActiveUser() {
		return activeUser;
	}
	
	public Booking book(Integer borrowableId, Integer quantity,
			Integer borrowerId, Date start, Date end, String reason) {
		if (! (activeUser instanceof Borrower)) {
			throw new RuntimeException("Active user "+activeUser+" is not a borrower");
			//return false;
		}
		
		Borrower borrower = (Borrower) activeUser;
		
		// Date verifications
		
		// Time before the beggining of the booking (reservation)
		// TODO : put this back !
		if (!DEBUG) {
			DateInterval reservationStartInterval = new DateInterval(new Date(), start);
			if (reservationStartInterval.getLength() > borrower.getMaxReservationLength()) {
				throw new RuntimeException("User cant do a reservation more than "+borrower.getMaxReservationLength()+" days ahead ! (here:"+reservationStartInterval.getLength()+")");
				//return false;
			}
		}
		
		// Duration of the booking
		DateInterval bookingInterval= new DateInterval(start, end);
		if (bookingInterval.getLength() > borrower.getMaxBookingLength()) {
			throw new RuntimeException("User cant book something for more than"+borrower.getMaxBookingLength()+" consecutives days! (here:"+bookingInterval.getLength()+")");
			//return false;
		}
		
		BorrowableStock stock = this.stock.get(borrowableId);
		
		if (stock == null) {
			throw new RuntimeException("Stock is Null");
			//return false;
		}
		
		if (!stock.isAvailable(quantity, start, end)){
			throw new RuntimeException("Object is not available in desired quantity");
			//return false;
		}
		
		Booking result = stock.book(borrowerId, quantity, bookingInterval, reason); 
		if (result != null) {
			save();
		}
		return result;
		//return stock.getCalendar().book(borrowerId, quantity, bookingInterval, reason);
	}

	public Boolean isAvailable(Integer borrowableId, Integer quantity) {
		BorrowableStock b = this.stock.get(borrowableId);
		if (b != null) {
			Date now = Manager.now;
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
		boolean late = booking.isLate();
		booking.end();
		return late;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Booking> getNotYetValidatedBookings() {
		if(!activeUser.canValidateBookings()){
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

	/*
	public Map<Integer, String> getStockDescriptionForFeature(String feature) {
		Map<Integer, String> descriptions = new HashMap<Integer, String>();
		for (BorrowableStock borrowable : this.stock.values()) {
			if (borrowable.hasFeature(feature)) {
				descriptions.put(borrowable.getId(), borrowable.getName());
			}
		}
		return descriptions;
	}*/

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
		//MaterialType typeA = new MaterialType(0, "Item0", "MyBrand", "Lol_descr", "reference, maxTimeLoan)
		SmartPhone s4 = new SmartPhone(0, "S4", "samsungs", "fassst", 42, OS.ANDROID, 7);
		SmartPhone lolPhone = new SmartPhone(1, "iPhone", "Applz", "pouerk", 1, OS.IOS, 6);
		
		Material m1 = new Material(s4, 0, "serial####1253QSF}"),
				m2 = new Material(s4, 1, "serial####QDSF");
		m2.setDestroyed(true);
		List<Material> s4Stock = new LinkedList<Material>();
		s4Stock.add(m1);
		s4Stock.add(m2);
		Material i1 = new Material(lolPhone, 2, "serial####lolll}");
		List<Material> iStock = new LinkedList<Material>();
		iStock.add(i1);
		
		
		BorrowableStock stockS4 = new BorrowableStock(s4, s4Stock);
		//BorrowableStock stockA = new BorrowableStock(new BorrowableModel(0, "item0"), 1); 
		stock.put(stockS4.getId(), stockS4);
		//BorrowableStock stockB = new BorrowableStock(new BorrowableModel(1, "item1"), 2);
		BorrowableStock stockIphon = new BorrowableStock(lolPhone, iStock);
		stock.put(stockIphon.getId(), stockIphon);
		
		// Also create user
		User u = new Student(1, "g", "tom", "tom", "hello");
		usersManager.add(u);
		
		User um = new StockManager(2, "MANAGER","LE","manager","hello2");
		usersManager.add(um);
		
		activeUser = u;
		
		Date a = new Date("01/25/2014"),
			b = new Date("01/26/2014"),
			c = new Date("01/16/2014"),
			d = new Date("01/15/2014"),
			e = new Date("01/27/2014"),
			f = new Date("01/28/2014");
		
		book(0, 1, 1, a, b, "MYCOURSE");
		Booking debugB = book(0, 1, 1, d, c, "MYCOURSE1");
		debugB.validate();
		debugB.end();
		book(0, 1, 1, e, f, "MYCOURSE"); 
		activeUser = null;
	}
	
	public List<Material> getMaterials() {
		List<Material> list = new LinkedList<Material>();
		for (BorrowableStock s : stock.values()) {
			list.addAll(s.getStock());
		}
		return list;
	}
	
	public List<Material> getMaterialWithState(State s) {
		List<Material> list = new LinkedList<Material>();
		for (Material m : getMaterials()) {
			if (m.getState() == s) {
				list.add(m);
			}
		}
		return list;
	}
	
	public List<Material> getMaterialsInRepair() {
		List<Material> list = new LinkedList<Material>();
		for (Material m : getMaterials()) {
			if (m.isInRepair()) {
				list.add(m);
			}
		}
		return list;
	}
	
	public List<Booking> getBookings() {
		List<Booking> list = new LinkedList<Booking>();
		for (Integer borrowableID : this.stock.keySet()) {
			BorrowableStock stock = this.stock.get(borrowableID);
			list.addAll(stock.getCalendar().getBookings());
		}
		return list;
	}
	
	public List<Booking> getActiveBookings() {
		List<Booking> list = new LinkedList<Booking>();
		// Browse the stock
		for (Integer borrowableID : this.stock.keySet()) {
			BorrowableStock stock = this.stock.get(borrowableID);
			// Browse the item calendar
			for (Booking b : stock.getCalendar().getBookings()) {
				// Add only active bookings
				if (b.isActive()) {
					list.add(b);
				}
			}
		}
		return list;
	}
	
	// TODO : factorize this with predicat class
	public List<Booking> getLateBookings() {
		List<Booking> list = new LinkedList<Booking>();
			for (Booking b : getBookings()) {
				// Add only late bookings
				if (b.isLate()) {
					list.add(b);
				}
			}
		return list;
	}
	
	public List<Booking> getCancelledBookings() {
		List<Booking> list = new LinkedList<Booking>();
		for (Booking b : getBookings()) {
			// Add only late bookings
			if (b.isCancelled()) {
				list.add(b);
			}
		}
		return list;
	}
	
	public List<Booking> getUserBookings(Integer userId) {
		List<Booking> list = new LinkedList<Booking>();
		for(Integer borrowableID : this.stock.keySet()) {
			BorrowableStock stock = this.stock.get(borrowableID);
			for(Booking b : stock.getCalendar().getBookings()) {
				if (b.getBorrowerId() == userId) {
					list.add(b);
				}
			}
		}
		return list;
	}
	
	/**
	 * Returns the list of the bookings of a user.
	 * @param userId
	 * @return
	 */
	public List<Booking> getUserActiveBookings(Integer userId) {
		List<Booking> list = new LinkedList<Booking>(),
				base = getUserBookings(userId);
		// Filter only the active bookings
		for (Booking b : base) {
			if (b.isActive()) {
				list.add(b);
			}
		}
		return list;
	}
	
	/**
	 * Returns the list of the reservations made by the user.
	 * @param userId The user id
	 * @return
	 */
	public List<Booking> getUserReservations(Integer userId) {
		List<Booking> list = new LinkedList<Booking>(),
				base = getUserBookings(userId);
		// Filter only the reservation (booking in the future)
		for (Booking b : base) {
			if (b.isReservation()) {
				//System.out.println(b+" IS a reservation");
				list.add(b);
			}
			else {
				//System.out.println(b+" is not a reservation");
			}
		}
		return list;
	}


	public Integer getIDAutoIncrement() {
		return usersManager.getIDAutoIncrement();
	}
	
	/**
	 * Make the material that have finished repairing available in the stock.
	 * @return
	 */
	public List<Material> getMaterialsBackFromRepair() {
		List<Material> list = new LinkedList<Material>();
		for (Material m : getMaterials()) {
			if (m.isInRepair() && Manager.now.after(m.getRepairEnd())) {
				m.takeBackFromRepair();
				list.add(m);
			}
		}
		return list;
	}

	public void cancelBooking(Booking booking) {
		//BorrowableStock theStock = stock.get(booking.getBorrowableStack().getModel().getId());
		//TODO
		//theStock.getCalendar().cancel(booking);
	}
	
	/**
	 * Saves the app data to file.
	 */
	public void save() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(toJSON());
		
		try {
			PrintWriter out = new PrintWriter("data.json");
			out.println(jsonOutput);
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error while saving data to file.");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Loads the app data from file.
	 */
	public void load() {
		try {
			String rawData = FileUtils.readFile("data.json");
			JsonParser jsonParser = new JsonParser();
			JsonObject json = (JsonObject)jsonParser.parse(rawData);
			fromJSON(json);
		} catch (IOException e) {
			System.err.println("Error while loading file");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public JsonElement toJSON() {
		JsonObject json = new JsonObject();
		JsonArray stockJson = new JsonArray();
		json.add("stock", stockJson);
		for (Integer id : stock.keySet()) {
			stockJson.add(stock.get(id).toJSON());
		}
		json.add("users", usersManager.toJSON());
		return json;
	}

	public void fromJSON(JsonObject json) {
		for (JsonElement j : json.get("stock").getAsJsonArray()) {
			BorrowableStock s = new BorrowableStock(j.getAsJsonObject());
			stock.put(s.getId(), s);
		}
		usersManager = new UsersManager(this, json.get("users").getAsJsonObject());
	}
}