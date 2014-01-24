package borrowmanager.view.menu;


import borrowmanager.model.Manager;

public class CancelledBookingsList extends BookingsList {

	public CancelledBookingsList(Manager m) {
		super(m, m.getCancelledBookings());
		setMessage("Displaying the list of all the cancelled bookings");
	}

}
