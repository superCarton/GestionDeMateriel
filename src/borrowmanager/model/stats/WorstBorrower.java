package borrowmanager.model.stats;

import java.util.Collection;
import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.Booking;
import borrowmanager.model.element.BorrowableStock;
import borrowmanager.model.user.Borrower;
import borrowmanager.model.user.User;

public class WorstBorrower extends Stats<Borrower> {

	public WorstBorrower(Manager manager) {
		super(manager, "Worst borrower", "The borrower that returns things late the most frequently.");
	}

	@Override
	public Borrower calculate() {
		User worst = null;
		List<User> users = manager.getUsersManager().getAllUsers();
		//Collection<BorrowableStock> stocks = manager.getStockList();
		for (User u : users) {
			int numberLate = 0;
			List<Booking> userBookings = manager.getUserBookings(u.getId());
			
		}
		return null;
	}

}
