package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.element.BorrowableStack;
import borrowmanager.model.element.BorrowableStock;
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
		System.out.println("Booking Information:");
		BorrowableStack item = booking.getBorrowableStack();
		System.out.println("Item borrowed :\t"+item.getName());
		System.out.println("Quantity :\t"+booking.getQuantity());
		System.out.println("Date :\t"+booking.getInterval().toString());
		
		if (booking.isLate()) {
			System.out.println();
			System.out.println("WARNING :\tThis booking is late ! You should give it back !");
			System.out.println("\tMoreover, you will have to pay an additional fee.");
		}
		
		System.out.println();
		
		// Offer the user to give the item back
		if (booking.getBorrowerId() == manager.getActiveUser().getId()) {
			if (question("Would you like to give back the item now ?")) {
				System.out.println("Wise !");
				//return new GiveBackMenu(manager, booking);
			}
		}
		return null;
	}

}
