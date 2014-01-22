package borrowmanager.view.menu;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.view.BookingPicker;
import borrowmanager.view.TextInterfacePage;

public class BookingsList extends TextInterfacePage {
	private Manager manager;
	private List<Booking> list;
	
	private String message;
	
	public BookingsList(Manager m, List<Booking> l) {
		manager = m;
		list = l;
	}
	
	protected void setMessage(String m) {
		message = m;
	}
	
	
	@Override
	public TextInterfacePage display() {
		System.out.println(message);
		
		if (list.size() > 0) {
			System.out.println("Select a booking to get more info :");
			System.out.println();
			

			BookingPicker picker = new BookingPicker(list);
			picker.display();
			Integer picked = picker.getPickedItemId();
			
			if (picked != null) {
				return new BookingDetails(manager, list.get(picked));
			}
		}
		
		return null;
	}

}
