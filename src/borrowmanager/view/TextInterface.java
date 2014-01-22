package borrowmanager.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import borrowmanager.UNUSED_user.UNUSED_User;
import borrowmanager.UNUSED_user.UNUSED_UserType;
import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.booking.DateInterval;
import borrowmanager.model.element.BorrowableStock;
/**
 * Text UI for the borrowmanager project
 * @author Tom Guillermin
 *
 */
public class TextInterface {
	
	private Integer idAutoIncrement = 1;
	private BufferedReader br;
	
	private Manager manager;
	private UNUSED_User user;
	
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
		System.out.println();
		System.out.println("Welcome to the IHM borrowing manager.");
		System.out.println();
		System.out.println("Do you want to login or create a new account ?");
		
		String input = null;
		while (true) {
			System.out.println("   1. Login");
			System.out.println("   2. Create account");
			try {
				input = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (input.equals("1")) {
				login();
				return;
			}
			else if (input.equals("2")) {
				//createAccount();
				return;
			}
		}
	}
	
	private void login() {
		/*
		System.out.println("Please log in : ");
		
		boolean connected = false;
		 
		while (!connected) {System.out.println();
			String s = null;
		    try {
				s = br.readLine();
			} catch (IOException e) {
				// nothing
			}
		    
		    manager.getUserByName(s);
		    if (user != null) {
		    	manager.setActiveUser(user);
			    System.out.println("Hello "+user.getName()+" !");
			    mainMenu();
			    return;
		    }
		    else {
		    	System.out.println("User "+s+" not found!");
		    	welcome();
		    	return;
		    }
		 }
		 */
	}
	
	private void logout() {
		//manager.setUser(null);
		welcome();
	}
	
	/*private void createAccount() {
		System.out.println("Please select what you want to be :");
		System.out.println("Available UserTypes :");
		System.out.println("   1. STUDENT");
		System.out.println("   2. TEACHER");
		System.out.println("   3. STOCK_MANAGER");
		System.out.println("Type 1,2 or 3 : ");
		
		boolean valid = false;
		String input = null;
		UNUSED_UserType userType = null ;
		String userName = null;
		while (!valid) {
			try {
				input = br.readLine();
			} catch (IOException e) {
				// do nothing
			}
			
			valid = true;
			if (input.equals("1")) {
				userType = UNUSED_UserType.STUDENT;
			} else if (input.equals("2")) {
				userType = UNUSED_UserType.TEACHER;
			} else if (input.equals("3")) {
				userType = UNUSED_UserType.STOCK_MANAGER;
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
	}*/
	
	private void mainMenu() {
		UNUSED_UserType ut = user.getUserType();
		if (ut == UNUSED_UserType.STUDENT || ut == UNUSED_UserType.TEACHER) {
			mainMenuBorrower();
		}
		else if (ut == UNUSED_UserType.STOCK_MANAGER) {
			mainMenuStockManager();
		}
	}
	
	private void mainMenuBorrower() {
		String input = null;
		Boolean valid = false;
		
		while (!valid) {
			System.out.println("");
			System.out.println("===========================");
			System.out.println("What do you want to do ?");
			System.out.println("   1. Borrow something");
			System.out.println("   2. Give back something");
			System.out.println("   3. Log out");
			System.out.println("   4. Save to file");
			System.out.println("Type 1, 2, 3 or 4 :");
			
			try {
				input = br.readLine();
			} catch (IOException e) {
				// do nothing
			}
			
			valid = true;
			
			if (input.equals("1")) {
				borrowMenu();
				return;
			} else if (input.equals("2")) {
				giveBackMenu();
				return;
			} else if (input.equals("3")) {
				logout();
				return;
			} else if (input.equals("4")) {
				save();
				return;
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
		List<Booking> userBookings = manager.getUserActiveBookings(user.getId());
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
		UNUSED_UserType ut = user.getUserType();
		Integer maxBookingLength = UNUSED_UserType.getBookingLength(ut);
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
		Integer maxReservationLength = UNUSED_UserType.getMaxReservationLength(ut);
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
				System.out.println("   "+(i++)+" "+b.toListString(simpleDateFormat));
				/*
				String borrowableName = b.getBorrowableStack().getName();
				Date start = b.getInterval().getStart();
				Date end = b.getInterval().getEnd();
				String startString = simpleDateFormat.format(start);
				String endString = simpleDateFormat.format(end);
				System.out.println("   "+(i++)+" "+borrowableName+" x"+b.getQuantity()+" ["+startString+" - "+endString+"] | Details: "+b.getReason());
				*/
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
	
	private void mainMenuStockManager() {
		String input = null;
		while (true) {
			System.out.println("You are in stock management interface !");
			System.out.println("What do you want to do ?");
			
			System.out.println("   1. Review not yet validated bookings");
			System.out.println("   2. View all bookings");
			System.out.println("   3. View late bookings");
			System.out.println("   4. Save data");
			System.out.println("   5. Logout");
			
			try {
				input = br.readLine();
			} catch (IOException e) {
				// do nothing
			}
			
			if (input.equals("1")) {
				reviewNotYetValidatedMenu();
				return;
			}
			else if (input.equals("2")) {
				viewAllBookings();
				return;
			}
			else if (input.equals("3")) {
				viewAllLateBookings();
				return;
			}
			else if (input.equals("4")) {
				save();
				return;
			}
			else if (input.equals("5")) {
				logout();
				return;
			}
		}
		
	}

	private void viewAllBookings() {
		System.out.println("Here are all the booking in the reservation system :");
		List<Booking> bookings = manager.getBookings();
		
		if (bookings.size() == 0) {
			System.out.println("No booking to display for now.");
		}
		
		for(Booking b : bookings) {
			String userName = manager.getUsersManager().getUser(b.getBorrowerId()).getName();
			System.out.println(b.toListString(simpleDateFormat)+" [Borrowed by "+userName+"]");
		}
		System.out.println("__________");
		mainMenu();
	}
	
	private void viewAllLateBookings() {
		List<Booking> bookings = manager.getBookings();
		
		if (bookings.size() == 0) {
			System.out.println("No late booking to display. People are nice these days.");
		}
		
		for(Booking b : bookings) {
			if (b.isLate()) {
				String userName = manager.getUsersManager().getUser(b.getBorrowerId()).getName();
				System.out.println(b.toListString(simpleDateFormat)+" [Borrowed by "+userName+"]");
			}
		}
		System.out.println("__________");
		mainMenu();
	}

	private void reviewNotYetValidatedMenu() {
		System.out.println("List of the bookings that are not validated yet.");
		System.out.println("Pick the one you want to validate :");
		
		List<Booking> bookings = manager.getNotYetValidatedBookings();
		
		if (bookings.size() == 0) {
			System.out.println("There is not booking to valided for now !");
			mainMenu();
			return;
		}
		
		Integer listIndex = pickBookingInList(bookings);
		
		if (listIndex == -1) {
			mainMenu();
			return;
		}
		
		listIndex--; // for user convenience, the displayed list was indexed from one.
		
		bookings.get(listIndex).validate();
		System.out.println("Booking validated successfuly !");
		mainMenu();
	}
	
	private void save() {
		manager.save();
		System.out.println("Data saved to file.");
		mainMenu();
	}
	
}
