package borrowmanager.view.menu;

import borrowmanager.model.Manager;

public class ActiveBookingsList extends BookingsList {

	public ActiveBookingsList(Manager m) {
		super(m, m.getActiveBookings());
		setMessage("Displaying all active bookings (including late bookings)");
	}
}
