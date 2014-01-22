package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.model.user.Borrower;
import borrowmanager.model.user.StockManager;
import borrowmanager.model.user.User;
import borrowmanager.view.TextInterfacePage;

/**
 * The purpose of this class is only to redirect correctly the user to
 * their home page. 
 * @author Tom Guillermin
 *
 */
public class HomeMenu extends TextInterfacePage {

	private Manager manager;
	public HomeMenu(Manager m) {
		manager = m;
	}

	@Override
	public TextInterfacePage display() {
		User u = manager.getActiveUser();
		if (u instanceof StockManager) {
			//return new StockManagerHomeMenu(m);
		} else if (u instanceof Borrower){
			return new BorrowerHomeMenu(manager);
		}
		return null;
	}
}
