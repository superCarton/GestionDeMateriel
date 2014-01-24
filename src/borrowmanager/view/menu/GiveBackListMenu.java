package borrowmanager.view.menu;

import java.util.LinkedList;
import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.element.State;
import borrowmanager.model.material.Material;
import borrowmanager.model.user.Borrower;
import borrowmanager.view.BookingPicker;
import borrowmanager.view.TextInterfacePage;

public class GiveBackListMenu extends TextInterfacePage {

	private Manager manager;
	public GiveBackListMenu(Manager m) {
		manager = m;
	}
	
	@Override
	public TextInterfacePage display() {
		Borrower user = (Borrower) manager.getActiveUser();
		System.out.println("You chose to give back something. (Good idea !)");

		List<Booking> userBookings = manager.getUserActiveBookings(user.getId());
		// The user can only give back for a booking that has been validated
		// So we filter the list to keep only the validated ones
		List<Booking> validatedBookings = new LinkedList<Booking>();
		for (Booking b : userBookings) {
			if (b.isValidated()) {
				validatedBookings.add(b);
			}
		}
		
		if (validatedBookings.size() == 0) {
			System.out.println("But you don't have any item to return! Nice job!");
			enterToContinue();
			return null;
		}
		
		System.out.println("Choose one of the booking to give back");
		BookingPicker picker = new BookingPicker(validatedBookings);
		picker.display();
		Integer bookingID = picker.getPickedItemId();
		
		// Back option in the picker
		if (bookingID == null) {
			return null;
		}
		
		// Get the selected booking
		Booking booking = validatedBookings.get(bookingID);
		
		// For each item borrowed,
		// Ask the user about the item state 
		for (Material m : booking.getMaterials()) {
			// Only if it was already bad
			if (m.getState() == State.BAD) {
				if (question("The item "+m.getMaterialType().getFullName()+" with serial number "+m.getSerialNumber()+" was already in bad condition when you borrowed it.\n"
						+"Was it destroyed or did it broke during your borrow ?")) {
					// DESTROY THE ITEM
					m.setDestroyed(true);
				}
			}
			// Apply natural degradation
			else {
				m.naturalDegradation();
			}
		}
		
		returnItem(booking);
	
		return null;
	}
	
	/**
	 * Returns the item and display a message according to the return delay.
	 * @param booking The booking that is returned
	 */
	public static void returnItem(Booking booking) {
		Boolean isLate = booking.isLate();
		booking.end();
		if (!isLate) {
			System.out.println("You returned "+booking.getMaterialType().getName()+" on time! Congrats!");
		}
		else {
			System.out.println("You returned "+booking.getMaterialType().getName()+" late. You will have to pay a fee.");
		}
	}

}
