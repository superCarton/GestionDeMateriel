package borrowmanager.view.menu;

import java.util.LinkedList;
import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.view.BookingPicker;
import borrowmanager.view.TextInterfacePage;

public class AllReservationListView extends TextInterfacePage {
	
	private Manager manager;
	
	public AllReservationListView(Manager m) {
		manager = m;
	}
	
	@Override
	public TextInterfacePage display() {
		List<Booking> bookings = manager.getBookings();
		List<Booking> reservations = new LinkedList<Booking>();
		for (Booking b : bookings) {
			if (b.isReservation()) {
				reservations.add(b);
			}
		}
		
		if (reservations.size() > 0) {
			System.out.println("Here is the list of all reservations (future-dated bookings).");
			System.out.println("Select one to have more info or validate it:");
		
			BookingPicker picker = new BookingPicker(reservations);
			picker.display();
			Integer picked = picker.getPickedItemId();
			if (picked == null) {
				return null;
			}
			openChildPage(new BookingDetails(manager, reservations.get(picked)));
		}
		
		return this;
	}

}
