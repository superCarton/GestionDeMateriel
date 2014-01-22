package borrowmanager.view.menu;

import java.util.Date;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.DateInterval;
import borrowmanager.model.user.Borrower;
import borrowmanager.view.ItemPicker;
import borrowmanager.view.TextInterfacePage;

public class BorrowMenu extends TextInterfacePage {

	private Manager manager;
	
	public BorrowMenu(Manager m) {
		manager = m;
		show();
	}
	
	@Override
	protected boolean show() {
		System.out.println("You chose to borrow something from the stock.");
		
		// Pick an item
		ItemPicker picker = new ItemInStockPicker(manager);
		Integer itemId = picker.getPickedItemId();
		
		// If the user wants to go back
		if (itemId == null) { 
			new BorrowerHomeMenu(manager);
		}
		
		Integer quantity = inputQuantity();
		DateInterval interval = inputDateInterval();
		
		System.out.println("Enter a reason for this booking :");
		String reason = input();
		
		if (tryToBook(itemId, quantity, interval, reason)) {
			System.out.println("You registered your booking successfuly."
					+" Your booking still has to be confirmed by a stock manager.");
			new BorrowerHomeMenu(manager);
		} else {
			System.out.println("There was an error during the booking process. Please try again.");
			new BorrowerHomeMenu(manager);
		}
		
		return false;
	}
	
	/**
	 * Validates and performs the booking if possible.
	 * @param borrowableId
	 * @param quantity
	 * @param interval
	 * @param reason
	 * @return
	 */
	private boolean tryToBook(Integer borrowableId, Integer quantity, DateInterval interval, String reason) {
		// Check if the user can book something for a given length
		//
		Borrower user = (Borrower) manager.getActiveUser();
		Integer maxBookingLength = user.getMaxBookingLength();
		if (interval.getLength() > maxBookingLength) {
			System.out.println("You can't book something for more than "+maxBookingLength+" consecutive days.");
			return false;
		}
		
		// Check if the user can book something ahead to the start date from now
		//
		Date now = manager.now;
		Date start = interval.getStart();
		Date end = interval.getEnd();
		DateInterval reservationInterval = new DateInterval(now, start);
		Integer maxReservationLength = user.getMaxReservationLength();
		if (reservationInterval.getLength() > maxReservationLength) {
			System.out.println("You can't book something more "+maxReservationLength+" days ahead (you tried "+reservationInterval.getLength()+" days)");
			return false;
		}
		
		// Check if the item is available in the stock
		//
		if (!manager.isAvailable(borrowableId, quantity, start, end)) {
			System.out.println("The item is not available for the specified date interval in this quantities.");
			return false;
		}
		
		return manager.book(borrowableId, quantity, user.getId(), interval.getStart(), interval.getEnd(), reason);
	}
	
}
