package borrowmanager.view.menu;

import borrowmanager.model.Manager;

public class AllBookingsList extends BookingsList {
	public AllBookingsList(Manager m) {
		super(m, m.getBookings());
		setMessage("Displaying all bookings");
	}
}
