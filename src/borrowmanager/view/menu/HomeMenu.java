package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.model.user.Borrower;
import borrowmanager.model.user.StockManager;
import borrowmanager.model.user.User;

/**
 * The purpose of this class is only to redirect correctly the user to
 * their home page. 
 * @author Tom Guillermin
 *
 */
public class HomeMenu {

	public HomeMenu(Manager m) {
		System.out.println("HOME REDIRECTION");
		User u = m.getActiveUser();
		if (u instanceof StockManager) {
			//new StockManagerHomeMenu(m);
		} else if (u instanceof Borrower){
			System.out.println("Creating new BorrowerHomeMenu with manager "+m);
			new BorrowerHomeMenu(m);
		}
	}
}
