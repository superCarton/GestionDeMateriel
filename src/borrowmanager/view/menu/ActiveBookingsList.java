package borrowmanager.view.menu;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.view.TextInterfacePage;

public class ActiveBookingsList extends BookingsList {

	public ActiveBookingsList(Manager m) {
		super(m, m.getActiveBookings());
		setMessage("Displaying all active bookings (including late bookings)");
		// TODO Auto-generated constructor stub
	}
}
