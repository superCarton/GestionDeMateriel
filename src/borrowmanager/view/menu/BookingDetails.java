package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.element.BorrowableStack;
import borrowmanager.model.element.BorrowableStock;
import borrowmanager.model.user.StockManager;
import borrowmanager.model.user.User;
import borrowmanager.view.TextInterfacePage;

public class BookingDetails extends TextInterfacePage {

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
		BorrowableStack item = booking.getBorrowableStack();
		System.out.println("Item borrowed :\t"+item.getName());
		System.out.println("Quantity :\t"+booking.getQuantity());
		System.out.println("Date :\t"+booking.getInterval().toString());
		
		
		if (isViewedByStockManager && ! booking.isValidated()) {
			System.out.println();
			if (question("This booking has not been validated, would you like to validate it now ?")) {
				booking.validate();
				System.out.println("The booking has been validated !");
			}
		}
		
		if (booking.isLate()) {
			System.out.println();
			System.out.println("WARNING :This booking is late !") ;
			String reminderString ;
			if (booking.getReminders().size() > 0) {
				reminderString = booking.getReminders().size()+" (last : "+simpleDateFormat.format(booking.getLastReminder());
			}
			else {
				reminderString = "none";
			}
			System.out.println("Number of reminders : "+reminderString);
			if (isViewedByBorrower) {
				System.out.println("\nYou should give it back !");
				System.out.println("\tMoreover, you will have to pay an additional fee.");
			}
			else if (isViewedByStockManager) {
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
			if (question("Would you like to give back the item now ?")) {
				System.out.println("Wise !");
				//return new GiveBackMenu(manager, booking);
			}
		}
		return null;
	}

}
