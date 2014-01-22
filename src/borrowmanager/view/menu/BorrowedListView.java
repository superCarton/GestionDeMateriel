package borrowmanager.view.menu;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.user.User;
import borrowmanager.view.BookingPicker;
import borrowmanager.view.TextInterfacePage;

public class BorrowedListView extends TextInterfacePage {

	private Manager manager;

	public BorrowedListView(Manager m) {
		manager = m;
	}

	@Override
	public TextInterfacePage display() {
		System.out.println();
		User u = manager.getActiveUser();
		List<Booking> bookings = manager.getUserActiveBookings(u.getId());
		
		if (bookings.size() > 0) {
			System.out.println("Here is the list of what you have currently booked.");
			System.out.println("Choose one to have more details :");
			BookingPicker picker = new BookingPicker(bookings);
			picker.display();
			Integer picked = picker.getPickedItemId(); 
			if (picked != null) {
				Booking b = bookings.get(picked);
				openChildPage(new BookingDetails(manager, b));
			}
		}
		else {
			System.out.println("You don't have any active bookings !");
		}
		
		System.out.println("Press enter to go back to the main menu");
		input();
		
		return null;
	}
	
}
