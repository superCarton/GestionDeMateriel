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
		addOption("stats","View statistics");
		addOption("logout", "Logout");
	}

	@Override
	protected TextInterfacePage handleCommand(String c) {
		if (c.equals("validate")) {
			openChildPage(new BookingsValidationMenu(manager));
		}
		else if (c.equals("seeAll")) {
			openChildPage(new AllBookingsList(manager));
		}
		else if (c.equals("seeActive")) {
			openChildPage(new ActiveBookingsList(manager));
		}
		else if (c.equals("seeLate")) {
			openChildPage(new LateBookingsList(manager));
		}
		else if (c.equals("stats")) {
			openChildPage(new StatisticsMenu(manager));
		}
		else if (c.equals("logout")) {
			return null;
		}
		return this;
	}

}
