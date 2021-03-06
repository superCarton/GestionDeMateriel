package borrowmanager.model.stats;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.user.User;

/**
 * The MostBorrowingMaterial is a class extends of Stats which gives the borrower who doesn't respect the less the delay
 * @author Jonathan Pujol
 * 
 */
public class WorstBorrower extends Stats<StatValue<User, Integer>> {

	public WorstBorrower(Manager manager) {
		super(manager, "Worst borrower", "The borrower that returns things late the most frequently.");
	}

	@Override
	public StatValue<User, Integer> calculate() {
		User worst = null;
		int numberOfLate = 0;
		List<User> users = manager.getUsersManager().getAllUsers();
		//Collection<BorrowableStock> stocks = manager.getStockList();
		for (User u : users) {
			int numberLate = 0;
			List<Booking> userBookings = manager.getUserBookings(u.getId());
			for (Booking booking : userBookings) {
				if (booking.isLate() || booking.wasReturnedLate()) {
					numberLate++;
				}
			}
			if(numberLate > numberOfLate) {
				worst = u;
				numberOfLate = numberLate;
			}
		}
		return new StatValue<User, Integer>(worst, numberOfLate);
	}

}
