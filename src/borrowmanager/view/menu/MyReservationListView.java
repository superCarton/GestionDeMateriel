package borrowmanager.view.menu;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.view.BookingPicker;
import borrowmanager.view.TextInterfacePage;

public class MyReservationListView extends TextInterfacePage {
	
	private Manager manager;
	
	public MyReservationListView(Manager m) {
		manager = m;
	}
	
	@Override
	public TextInterfacePage display() {
		List<Booking> list = manager.getUserReservations(manager.getActiveUser().getId());
		
		System.out.println("Here is the list of your reservations.");
		System.out.println("Select one to have more info :");
			BookingPicker picker = new BookingPicker(list);
		picker.display();
		Integer picked = picker.getPickedItemId();
		if (picked == null) {
			return null;
		}
		openChildPage(new BookingDetails(manager, list.get(picked)));
				
		return this;
	}

}
