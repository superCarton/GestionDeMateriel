package borrowmanager.model.stats;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.user.User;

/**
 * The MostBorrowingMaterial is a class extends of Stats which gives the biggest borrower
 * @author Jonathan Pujol
 */
public class BiggestBorrower extends Stats<String> {

	public BiggestBorrower(Manager manager) {
		super(manager, "Bigest Borrower", "The borrower who has the most borrow");
	}

	@Override
	public String calculate() {
		User biggest = null;
		int numberOfBorrow = 0;
		List<User> users = manager.getUsersManager().getAllUsers();
		for (User u : users){
			if(manager.getUserBookings(u.getId()).size() > numberOfBorrow) {
				biggest = u;
			}
		}
		return biggest.toString();
	}
	
}
