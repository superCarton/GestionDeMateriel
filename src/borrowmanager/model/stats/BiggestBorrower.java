package borrowmanager.model.stats;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.user.User;

/**
 * The BiggestBorrower is a class extends of Stats which gives the biggest borrower
 * @author Jonathan Pujol
 * 
 */
public class BiggestBorrower extends Stats<StatValue<User, Integer>> {

	public BiggestBorrower(Manager manager) {
		super(manager, "Bigest Borrower", "The borrower who has the most borrow");
	}

	@Override
	public StatValue<User, Integer> calculate() {
		User biggest = null;
		int numberOfBorrow = 0;
		List<User> users = manager.getUsersManager().getAllUsers();
		for (User u : users){
			int userNum = manager.getUserBookings(u.getId()).size();
			if(userNum >= numberOfBorrow) {
				numberOfBorrow = userNum;
				biggest = u;
			}
		}
		return new StatValue<User, Integer>(biggest, numberOfBorrow);
	}
	
}
