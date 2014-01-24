package borrowmanager.view.menu;

import java.util.List;

import borrowmanager.model.Manager;
import borrowmanager.model.material.Material;
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
		addOption("seeStocks", "View the stock");
		addOption("seeAll", "View all bookings");
		addOption("seeActive", "View all active bookings");
		addOption("seeReservations", "View all reservations");
		addOption("seeLate", "View all late bookings");
		addOption("seeCancelled", "View all cancelled bookings");
		addOption("needRepairList", "View material that needs to be repaired");
		addOption("takeBackFromRepair", "Put all the repaired material back in stock");
		addOption("stats","View statistics");
		addOption("logout", "Logout");
	}

	@Override
	protected TextInterfacePage handleCommand(String c) {
		if (c.equals("validate")) {
			openChildPage(new BookingsValidationMenu(manager));
		}
		else if (c.equals("seeStocks")) {
			openChildPage(new StocksList(manager));
		}
		else if (c.equals("seeAll")) {
			openChildPage(new AllBookingsList(manager));
		}
		else if (c.equals("seeActive")) {
			openChildPage(new ActiveBookingsList(manager));
		}
		else if (c.equals("seeReservations")) {
			openChildPage(new AllReservationListView(manager));
		}
		else if (c.equals("seeLate")) {
			openChildPage(new LateBookingsList(manager));
		}
		else if (c.equals("seeCancelled")) {
			openChildPage(new CancelledBookingsList(manager));
		}
		else if (c.equals("stats")) {
			openChildPage(new StatisticsMenu(manager));
		}
		else if (c.equals("needRepairList")) {
			openChildPage(new NeedRepairList(manager));
		}
		else if (c.equals("takeBackFromRepair")) {
			List<Material> repaired = manager.getMaterialsBackFromRepair();
			System.out.println("Number of items repaired : "+repaired.size());
			if (repaired.size() > 0) {
				System.out.println("List of items repaired :");
				for (Material m : repaired) {
					System.out.println("\t- "+m.getFullName());
				}
			}
			enterToContinue();
		}
		else if (c.equals("logout")) {
			return null;
		}
		return this;
	}

}
