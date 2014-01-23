package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.element.BorrowableStack;
import borrowmanager.model.element.BorrowableStock;
import borrowmanager.model.material.MaterialType;
import borrowmanager.model.user.StockManager;
import borrowmanager.model.user.User;
import borrowmanager.view.TextInterfaceOptionPage;
import borrowmanager.view.TextInterfacePage;

public class BookingDetails extends TextInterfaceOptionPage {

	private Manager manager;
	private Booking booking;
	
	public BookingDetails(Manager m, Booking b) {
		manager = m;
		booking = b;
	}
	
	@Override
	public TextInterfacePage display() {
		User user = manager.getUsersManager().getUser(booking.getBorrowerId());
		boolean isViewedByBorrower = user == manager.getActiveUser(),
				isViewedByStockManager = manager.getActiveUser() instanceof StockManager;
		System.out.println("Booking Information:");
		MaterialType item = booking.getMaterialType();
		//BorrowableStack item = booking.getBorrowableStack();
		System.out.println("Item borrowed :\t"+item.getName());
		System.out.println("Quantity :\t"+booking.getQuantity());
		System.out.println("Date :\t"+booking.getInterval().toString());
		
		
		if (booking.isLate()) {
			System.out.println();
			System.out.println("WARNING :This booking is late !") ;
			String reminderString ;
			if (booking.getReminders().size() > 0) { 
				reminderString = booking.getReminders().size()+" (last: "+simpleDateFormat.format(booking.getLastReminder().getDate())+")";
			}
			else {
				reminderString = "none";
			}
			System.out.println("Number of reminders : "+reminderString);
			if (isViewedByBorrower) {
				System.out.println("\nYou should give it back !");
				System.out.println("\tMoreover, you will have to pay an additional fee.");
			}
		}
			
		// Actions on the booking
		//
		// Actions for stock manager
		if (isViewedByStockManager) {
			// Not validated
			if (! booking.isValidated()) {
				System.out.println();
				if (question("This booking has not been validated, would you like to validate it now ?")) {
					booking.validate();
					System.out.println("The booking has been validated !");
					System.out.println("Press enter to continue");
					input();
				}
			}
			// Late booking
			if (booking.isLate()) {
				if (question("Would you like to send a reminder to the borrower ?")) {
					if (booking.wasAReminderSendToday()) {
						System.out.println("Sorry, but a reminder has already been sent today.");
					}
					else {
						booking.addReminder();
						System.out.println("Reminder sent successfully !");
					}
					System.out.println("Press enter to continue");
					input();
				}
			}
		}
			
		// Offer the user to give the item back
		if (isViewedByBorrower) {
			System.out.println();
			if (booking.isActive()) {
				if (question("Would you like to give back the item now ?")) {
					System.out.println("Wise !");
					if (booking.isLate()) System.out.println("Better late than never.");
					GiveBackListMenu.returnItem(booking);
				}
			}
			if (booking.isReservation()) {
				if (question("Would you like to cancel the reservation ?")) {
					
				}
			}
		}
		return null;
	}

	@Override
	protected void build() {
		User user = manager.getUsersManager().getUser(booking.getBorrowerId());
		boolean isViewedByBorrower = user == manager.getActiveUser(),
				isViewedByStockManager = manager.getActiveUser() instanceof StockManager;
		System.out.println("Booking Information:");
		MaterialType item = booking.getMaterialType(); //getBorrowableStack();
		System.out.println("Item borrowed :\t"+item.getName());
		System.out.println("Quantity :\t"+booking.getQuantity());
		System.out.println("Date :\t"+booking.getInterval().toString());
		
		
		if (booking.isLate()) {
			System.out.println();
			System.out.println("WARNING :This booking is late !") ;
			String reminderString ;
			if (booking.getReminders().size() > 0) { 
				reminderString = booking.getReminders().size()+" (last: "+simpleDateFormat.format(booking.getLastReminder().getDate())+")";
			}
			else {
				reminderString = "none";
			}
			System.out.println("Number of reminders : "+reminderString);
			if (isViewedByBorrower) {
				System.out.println("\nYou should give it back !");
				System.out.println("\tMoreover, you will have to pay an additional fee.");
			}
		}
			
		// Actions on the booking
		//
		// Actions for stock manager
		if (isViewedByStockManager) {
			// Not validated
			if (! booking.isValidated()) {
				System.out.println();
				addOption("validate", "Validate the booking");
			}
			// Late booking
			if (booking.isLate()) {
				addOption("sendReminder","Send a reminder to the borrower");
			}
		}
			
		// Offer the user to give the item back
		if (isViewedByBorrower) {
			System.out.println();
			if (booking.isActive()) {
				addOption("returnItem", "Return the item");
			}
			if (booking.isReservation()) {
				addOption("cancelReservation","Cancel reservation");
			}
		}
	}

	@Override
	protected TextInterfacePage handleCommand(String c) {
		if (c.equals("validate")) {
			if (question("This booking has not been validated, would you like to validate it now ?")) {
				booking.validate();
				System.out.println("The booking has been validated !");
				System.out.println("Press enter to continue");
				input();
			}
		}
		else if (c.equals("sendReminder")) {
			if (question("Would you like to send a reminder to the borrower ?")) {
				if (booking.wasAReminderSendToday()) {
					System.out.println("Sorry, but a reminder has already been sent today.");
				}
				else {
					booking.addReminder();
					System.out.println("Reminder sent successfully !");
				}
				System.out.println("Press enter to continue");
				input();
			}
		}
		else if (c.equals("returnItem")) {
			if (question("Would you like to give back the item now ?")) {
				System.out.println("Wise !");
				if (booking.isLate()) System.out.println("Better late than never.");
				GiveBackListMenu.returnItem(booking);
			}
		}
		else if (c.equals("cancelReservation")) {
			if (question("Would you like to cancel the reservation ?")) {
				// TODO
				//manager.cancelBooking(booking);
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

}
