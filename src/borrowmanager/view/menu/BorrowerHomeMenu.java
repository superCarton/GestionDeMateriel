package borrowmanager.view.menu;

import borrowmanager.model.Manager;
import borrowmanager.view.TextInterfaceOptionPage;

public class BorrowerHomeMenu extends TextInterfaceOptionPage {

	private Manager manager;
	
	public BorrowerHomeMenu(Manager m) {
		manager = m;
		ready();
	}
	
	@Override
	protected void build() {
		System.out.println("Manager = "+manager);
		System.out.println("ActiveUser = "+manager.getActiveUser());
		setMessage("Welcome "+manager.getActiveUser().getName()+" !\n"+
				"What do you want to do ?");
		addOption("borrow", "Borrow something");
		addOption("giveBack", "Give back something");
		addOption("logout", "Log out");
	}
	
	@Override
	public void handleCommand(String c) {
		if (c.equals("borrow")) {
			new BorrowMenu(manager);
		}
		else if (c.equals("giveBack")) {
			new GiveBackMenu(manager);
		}
		else if (c.equals("logout")) {
			
		}
	}

}
