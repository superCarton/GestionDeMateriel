package borrowmanager.view.menu;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.view.BookingPicker;
import borrowmanager.view.TextInterfacePage;

public class BookingsValidationMenu extends TextInterfacePage {

	
	private Manager manager;

	public BookingsValidationMenu(Manager m) {
		manager = m;
	}

	@Override
	public TextInterfacePage display() {
		List<Booking> list = manager.getNotYetValidatedBookings();
		
		if (list.size() > 0) {
			System.out.println("Select the booking that you would like to validate in the list bellow :");
			
			BookingPicker picker = new BookingPicker(list);
			picker.display();
			Integer picked = picker.getPickedItemId();
			
			if (picked != null) {
				list.get(picked).validate();
			}
			else {
				return null;
			}
		}
		else {
			System.out.println("There is not booking to validate for now !");
			System.out.println("Press enter to continue to the main menu");
			input();
			return null;
		}
		
		return this;
	}

}
