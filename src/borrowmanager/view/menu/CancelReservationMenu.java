package borrowmanager.view.menu;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.view.BookingPicker;
import borrowmanager.view.TextInterfacePage;

public class CancelReservationMenu extends TextInterfacePage {
	private Manager manager;
	
	public CancelReservationMenu(Manager m) {
		manager = m;
	}
	
	@Override
	public TextInterfacePage display() {
		List<Booking> list = manager.getUserReservations(manager.getActiveUser().getId());
		
		if (list.size() > 0) {
			System.out.println("Here is the list of your reservations.");
			System.out.println("Select the one you want to cancel :");
		
			BookingPicker picker = new BookingPicker(list);
			picker.display();
			Integer picked = picker.getPickedItemId();
			if (picked == null) {
				return null;
			}
			if (question("Are you sure ?")) {
				list.get(picked).cancel();
				System.out.println("Reservation cancelled.");
				enterToContinue();
			}
			openChildPage(new BookingDetails(manager, list.get(picked)));
		}
		
		return this;
	}
}
