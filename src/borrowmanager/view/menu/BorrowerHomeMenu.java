package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.view.TextInterfaceOptionPage;
import borrowmanager.view.TextInterfacePage;

public class BorrowerHomeMenu extends TextInterfaceOptionPage {

	private Manager manager;
	
	public BorrowerHomeMenu(Manager m) {
		manager = m;
		ready();
	}
	
	@Override
	protected void build() {
		setMessage("Welcome "+manager.getActiveUser().getName()+" !\n"+
				"What do you want to do ?");
		addOption("borrow", "Borrow something");
		addOption("giveBack", "Give back something");
		addOption("borrowedList", "See what I've currently borrowed");
		addOption("reservationList", "See my reservations");
		addOption("cancelReservation", "Cancel a reservation");
		addOption("reservationList", "See all my bookings");
		addOption("logout", "Log out");
	}
	
	@Override
	public TextInterfacePage handleCommand(String c) {
		if (c.equals("borrow")) {
			openChildPage(new BorrowMenu(manager));
		}
		else if (c.equals("giveBack")) {
			openChildPage(new GiveBackListMenu(manager));
		}
		else if (c.equals("borrowedList")) {
			openChildPage(new BorrowedListView(manager));
		}
		else if (c.equals("reservationList")) {
			openChildPage(new MyReservationListView(manager));
		}
		else if (c.equals("logout")) {
			manager.setActiveUser(null);
			return null;
		}
		return this;
	}

}
