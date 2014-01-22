package borrowmanager.view.menu;


import borrowmanager.model.Manager;

public class LateBookingsList extends BookingsList {

	public LateBookingsList(Manager m) {
		super(m, m.getLateBookings());
		setMessage("Displaying the list of all late bookings");
	}

}
