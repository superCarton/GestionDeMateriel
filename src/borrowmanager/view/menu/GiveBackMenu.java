package borrowmanager.view.menu;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.user.Borrower;
import borrowmanager.view.BookingPicker;
import borrowmanager.view.TextInterfacePage;

public class GiveBackMenu extends TextInterfacePage {

	private Manager manager;
	public GiveBackMenu(Manager m) {
		manager = m;
	}
	@Override
	protected boolean show() {
		Borrower user = (Borrower) manager.getActiveUser();
		System.out.println("You chose to give back something ! Good idea !");

		List<Booking> userBookings = manager.getUserActiveBookings(user.getId());
		if (userBookings.size() == 0) {
			System.out.println("But you don't have any item to return! Nice job!");
			new BorrowerHomeMenu(manager);
			return true;
		}
		
		System.out.println("Choose one of the item to give back");
		BookingPicker picker = new BookingPicker(userBookings);
		
		Integer bookingID = picker.getPickedItemId();
		
		if (bookingID == null) {
			new BorrowerHomeMenu(manager);
			return false;
		}
		
		Booking booking = userBookings.get(bookingID);

		Boolean isLate = booking.end();
		if (!isLate) {
			System.out.println("You returned "+booking.getBorrowableStack().getName()+" on time! Congrats!");
		}
		else {
			System.out.println("You returned "+booking.getBorrowableStack().getName()+" late. You will have to pay a fee.");
		}
		
		new BorrowerHomeMenu(manager);
		return false;
	}

}
