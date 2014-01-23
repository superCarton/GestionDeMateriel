package borrowmanager.view.menu;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.user.Borrower;
import borrowmanager.view.BookingPicker;
import borrowmanager.view.TextInterfacePage;

public class GiveBackListMenu extends TextInterfacePage {

	private Manager manager;
	public GiveBackListMenu(Manager m) {
		manager = m;
	}
	
	@Override
	public TextInterfacePage display() {
		Borrower user = (Borrower) manager.getActiveUser();
		System.out.println("You chose to give back something. (Good idea !)");

		List<Booking> userBookings = manager.getUserActiveBookings(user.getId());
		if (userBookings.size() == 0) {
			System.out.println("But you don't have any item to return! Nice job!");
			System.out.println("Press enter to continue to the main menu.");
			input();
			//new BorrowerHomeMenu(manager);
			return null;
		}
		
		System.out.println("Choose one of the item to give back");
		BookingPicker picker = new BookingPicker(userBookings);
		picker.display();
		Integer bookingID = picker.getPickedItemId();
		
		if (bookingID == null) {
			//new BorrowerHomeMenu(manager);
			return null;
		}
		
		Booking booking = userBookings.get(bookingID);

		returnItem(booking);
		
		//new BorrowerHomeMenu(manager);
		return null;
	}
	
	public static void returnItem(Booking booking) {
		Boolean isLate = booking.isLate();
		booking.end();
		if (!isLate) {
			System.out.println("You returned "+booking.getMaterialType().getName()+" on time! Congrats!");
		}
		else {
			System.out.println("You returned "+booking.getMaterialType().getName()+" late. You will have to pay a fee.");
		}
	}

}
