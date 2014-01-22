package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.model.user.Borrower;
import borrowmanager.model.user.StockManager;
import borrowmanager.model.user.User;
import borrowmanager.view.TextInterfacePage;

public class HomeMenu {

	public HomeMenu(Manager m) {
		User u = m.getActiveUser();
		if (u instanceof StockManager) {
			new StockManagerHomeMenu(m);
		} else if (u instanceof Borrower){
			new BorrowerHomeMenu(m);
		}
	}
}
