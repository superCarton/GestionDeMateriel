package borrowmanager.view;

import java.util.List;

import borrowmanager.model.booking.Booking;

public class BookingPicker extends ListPicker<Booking> {

	public BookingPicker(List<Booking> l) {
		super(l);
	}
	
	@Override
	protected String elementToListString(Booking element) {
		return element.toListString(simpleDateFormat);
	}

}
