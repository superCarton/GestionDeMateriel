package borrowmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import borrowmanager.booking.Booking;
import borrowmanager.booking.DateInterval;
import borrowmanager.element.BorrowableStack;
import borrowmanager.element.BorrowableStock;
import borrowmanager.user.User;
import borrowmanager.user.UserType;

public class TextInterface {
	
	private Integer idAutoIncrement = 1;
	private BufferedReader br;
	
	private Manager manager;
	private User user;
	
	private String dateFormat = "dd/MM/yyyy";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
	
	public TextInterface() {
		manager = new Manager();
		manager.fillTemporaryStock();
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void start() {
		welcome();
	}
	
	public void welcome() {
		System.out.println("Welcome to the borrowing manager.");
		createAccount();		
	}
	
	private void login() {
		System.out.println("Please log in : ");
		
		boolean connected = false;
		 
		while (!connected) {System.out.println();
			String s = null;
		    try {
				s = br.readLine();
			} catch (IOException e) {
				// nothing
			}
		    user = new User(1, s, UserType.STUDENT);
		    manager.setUser(user);
		    System.out.println("Hello "+user.getName()+" !");
		 }
	}
	
	private void logout() {
		manager.setUser(null);
		welcome();
	}
	
	private void createAccount() {
		System.out.println("Please select what you want to be :");
		System.out.println("Available UserTypes :");
		System.out.println("   1. STUDENT");
		System.out.println("   2. TEACHER");
		System.out.println("   3. STOCK_MANAGER");
		System.out.println("Type 1,2 or 3 : ");
		
		boolean valid = false;
		String input = null;
		UserType userType = null ;
		String userName = null;
		while (!valid) {
			try {
				input = br.readLine();
			} catch (IOException e) {
				// do nothing
			}
			
			valid = true;
			if (input.equals("1")) {
				userType = UserType.STUDENT;
			} else if (input.equals("2")) {
				userType = UserType.TEACHER;
			} else if (input.equals("3")) {
				userType = UserType.STOCK_MANAGER;
			} else {
				valid = false;
			}
		}
		
		System.out.println("Please enter your name : ");
		valid = false;
		try {
			userName = br.readLine();
		} catch (IOException e) {
			// do nothing
		}
		
		user = new User(idAutoIncrement, userName, userType);
		manager.setUser(user);
		mainMenu();
	}
	
	private void mainMenu() {
		UserType ut = user.getUserType();
		if (ut == UserType.STUDENT || ut == UserType.TEACHER) {
			mainMenuBorrower();
		}
		else if (ut == UserType.STOCK_MANAGER) {
			mainMenuStockManager();
		}
	}
	
	private void mainMenuBorrower() {
		String input = null;
		Boolean valid = false;
		
		while (!valid) {
			System.out.println("What do you want to do ?");
			System.out.println("   1. Borrow something");
			System.out.println("   2. Give back something");
			System.out.println("   3. Log out");
			System.out.println("Type 1, 2 or 3 :");
			
			try {
				input = br.readLine();
			} catch (IOException e) {
				// do nothing
			}
			
			valid = true;
			
			if (input.equals("1")) {
				borrowMenu();
			} else if (input.equals("2")) {
				giveBackMenu();
			} else if (input.equals("3")) {
				logout();
			} else {
				valid = false;
			}
		}
	}
	
	private void borrowMenu() {
		System.out.println("You chose to borrow something from the stock.");		
		Boolean valid = false;
		Integer itemId = 0;
		while (!valid) {
			itemId = pickItemInStock();
			if (itemId == -1) { // go back
				mainMenu();
				return ;
			}
			valid = true;
			Integer quantity = selectQuantity();
			DateInterval interval = selectDuration();
			String reason = null;
			System.out.println("Enter a reason for this booking :");
			try {
				reason = br.readLine();
			} catch (IOException e) {
				// do nothing
			}
			
			if (tryToBook(itemId, quantity, interval, reason)) {
				System.out.println("You registered your booking successfuly."
						+" Your booking still has to be confirmed by a stock manager.");
				mainMenu();
			} else {
				System.out.println("There was an error during the booking process. Please try again.");
				borrowMenu();
				return ;
			}
		}
	}
	
	private void giveBackMenu() {
		System.out.println("You chose to give back something ! Good idea !");
		
		
		//List<BorrowableStack> userStock = manager.getUserStock(user.getId());
		List<Booking> userBookings = manager.getUserBookings(user.getId());
		if (userBookings.size() == 0) {
			System.out.println("But you don't have any item to return! Nice job!");
			mainMenu();
			return;
		}
		
		System.out.println("Choose one of the item to give back");
		Integer bookingID = pickBookingInList(userBookings);
		
		if (bookingID == -1) {
			mainMenu();
			return;
		}
		
		// Because the list displayed was indexed from 1
		bookingID--;
		
		Booking booking = userBookings.get(bookingID);

		Boolean isLate = booking.end();
		if (!isLate) {
			System.out.println("You returned "+booking.getBorrowableStack().getName()+" on time! Congrats!");
		}
		else {
			System.out.println("You returned "+booking.getBorrowableStack().getName()+" late. You will have to pay a fee.");
		}
		
		mainMenu();
	}
	
	private boolean tryToBook(Integer borrowableId, Integer quantity, DateInterval interval, String reason) {
		// Check if the user can book something for a given length
		//
		UserType ut = user.getUserType();
		Integer maxBookingLength = UserType.getBookingLength(ut);
		if (interval.getLength() > maxBookingLength) {
			System.out.println("You can't book something for more than "+maxBookingLength+" consecutive days.");
			return false;
		}
		
		// Check if the user can book something ahead to the start date from now
		//
		Date now = new Date();
		Date start = interval.getStart();
		Date end = interval.getEnd();
		DateInterval reservationInterval = new DateInterval(now, start);
		Integer maxReservationLength = UserType.getMaxReservationLength(ut);
		if (reservationInterval.getLength() > maxReservationLength) {
			System.out.println("You can't book something more "+maxReservationLength+" days ahead (you tried "+reservationInterval.getLength()+" days)");
			return false;
		}
		
		// Check if the item is available in the stock
		//
		if (!manager.isAvailable(borrowableId, quantity, start, end)) {
			System.out.println("The item is not available for the specified date interval.");
			return false;
		}
		
		return manager.book(borrowableId, quantity, user.getId(), interval.getStart(), interval.getEnd(), reason);
	}
	
	private DateInterval selectDuration() {
		Date
			startDate,
			endDate;
		System.out.println("Please enter a start date : ");
		startDate = readFutureDate();
		System.out.println("Please enter a end date : ");
		endDate = readFutureDate();
		
		return new DateInterval(startDate, endDate);
	}
	
	private Date readFutureDate() {
		Date date ;
		Date now = new Date();
		
		while (true) {
			date = readDate();
			if (date.compareTo(now) <= 0) {
				System.out.println("You must enter a future date !");
			} else {
				return date;
			}
		}
	}
	
	private Date readDate() {
		String input = null;
		while (true) {
			System.out.println("Enter date ("+dateFormat+") : ");
			try {
				input = br.readLine();
			} catch (IOException e1) {
				// do nothing
			}
			
			
			
			try {
				Date d = simpleDateFormat.parse(input);
				System.out.println("Parsed date: "+simpleDateFormat.format(d));
				return d;
			} catch (ParseException e) {
				System.out.println("Bad date format !");
			} 
			System.out.println("Invalid date. Please enter a correct date.");
		}
	}

	
	private Integer pickItemInStock() {
		return pickItemInList(manager.getStockList());		
	}
	
	/**
	 * Pick an item in a list
	 * @return
	 */
	private Integer pickItemInList(Collection<BorrowableStock> list) {
		String input = null;
		Boolean valid = false;
		
		while (!valid) {
			System.out.println("Select an item in the following list :");
			for (BorrowableStock b : list) {
				String borrowableName = b.getName();
				System.out.println("   "+b.getId()+". "+borrowableName);
			}
			System.out.println("   b. Go back");
			
			try {
				input = br.readLine();
			} catch (IOException e) {
				// do nothing
			}
			
			for (BorrowableStock b : list) {
				Integer id = b.getId();
				if (input.equals(Integer.toString(id))) {
					return id;
				}
			}
			if (input.toLowerCase().equals("b")) {
				return -1;
			}
		}
		return -1;
	}
	
	
	private Integer pickBookingInList(List<Booking> list) {
		String input = null;
		
		while (true) {
			System.out.println("Select an item in the following list :");
			Integer i = 1;
			for (Booking b : list) {
				String borrowableName = b.getBorrowableStack().getName();
				Date start = b.getInterval().getStart();
				Date end = b.getInterval().getEnd();
				String startString = simpleDateFormat.format(start);
				String endString = simpleDateFormat.format(end);
				System.out.println("   "+(i++)+" "+borrowableName+" x"+b.getQuantity()+" ["+startString+" - "+endString+"] | Details: "+b.getReason());
			}
			System.out.println("   b. Go back");
			
			try {
				input = br.readLine();
			} catch (IOException e) {
				// do nothing
			}
			
			if (input.toLowerCase().equals("b")) {
				return -1;
			}
			
			try {
				return Integer.parseInt(input);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
	}
	
	
	private Integer selectQuantity() {
		String input = null;
		Boolean valid = false;
		while (!valid) {
			System.out.println("Please enter a quantity :");
			try {
				input = br.readLine();
				Boolean isInteger;
				try {
					return Integer.parseInt(input);
				} catch (NumberFormatException e) {
					// do nothing
				}
			} catch (IOException e) {
				// do nothing
			}
		}
		return -1;
	}
	
	private BorrowableStack pickItemInUserInventory() {
		return null;
	}
	
	private void mainMenuStockManager() {
		
	}
	
}
