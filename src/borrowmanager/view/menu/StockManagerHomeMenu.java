package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.view.TextInterfaceOptionPage;
import borrowmanager.view.TextInterfacePage;

public class StockManagerHomeMenu extends TextInterfaceOptionPage {

	private Manager manager;
	
	public StockManagerHomeMenu(Manager m) {
		manager = m;
		ready();
	}
	
	@Override
	protected void build() {
		addOption("validate","Validate bookings");
		addOption("seeAll", "View all bookings");
		addOption("seeActive", "View all active bookings");
		addOption("seeLate", "View all late bookings");
		addOption("logout", "Logout");
	}

	@Override
	protected TextInterfacePage handleCommand(String c) {
		if (c.equals("validate")) {
			openChildPage(new BookingsValidationMenu(manager));
		}
		return null;
	}

}
